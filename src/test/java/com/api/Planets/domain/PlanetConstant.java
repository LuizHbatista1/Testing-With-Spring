package com.api.Planets.domain;

import com.api.Planets.DTO.PlanetDTO;

public class PlanetConstant {

    public final static PlanetDTO PLANET_VALID_DATE = new PlanetDTO("Test","Test",0.0F , "Rock", "Cold");
    public final static PlanetDTO PLANET_INVALID_DATE = new PlanetDTO("","",12.0F,"","");


}
