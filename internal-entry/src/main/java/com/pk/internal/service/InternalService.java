package com.pk.internal.service;

import com.pk.internal.models.Weather;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class InternalService {
  
  RestTemplate restTemplate;

  public Weather getWeather(String lat, String lon) throws Exception {
    ResponseEntity<Weather> weather = restTemplate.getForEntity("//TODO add url", Weather.class);
    log.debug(weather.toString());
    if (weather.getBody() == null) {
      throw new Exception("weather is null");
    }
    log.debug(weather.getBody().toString());
    return weather.getBody();
  }

  public void sendEmail() {
    ;
  }

  // TODO One method then calling private ones to reduce code length?
  public void editUser() {
    ;
  }

  public void logistics() {
    ;
  }

  public void workSchedule() {
    ;
  }

  public void planeSchedule() {
    ;
  }

  public void tickets() {
    ;
  }

  public void landingReservation() {
    ;
  }
}
