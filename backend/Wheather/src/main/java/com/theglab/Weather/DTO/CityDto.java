package com.theglab.Weather.dto;

public record CityDto(
        Long id,
        String name,
        String countryCode,
        Double latitude,
        Double longitude
) {}
