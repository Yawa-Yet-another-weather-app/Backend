package com.yawa.yawa.weather;

import com.yawa.yawa.model.average.AverageWeather;
import com.yawa.yawa.model.forecast.WeatherInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @CrossOrigin(origins = "https://yawa-frontend.onrender.com")
    @GetMapping("/weather/forecast")
    public ResponseEntity<List<WeatherInfo>> getWeekForecast(@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude){
        try{
            List<WeatherInfo> weekForecast = weatherService.getWeekForecast(latitude, longitude);
            return new ResponseEntity<>(weekForecast, HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }

    }

    @CrossOrigin(origins = "https://yawa-frontend.onrender.com")
    @GetMapping("/weather/average")
    public ResponseEntity<AverageWeather> getAverageWeather(@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude){
        try{
            AverageWeather averageWeather = weatherService.getAverageWeather(latitude, longitude);
            return new ResponseEntity<>(averageWeather, HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }

    }

}
