package com.pk.internal.controllers;

import com.pk.internal.models.ErrMsg;
import com.pk.internal.models.Weather;
import com.pk.internal.service.InternalService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class InternalController {
  InternalService internalService;

  @GetMapping("/weather")
  public Weather getWeather(@RequestParam String lat, @RequestParam String lon) throws Exception {
    return internalService.getWeather(lat, lon);
  }  
}
