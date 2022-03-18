package com.pk.weather.controller;

import com.pk.weather.models.Weather;
import com.pk.weather.service.WeatherService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class WeatherController {

  WeatherService weatherService;

  @GetMapping("/oneDay")
  // TODO add validation
  public Weather getOneDayWeatherInfo(@RequestParam String lat, @RequestParam String lon) {
    // TODO use advice insted
    try {
      return weatherService.getOneDay(lat, lon);
    } catch (Exception e) {
      return null;
    }
  }
}
