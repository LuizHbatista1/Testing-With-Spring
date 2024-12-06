package com.api.Planets.domain;

import com.api.Planets.DTO.PlanetDTO;

import java.util.ArrayList;
import java.util.List;

public class PlanetConstant {

    public final static PlanetDTO PLANET_VALID_DATE = new PlanetDTO("Earth","Test",0.0 , "Rock", "Cold");
    public final static PlanetDTO PLANET_INVALID_DATE = new PlanetDTO("","",null,"","");


    public final static Planet MARS = new Planet(1L,"Mars","Test",0.0 , "Rock", "Cold");
    public final static Planet SATURN = new Planet(2L,"Saturn","Test",0.0 , "Rock", "Cold");
    public final static Planet JUPITER = new Planet(3L,"Jupiter","Test",0.0 , "Rock", "Cold");

    public static final List<Planet>PLANETS = new ArrayList<>(){

        {

            add(MARS);
            add(JUPITER);
            add(SATURN);
        }

    };

}
