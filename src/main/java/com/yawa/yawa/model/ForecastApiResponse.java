package com.yawa.yawa.model;

import lombok.Data;

@Data
public class ForecastApiResponse {
    Hourly hourly;
    Daily daily;
}
