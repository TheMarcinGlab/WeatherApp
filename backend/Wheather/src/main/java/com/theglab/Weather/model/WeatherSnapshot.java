package com.theglab.Weather.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class WeatherSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    private Double temperature;
    private Double temperaturePerceived; // nowe pole
    private Integer pressure;
    private Integer humidity;
    private Double windSpeed;
    private Integer indexUV; // nowe pole
    private String description;
    private String icon;
    private LocalDateTime recordedAt;

    // --- GETTERY I SETTERY ---

    public Long getId() { return id; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Double getTemperaturePerceived() { return temperaturePerceived; }
    public void setTemperaturePerceived(Double temperaturePerceived) { this.temperaturePerceived = temperaturePerceived; }

    public Integer getPressure() { return pressure; }
    public void setPressure(Integer pressure) { this.pressure = pressure; }

    public Integer getHumidity() { return humidity; }
    public void setHumidity(Integer humidity) { this.humidity = humidity; }

    public Double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(Double windSpeed) { this.windSpeed = windSpeed; }

    public Integer getIndexUV() { return indexUV; }
    public void setIndexUV(Integer indexUV) { this.indexUV = indexUV; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}
