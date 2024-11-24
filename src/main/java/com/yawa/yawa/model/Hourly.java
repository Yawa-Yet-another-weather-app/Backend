package com.yawa.yawa.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Hourly {
    List<LocalDateTime> time;
    List<Float> temperature_2m;
}
