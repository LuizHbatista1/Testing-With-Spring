package com.api.Planets.service;

import com.api.Planets.DTO.PlanetDTO;
import com.api.Planets.domain.Planet;

import java.util.List;
import java.util.Optional;

public interface PlanetService {

    Planet createPlanet(PlanetDTO planetDTO);

    void savePlanet(Planet planet);

    Optional<Planet> getPlanetById(Long id);

    Optional<Planet> getPlanetByName(String name);

    List<Planet> listPlanetsByFilters(String terrain , String climate);

    void delete(Long id);






}
