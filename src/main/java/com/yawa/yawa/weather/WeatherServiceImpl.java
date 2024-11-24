package com.yawa.yawa.weather;

import com.yawa.yawa.model.ForecastApiResponse;
import com.yawa.yawa.model.ForecastRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedList;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService{

    RestClient restClient = RestClient.create();

    @Override
    public List<WeatherInfo> getWeekForecast(ForecastRequest forecastRequest) {
        ForecastApiResponse apiResponse = restClient.get()
                .uri(
                        UriComponentsBuilder
                            .fromUriString("https://api.open-meteo.com/v1/forecast?latitude={l}&longitude={p}&hourly=temperature_2m&daily=weather_code,sunshine_duration")
                            .build(forecastRequest.getLatitude(), forecastRequest.getLongitude())
                )
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new IllegalStateException("ForecastRequest: " + forecastRequest + '\n' + "Status code: " + response.getStatusCode());
                })
                .body(ForecastApiResponse.class);

        List<WeatherInfo> result = new LinkedList<>();

        //var hourIterator = apiResponse.getHourly().getTime().iterator();
        var temparatureIterator = apiResponse.getHourly().getTemperature_2m().iterator();

        var dateIterator = apiResponse.getDaily().getTime().iterator();
        var weatherCodeIterator = apiResponse.getDaily().getWeather_code().iterator();
        var sunshineDurationIterator = apiResponse.getDaily().getSunshine_duration().iterator();

        while(dateIterator.hasNext()){

            double approximateEnergy = 2.5 * 0.2 * sunshineDurationIterator.next();

            float maxTemperature = temparatureIterator.next();
            float minTemperature = maxTemperature;

            for(int i = 0; i < 23; i++){
                float current = temparatureIterator.next();

                if(current < minTemperature)
                    minTemperature = current;
                if(current > maxTemperature)
                    maxTemperature = current;
            }

            result.add(
                    new WeatherInfo(
                            dateIterator.next(), weatherCodeIterator.next(), minTemperature, maxTemperature, approximateEnergy
                    )
            );
        }

        return result;
    }
}
