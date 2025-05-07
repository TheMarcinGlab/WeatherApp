package com.theglab.Weather.DTO;

public record CityDto(
        Long id,
        String name,
        String countryCode,
        Double latitude,
        Double longitude
) {}
