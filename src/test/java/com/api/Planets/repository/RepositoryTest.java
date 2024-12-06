package com.api.Planets.repository;

import com.api.Planets.DTO.PlanetDTO;
import com.api.Planets.domain.Planet;

import static com.api.Planets.domain.PlanetConstant.MARS;
import static com.api.Planets.domain.PlanetConstant.PLANET_VALID_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.api.Planets.domain.PlanetConstant;
import com.api.Planets.domain.QueryBuilder;
import org.h2.table.Plan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

import javax.management.Query;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void savePlanet_WithValidDate_ReturnSavePlanet(){

        PlanetDTO planetDTO = PlanetConstant.PLANET_VALID_DATE;
        Planet newPlanet = new Planet(planetDTO);

        planetRepository.save(newPlanet);

        Planet sut = entityManager.find(Planet.class , newPlanet.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(newPlanet.getName());
        assertThat(sut.getDescription()).isEqualTo(newPlanet.getDescription());
        assertThat(sut.getMass()).isEqualTo(newPlanet.getMass());
        assertThat(sut.getTerrain()).isEqualTo(newPlanet.getTerrain());
        assertThat(sut.getClimate()).isEqualTo(newPlanet.getClimate());

    }

    @Test
    public void savePlanet_WithInvalidDate_ReturnThrow(){

        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet(null,"","",null,"","");

        assertThatThrownBy(()-> planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(()-> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);

    }

    @Test
    public void savePlanet_WithExistingName_ReturnThrow(){

        PlanetDTO planetDTO = PlanetConstant.PLANET_VALID_DATE;
        Planet newPlanet = new Planet(planetDTO);

        Planet planet = entityManager.persistFlushFind(newPlanet);
        entityManager.detach(planet);
        planet.setId(null);

        assertThatThrownBy(()-> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);

    }

    @Test
    public void getPlanet_ByExistingId_ReturnPlanet(){

        PlanetDTO planetDTO = PlanetConstant.PLANET_VALID_DATE;
        Planet newPlanet = new Planet(planetDTO);

        Planet planet = entityManager.persistFlushFind(newPlanet);
        Optional<Planet> sut = planetRepository.findById(planet.getId());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(newPlanet);

    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnEmpty(){

        Optional<Planet> sut = planetRepository.findById(1L);

        assertThat(sut).isEmpty();

    }

    @Test
    public void getPlanet_ByExistingName_ReturnPlanet(){

        PlanetDTO planetDTO = PlanetConstant.PLANET_VALID_DATE;
        Planet newPlanet = new Planet(planetDTO);

        Planet planet = entityManager.persistFlushFind(newPlanet);

        Optional<Planet> sut = planetRepository.findPlanetByName(planet.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(newPlanet);
        
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnEmpty(){

        Optional<Planet> sut = planetRepository.findPlanetByName("Mars");

        assertThat(sut).isEmpty();

    }

    @Sql(scripts = "/import_planets.sql")
    @Test
    public void listPlanet_ReturnFilteredPlanets(){

        Example<Planet> queryWithoutFilters = QueryBuilder.makeQuery(new Planet());
        Example<Planet> queryWithFilters = QueryBuilder.makeQuery(new Planet(MARS.getTerrain() , MARS.getClimate()));

        List<Planet> responseWithoutFilters = planetRepository.findAll(queryWithoutFilters);
        List<Planet> responseWithFilters = planetRepository.findAll(queryWithFilters);

        assertThat(responseWithoutFilters).isNotEmpty();
        assertThat(responseWithoutFilters).hasSize(3);

        assertThat(responseWithFilters).isNotEmpty();
        assertThat(responseWithFilters).hasSize(1);
        assertThat(responseWithFilters.get(0)).isEqualTo(MARS);


    }




}
