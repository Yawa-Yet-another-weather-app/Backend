package com.yawa.yawa.model.average;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AverageWeather {
    double avgPressure;
    double avgSunExposure;
    double minTemperature;
    double maxTemperature;
    String description;
}
