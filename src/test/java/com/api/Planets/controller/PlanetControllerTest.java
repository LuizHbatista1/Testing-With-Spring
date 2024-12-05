package com.api.Planets.controller;

import com.api.Planets.DTO.PlanetDTO;
import com.api.Planets.domain.Planet;
import com.api.Planets.domain.PlanetConstant;
import com.api.Planets.service.PlanetServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import static com.api.Planets.domain.PlanetConstant.PLANET_VALID_DATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.awt.*;
import java.util.Optional;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PlanetServiceImpl planetService;

    @Test
    public void createPlanet_WithValidDate_ReturnCreated() throws Exception{

        PlanetDTO planetDTO = PlanetConstant.PLANET_VALID_DATE;
        Planet newPlanet = new Planet(planetDTO);

        when(planetService.createPlanet(PLANET_VALID_DATE)).thenReturn(newPlanet);

        mockMvc.perform(post("/planet/create").content(objectMapper.writeValueAsString(newPlanet)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$").value(newPlanet));

    }

    @Test
    public void createPlanet_WithInvalidDate_ReturnBadRequest()throws Exception{

        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("","",null,"","");

        mockMvc.perform(post("/planet/create").content(objectMapper.writeValueAsString(emptyPlanet))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());


        mockMvc.perform(post("/planet/create").content(objectMapper.writeValueAsString(invalidPlanet))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void createPlanet_WithExistingName_ReturnConflict() throws Exception{

        when(planetService.createPlanet(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/planet/create").content(objectMapper.writeValueAsString(PLANET_VALID_DATE)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

    }

    @Test
    public void getPlanet_ByExistingId_ReturnPlanet() throws Exception{

        Planet newPlanet = new Planet();

        when(planetService.getPlanetById(1L)).thenReturn(Optional.of(newPlanet));

        mockMvc.perform(get("/planet/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$").value(newPlanet));

    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnNotFound() throws Exception{

        Planet newPlanet = new Planet();

        when(planetService.getPlanetById(isNull())).thenReturn(Optional.of(newPlanet));

        mockMvc.perform(get("/planet/1"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getPlanet_ByExistingName_ReturnPlanet() throws Exception{

        PlanetDTO planetDTO = PlanetConstant.PLANET_VALID_DATE;
        Planet newPlanet = new Planet(planetDTO);

        when(planetService.getPlanetByName(newPlanet.getName())).thenReturn(Optional.of(newPlanet));

        mockMvc.perform(get("/planet/name/" + newPlanet.getName()))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$").value(newPlanet));


    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnEmpty() throws Exception {

        PlanetDTO planetDTO = PlanetConstant.PLANET_VALID_DATE;
        Planet newPlanet = new Planet(planetDTO);

        when(planetService.getPlanetByName(isNull())).thenReturn(Optional.empty());

        mockMvc.perform(get("/planet/name/" + newPlanet.getName()))
                .andExpect(status().isNotFound());

    }

}


