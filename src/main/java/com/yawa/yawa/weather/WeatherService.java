package com.yawa.yawa.weather;

import com.yawa.yawa.model.average.AverageWeather;
import com.yawa.yawa.model.Location;

import java.util.List;
public interface WeatherService {
    List<WeatherInfo> getWeekForecast(Location location);
    AverageWeather getAverageWeather(Location location);
}
