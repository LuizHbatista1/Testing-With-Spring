package com.api.Planets.service;

import com.api.Planets.DTO.PlanetDTO;
import com.api.Planets.domain.Planet;
import com.api.Planets.domain.QueryBuilder;
import com.api.Planets.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetServiceImpl implements PlanetService{

    private final PlanetRepository planetRepository;

    @Autowired
    public PlanetServiceImpl(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @Override
    public Planet createPlanet(PlanetDTO planetDTO) {
        Planet newPlanet =  new Planet(planetDTO);
        newPlanet.setName(planetDTO.name());
        newPlanet.setDescription(planetDTO.description());
        newPlanet.setMass(planetDTO.mass());
        newPlanet.setTerrain(planetDTO.terrain());
        newPlanet.setClimate(newPlanet.getClimate());
        this.savePlanet(newPlanet);
        return newPlanet;
    }

    @Override
    public void savePlanet(Planet planet) {

        this.planetRepository.save(planet);

    }

    @Override
    public Optional<Planet> getPlanetById(Long id) {

       return Optional.ofNullable(planetRepository.findById(id).orElseThrow(RuntimeException::new));

    }

    @Override
    public Optional<Planet> getPlanetByName(String name) {

        return Optional.ofNullable(planetRepository.findPlanetByName(name)).orElseThrow((RuntimeException::new));

    }

    @Override
    public List<Planet> listPlanetsByFilters(String terrain, String climate) {
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(terrain , climate));
        return planetRepository.findAll(query);
    }

    @Override
    public void delete(Long id) {
        this.planetRepository.deleteById(id);
    }
}
