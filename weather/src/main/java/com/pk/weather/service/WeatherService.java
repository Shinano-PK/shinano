package com.pk.weather.service;

import com.pk.weather.models.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {
  
  private static final String API_URL_ONE_DAY_WEATHER = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API_key}";

  @Value("${shinano_weather_api_key}")
  private String apiKey;

  @NonNull private RestTemplate restTemplate;

  public Root getOneDay(String lat, String lon) throws Exception {
    String apiUrlOneDayWeather = API_URL_ONE_DAY_WEATHER;
    apiUrlOneDayWeather = apiUrlOneDayWeather.replace("{lat}", lat);
    apiUrlOneDayWeather = apiUrlOneDayWeather.replace("{lon}", lon);
    apiUrlOneDayWeather = apiUrlOneDayWeather.replace("{API_key}", apiKey);
    ResponseEntity<Root> weather = restTemplate.getForEntity(apiUrlOneDayWeather, Root.class);
    log.debug(weather.toString());
    if (weather.getBody() == null) {
      throw new Exception("Weather body is null");
    }
    log.debug(weather.getBody().toString());
    return weather.getBody();
  }
}
