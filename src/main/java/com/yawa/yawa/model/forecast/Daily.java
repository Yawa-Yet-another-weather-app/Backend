package com.yawa.yawa.model.forecast;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Daily {
    List<LocalDate> time;
    List<Integer> weather_code;
    List<Float> temperature_2m_max;
    List<Float> temperature_2m_min;
    List<Float> sunshine_duration;
}
