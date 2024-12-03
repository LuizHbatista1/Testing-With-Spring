package com.api.Planets.service;

import com.api.Planets.DTO.PlanetDTO;
import com.api.Planets.domain.Planet;
import com.api.Planets.domain.PlanetConstant;
import com.api.Planets.domain.QueryBuilder;
import com.api.Planets.repository.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.api.Planets.domain.PlanetConstant.PLANET_INVALID_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PlanetServiceImpl.class)
public class ServiceTest {

    @MockitoBean
    private PlanetRepository planetRepository;

    @Autowired
    private PlanetServiceImpl planetService;

    @Test
    public void createPlanet_WithValidDate_ReturnPlanet(){

        PlanetDTO planetDTO = PlanetConstant.PLANET_VALID_DATE;
        Planet newPlanet = new Planet(planetDTO);

        when(planetRepository.save(ArgumentMatchers.any(Planet.class))).thenReturn(newPlanet);

        Planet sut = planetService.createPlanet(planetDTO);

        assertThat(sut).isEqualTo(newPlanet);

    }

    @Test
    public void createPlanet_WithInvalid_Date(){

        when(planetRepository.save(ArgumentMatchers.any(Planet.class))).thenThrow(RuntimeException.class);

        assertThatThrownBy(()-> planetService.createPlanet(PLANET_INVALID_DATE));

    }

    @Test
    public void getPlanet_ByExistingId_ReturnPlanet(){

        Planet newPlanet =  new Planet();

        when(planetRepository.findById(1L)).thenReturn(Optional.of(newPlanet));

        Optional<Planet> sut = planetService.getPlanetById(1L);

        assertThat(sut.get()).isEqualTo(newPlanet);

    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnEmpty(){

        when(planetRepository.findById(1L)).thenThrow(RuntimeException.class);

        assertThatThrownBy(()-> planetService.getPlanetById(isNull()));
    }

    @Test
    public void getPlanet_ByExistingName_ReturnPlanet(){

        Planet newPlanet = new Planet();

        when(planetRepository.findPlanetByName("Earth")).thenReturn(Optional.of(newPlanet));

        Optional<Planet> sut = planetService.getPlanetByName("Earth");

        assertThat(sut.get()).isEqualTo(newPlanet);

    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnEmpty(){

        when(planetRepository.findPlanetByName("Earth")).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.getPlanetByName("Earth");

        assertThat(sut).isEmpty();

    }

    @Test
    public void listPlanets_ReturnsAllPlanets(){

        Planet newPlanet = new Planet();

        List<Planet> planets = new ArrayList<>(){

            {
                add(newPlanet);
            }
        };

        Example<Planet> query = QueryBuilder.makeQuery(new Planet(newPlanet.getTerrain() , newPlanet.getClimate()));

        when(planetRepository.findAll(query)).thenReturn(planets);

        List<Planet> sut = planetService.listPlanetsByFilters(newPlanet.getTerrain(), newPlanet.getClimate());

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(newPlanet);

    }

    @Test
    public void listPlanets_ReturnNoPlanets(){

        Planet newPlanet = new Planet();

        when(planetRepository.findAll((Example<Planet>) any())).thenReturn(Collections.emptyList());

        List<Planet> sut = planetService.listPlanetsByFilters(newPlanet.getTerrain(), newPlanet.getTerrain());

        assertThat(sut).isEmpty();

    }

    @Test
    public void whenDeleteByIdFromRepository_thenDeletingShouldBeSuccessful(){

        planetRepository.deleteById(1L);
        assertThat(planetRepository.count()).isEqualTo(0);

    }

    @Test
    public void whenDeleteByIdFromRepository_thenRepositoryShouldBeEmpty(){

        planetRepository.deleteById(isNull());
        assertThat(planetRepository.count()).isEqualTo(0);

    }





}
