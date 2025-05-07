package com.theglab.Weather.service;

import com.theglab.Weather.model.WeatherSnapshot;
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
}
