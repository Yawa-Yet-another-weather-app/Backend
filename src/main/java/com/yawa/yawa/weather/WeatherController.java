package com.yawa.yawa.weather;

import com.yawa.yawa.model.ForecastRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather/forecast")
    public List<WeatherInfo> getWeekForecast(@RequestBody ForecastRequest forecastRequest){
        return weatherService.getWeekForecast(forecastRequest);
    }


}
