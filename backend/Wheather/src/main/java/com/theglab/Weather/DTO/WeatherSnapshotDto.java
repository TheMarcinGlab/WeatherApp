package com.theglab.Weather.dto;

import java.time.LocalDateTime;

public record WeatherSnapshotDto(
        Long id,
        com.theglab.Weather.dto.CityDto city,
        Double temperature,
        Double temperaturePerceived,
        Integer pressure,
        Integer humidity,
        Double windSpeed,
        Integer indexUV,
        String description,
        String icon,
        LocalDateTime recordedAt
) {}
