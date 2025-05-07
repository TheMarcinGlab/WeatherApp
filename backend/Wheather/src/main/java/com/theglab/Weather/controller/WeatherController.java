package com.theglab.Weather.controller;

import com.theglab.Weather.model.City;
import com.theglab.Weather.model.WeatherSnapshot;
import com.theglab.Weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/public/weather")
@CrossOrigin(origins = "http://localhost:4200")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/current/byId")
    public ResponseEntity<WeatherSnapshot> getCurrentById(@RequestParam Long cityId) {
        return ResponseEntity.ok(weatherService.getCurrentWeatherByCityId(cityId));
    }


    private com.theglab.Weather.DTO.CityDto mapCityToDto(City city) {
        return new com.theglab.Weather.DTO.CityDto(
                city.getId(),
                city.getName(),
                city.getCountryCode(),
                city.getLatitude(),
                city.getLongitude()
        );
    }

    private com.theglab.Weather.DTO.WeatherSnapshotDto mapSnapshotToDto(WeatherSnapshot snapshot) {
        return new com.theglab.Weather.DTO.WeatherSnapshotDto(
                snapshot.getId(),
                mapCityToDto(snapshot.getCity()),
                snapshot.getTemperature(),
                snapshot.getTemperaturePerceived(),
                snapshot.getPressure(),
                snapshot.getHumidity(),
                snapshot.getWindSpeed(),
                snapshot.getIndexUV(),
                snapshot.getDescription(),
                snapshot.getIcon(),
                snapshot.getRecordedAt()
        );
    }


    /** Historia od daty start do daty end (oba inclusive) */
    @GetMapping("/history/range")
    public ResponseEntity<List<com.theglab.Weather.DTO.WeatherSnapshotDto>> getHistoryByRange(
            @RequestParam Long cityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<WeatherSnapshot> list = weatherService.getHistoryByDateRange(cityId, startDate, endDate);

        List<com.theglab.Weather.DTO.WeatherSnapshotDto> dtoList = list.stream()
                .map(this::mapSnapshotToDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }


    @GetMapping("/history/lastDays")
    public ResponseEntity<List<WeatherSnapshot>> getHistoryLastDays(
            @RequestParam Long cityId,
            @RequestParam(defaultValue = "7") int days) {

        return ResponseEntity.ok(weatherService.getLastDaysHistory(cityId, days));
    }

    @GetMapping("/futureWeather")
    public ResponseEntity<List<WeatherSnapshot>> futureWeather(
        @RequestParam Long cityId,
        @RequestParam int days)
    {

        if(days < 1 || days > 14){
            return ResponseEntity.badRequest().build();
        }


        List<WeatherSnapshot> futureWeather = weatherService.getFutureWeather(cityId, days);
        return ResponseEntity.ok(futureWeather);

    }

}
