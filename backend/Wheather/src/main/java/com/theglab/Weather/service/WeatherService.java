package com.theglab.Weather.service;

import com.theglab.Weather.DTO.WeatherSnapshotDto;
import com.theglab.Weather.model.City;
import com.theglab.Weather.model.WeatherSnapshot;
import com.theglab.Weather.repository.CityRepository;
import com.theglab.Weather.repository.WeatherSnapshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class WeatherService {

    @Autowired
    private WeatherSnapshotRepository repo;

    @Autowired
    private CityRepository cityRepository;

    public WeatherSnapshot getCurrentWeatherByCityId(Long cityId) {
        return repo.findTopByCityIdOrderByRecordedAtDesc(cityId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Brak danych pogodowych dla miasta o ID " + cityId
                        )
                );
    }

    public List<WeatherSnapshot> getHistoryByDateRange(Long cityId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime from = startDate.atStartOfDay();
        LocalDateTime to   = endDate.atTime(LocalTime.MAX.withNano(0));
        return repo.findByCityIdAndRecordedAtBetweenOrderByRecordedAtDesc(cityId, from, to);
    }

    public List<WeatherSnapshot> getLastDaysHistory(Long cityId, int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return repo.findByCityIdAndRecordedAtAfterOrderByRecordedAtDesc(cityId, since);
    }


    public List<WeatherSnapshot> getFutureWeather(Long cityId, int days){
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = start.plusDays(days -1);


        LocalDateTime startDate = start.atStartOfDay();
        LocalDateTime endDate = end.atTime(LocalTime.MAX.withNano(0));

        return repo.findForecastByCityAndDateRange(cityId, startDate, endDate);
    }

    public void saveSnapshotFromDto(WeatherSnapshotDto dto) {
        City city = cityRepository.findById(dto.city().id())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono miasta"));

        WeatherSnapshot snapshot = new WeatherSnapshot();
        snapshot.setCity(city);
        snapshot.setTemperature(dto.temperature());
        snapshot.setTemperaturePerceived(dto.temperaturePerceived());
        snapshot.setPressure(dto.pressure());
        snapshot.setHumidity(dto.humidity());
        snapshot.setWindSpeed(dto.windSpeed());
        snapshot.setIndexUV(dto.indexUV());
        snapshot.setDescription(dto.description());
        snapshot.setIcon(dto.icon());
        snapshot.setRecordedAt(dto.recordedAt());

        repo.save(snapshot);
    }



}
