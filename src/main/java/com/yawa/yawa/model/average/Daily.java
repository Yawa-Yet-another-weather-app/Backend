package com.yawa.yawa.model.average;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class Daily {
    List<LocalDate> time;
    List<Double> temperature_2m_max;
    List<Double> temperature_2m_min;
    List<Double> daylight_duration;
    List<Double> rain_sum;
}
