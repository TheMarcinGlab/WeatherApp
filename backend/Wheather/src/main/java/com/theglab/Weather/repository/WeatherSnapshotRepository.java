package com.theglab.Weather.repository;

import com.theglab.Weather.model.WeatherSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherSnapshotRepository extends JpaRepository<WeatherSnapshot, Long> {
    Optional<WeatherSnapshot> findTopByCityIdOrderByRecordedAtDesc(Long cityId);
    List<WeatherSnapshot> findByCityIdAndRecordedAtBetweenOrderByRecordedAtDesc(
            Long cityId, LocalDateTime start, LocalDateTime end);
    List<WeatherSnapshot> findByCityIdAndRecordedAtAfterOrderByRecordedAtDesc(
            Long cityId, LocalDateTime after);


    @Query("SELECT w FROM WeatherSnapshot w WHERE w.city.id = :cityId AND w.recordedAt BETWEEN :start AND :end")
    List<WeatherSnapshot> findForecastByCityAndDateRange(
            @Param("cityId") Long cityId,
            @Param("start") LocalDateTime startDate,
            @Param("end") LocalDateTime endDate
    );

}
