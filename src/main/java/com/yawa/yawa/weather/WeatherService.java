package com.yawa.yawa.weather;

import com.yawa.yawa.model.average.AverageWeather;
import com.yawa.yawa.model.forecast.WeatherInfo;

import java.util.List;
public interface WeatherService {
    List<WeatherInfo> getWeekForecast(String latitude,String longitude);
    AverageWeather getAverageWeather(String latitude,String longitude);
}
