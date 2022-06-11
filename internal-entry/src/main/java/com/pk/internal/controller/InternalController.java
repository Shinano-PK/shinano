package com.pk.internal.controller;

import com.pk.internal.model.Root;
import com.pk.internal.service.InternalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class InternalController {
  InternalService internalService;

  @GetMapping("/weather/oneDay")
  public Root getWeather(@RequestParam String lat, @RequestParam String lon) throws Exception {
    return internalService.getWeather(lat, lon);
  }  
}
