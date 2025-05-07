package com.theglab.Weather.controller;

import com.theglab.Weather.DTO.WeatherSnapshotDto;
import com.theglab.Weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/weather") // ⬅️ ustala prefiks URL
@CrossOrigin(origins = "http://localhost:4200") // ⬅️ Angular frontend
public class WeatherControllerADMIN {

    @Autowired
    private WeatherService weatherService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')") // ⬅️ jeśli chcesz dopuścić też zwykłych użytkowników
    public ResponseEntity<Void> addWeatherSnapshot(@RequestBody WeatherSnapshotDto dto) {
        weatherService.saveSnapshotFromDto(dto);
        return ResponseEntity.ok().build();
    }
}
