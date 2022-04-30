package com.pk.internal.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.pk.internal.model.Root;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class InternalService {

  RestTemplate restTemplate;

  public Root getWeather(String lat, String lon) throws Exception {
    ResponseEntity<Root> weather =
        restTemplate.getForEntity(
            "http://weather-service/oneDay?lat={lat}&lon={lon}", Root.class, lat, lon);
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
