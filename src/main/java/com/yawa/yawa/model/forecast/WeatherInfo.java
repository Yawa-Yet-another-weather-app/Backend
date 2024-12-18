package com.yawa.yawa.model.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WeatherInfo {
    LocalDate date;
    Integer weatherCode;
    float minTemperature;
    float maxTemperature;
    double approximateEnergy;
}
