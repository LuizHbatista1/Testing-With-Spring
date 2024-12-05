package com.api.Planets.controller;


import com.api.Planets.DTO.PlanetDTO;
import com.api.Planets.domain.Planet;
import com.api.Planets.service.PlanetServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/planet")
public class PlanetController {

    private PlanetServiceImpl planetService;

    @Autowired
    public PlanetController(PlanetServiceImpl planetService) {
        this.planetService = planetService;
    }

    @PostMapping("/create")
    public ResponseEntity<Planet>createPlanet(@RequestBody @Valid PlanetDTO planetDTO){

        Planet newPlanet = planetService.createPlanet(planetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlanet);


    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet>getPlanetsById(@PathVariable("id") Long id){

        return planetService.getPlanetById(id).map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Planet>getPlanetsByName(@PathVariable("name") String name){

        return planetService.getPlanetByName(name).map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<List<Planet>> listPlanetsByFilters(@RequestParam(required = false)String terrain,
    @RequestParam(required = false)String climate){

        List<Planet> planets = planetService.listPlanetsByFilters(terrain , climate);
        return ResponseEntity.ok(planets);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void>deletePlanetsById(@PathVariable ("id")Long id){

        planetService.delete(id);
        return ResponseEntity.noContent().build();

    }

}
