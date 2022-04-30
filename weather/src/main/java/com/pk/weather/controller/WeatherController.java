package com.pk.weather.controller;

import com.pk.weather.model.Root;
import com.pk.weather.service.WeatherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class WeatherController {
  WeatherService weatherService;

  @GetMapping("/oneDay")
  public Root getOneDayWeatherInfo(@RequestParam String lat, @RequestParam String lon) throws Exception {
    log.trace("getOneDayWeatherInfo() called");

    Double.parseDouble(lat);
    Double.parseDouble(lon);
    return weatherService.getOneDay(lat, lon);
  }
}
