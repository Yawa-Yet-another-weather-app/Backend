package com.yawa.yawa.model;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Daily {
    List<LocalDate> time;
    List<Integer> weather_code;
    List<Float> sunshine_duration;
}
