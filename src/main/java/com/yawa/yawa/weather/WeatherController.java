package com.yawa.yawa.weather;

import com.yawa.yawa.model.average.AverageWeather;
import com.yawa.yawa.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather/forecast")
    public ResponseEntity<List<WeatherInfo>> getWeekForecast(@RequestBody Location location){
        try{
            List<WeatherInfo> weekForecast = weatherService.getWeekForecast(location);
            return new ResponseEntity<>(weekForecast, HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }

    }

    @GetMapping("/weather/average")
    public ResponseEntity<AverageWeather> getAverageWeather(@RequestBody Location location){
        try{
            AverageWeather averageWeather = weatherService.getAverageWeather(location);
            return new ResponseEntity<>(averageWeather, HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }

    }

}
