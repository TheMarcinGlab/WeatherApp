package com.theglab.Weather.DTO;

import java.time.LocalDateTime;

public record WeatherSnapshotDto(
        Long id,
        com.theglab.Weather.DTO.CityDto city,
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
