package com.yawa.yawa.weather;

import com.yawa.yawa.model.average.AverageApiResponse;
import com.yawa.yawa.model.average.AverageWeather;
import com.yawa.yawa.model.forecast.ForecastApiResponse;
import com.yawa.yawa.model.forecast.WeatherInfo;
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
    public List<WeatherInfo> getWeekForecast(String latitude,String longitude) {
        ForecastApiResponse apiResponse = restClient.get()
            .uri(
                    UriComponentsBuilder
                        .fromUriString("https://api.open-meteo.com/v1/forecast?latitude={l}&longitude={p}&daily=weather_code,temperature_2m_max,temperature_2m_min,sunshine_duration")
                        .build(latitude, longitude)
            )
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                throw new IllegalStateException("Status code: " + response.getStatusCode());
            })
            .body(ForecastApiResponse.class);

        List<WeatherInfo> result = new LinkedList<>();

        var dateIterator = apiResponse.getDaily().getTime().iterator();
        var weatherCodeIterator = apiResponse.getDaily().getWeather_code().iterator();
        var maxTemperatureIterator = apiResponse.getDaily().getTemperature_2m_max().iterator();
        var minTemperatureIterator = apiResponse.getDaily().getTemperature_2m_min().iterator();
        var sunshineDurationIterator = apiResponse.getDaily().getSunshine_duration().iterator();

        while(dateIterator.hasNext()){
            double approximateEnergy = 2.5 * 0.2 * sunshineDurationIterator.next();
            result.add(
                    new WeatherInfo(
                            dateIterator.next(), weatherCodeIterator.next(), minTemperatureIterator.next(), maxTemperatureIterator.next(), approximateEnergy
                    )
            );
        }

        return result;
    }

    @Override
    public AverageWeather getAverageWeather(String latitude,String longitude) {

        AverageApiResponse apiResponse = restClient.get()
            .uri(
                    UriComponentsBuilder
                            .fromUriString("https://api.open-meteo.com/v1/forecast?latitude={l}&longitude={p}&hourly=surface_pressure&daily=temperature_2m_max,temperature_2m_min,daylight_duration,rain_sum")
                            .build(latitude, longitude)
            )
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                throw new IllegalStateException("Status code: " + response.getStatusCode());
            })
            .body(AverageApiResponse.class);

        double avgPressure = apiResponse.getHourly().getSurface_pressure().stream().mapToDouble(Double::doubleValue).average().orElse(-1.0);
        double avgSunExposure =
                apiResponse.getDaily().getDaylight_duration().stream().mapToDouble(Double::doubleValue).average().orElse(-1.0);
        double minTemperature = apiResponse.getDaily().getTemperature_2m_min().stream().min(Double::compareTo).orElse(-1.0);
        double maxTemperature = apiResponse.getDaily().getTemperature_2m_max().stream().max(Double::compareTo).orElse(-1.0);
        long rainyDays = apiResponse.getDaily().getRain_sum().stream().filter(x -> x > 5.0).count();
        String description = rainyDays > 3 ? "Rainy" : "Sunny";

        return new AverageWeather(avgPressure, avgSunExposure, minTemperature, maxTemperature, description);
    }
}
