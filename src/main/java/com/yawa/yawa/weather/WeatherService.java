package com.yawa.yawa.weather;

import com.yawa.yawa.model.ForecastRequest;

import java.util.List;
public interface WeatherService {
    List<WeatherInfo> getWeekForecast(ForecastRequest forecastRequest);
}
