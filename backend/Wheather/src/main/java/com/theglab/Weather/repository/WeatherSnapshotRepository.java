package com.theglab.Weather.repository;

import com.theglab.Weather.model.WeatherSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
