package com.api.Planets.domain;

import com.api.Planets.DTO.PlanetDTO;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Table(name = "planets")
@Entity(name = "planets")
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty
    @Column(nullable = false , unique = true)
    private String name;
    @NotEmpty
    @Column
    private String description;
    @NotEmpty
    @Column(nullable = false)
    private Double mass;
    @NotEmpty
    @Column(nullable = false)
    private String terrain;
    @NotEmpty
    @Column(nullable = false)
    private String climate;

    public Planet() {

    }

    public Planet(PlanetDTO data) {

        this.name = data.name();
        this.description = data.description();
        this.mass = data.mass();
        this.terrain = data.terrain();
        this.climate = data.climate();

    }

    public Planet(Long id , String name, String description, Double mass , String terrain , String climate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.mass = mass;
        this.terrain = terrain;
        this.climate = climate;
    }

    public Planet(String terrain, String climate) {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(obj, this);
    }

}
