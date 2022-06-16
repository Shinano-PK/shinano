package com.pk.internal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.internal.model.Flight;
import com.pk.internal.model.FlightControlRequest;
import com.pk.internal.model.FlightInput;
import com.pk.internal.model.RestockSupply;
import com.pk.internal.model.Root;
import com.pk.internal.service.InternalService;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@AllArgsConstructor
public class InternalController {
  RestTemplate restTemplate;
  InternalService internalService;
  ObjectMapper objectMapper;

  @GetMapping("/weather/oneDay")
  public Root getWeather(@RequestParam String lat, @RequestParam String lon) throws Exception {
    return internalService.getWeather(lat, lon);
  }

  @GetMapping("/logistics/status")
  public List<RestockSupply> getStatus() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      ResponseEntity<List<RestockSupply>> userEntity =
          restTemplate.exchange(
              "http://logistics-service/restockSupply",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<RestockSupply>>() {});

      log.debug("User service response: {}", userEntity.getBody());
      return userEntity.getBody();
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return Collections.emptyList();
    }
  }

  @PostMapping("/logistics/status")
  public RestockSupply requestRestock(
      @RequestBody @Valid RestockSupply restockSupply, BindingResult bindResult) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    ResponseEntity<RestockSupply> userEntity;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(restockSupply), headers);
      userEntity =
          restTemplate.exchange(
              "http://logistics-service/restockSupply",
              HttpMethod.POST,
              entity,
              RestockSupply.class);
    } catch (JsonProcessingException e) {
      log.error("Json processing error", e);
      return null;
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return null;
    }
    log.debug("User service response: {}", userEntity.getBody());
    return userEntity.getBody();
  }

  @GetMapping("/flightSchedule/departure")
  public List<FlightControlRequest> getDepartures() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      ResponseEntity<List<FlightControlRequest>> flightSchedule =
          restTemplate.exchange(
              "http://flight-schedule-service/flight/control/departure",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<FlightControlRequest>>() {});
      log.debug("Ticket service response: {}", flightSchedule.getBody());
      return flightSchedule.getBody();
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return Collections.emptyList();
    }
  }

  @GetMapping("/flightSchedule/arrival")
  public List<FlightControlRequest> getArrivals() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      ResponseEntity<List<FlightControlRequest>> flightSchedule =
          restTemplate.exchange(
              "http://flight-schedule-service/flight/control/arrival",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<FlightControlRequest>>() {});
      log.debug("Flight service response: {}", flightSchedule.getBody());
      return flightSchedule.getBody();
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return Collections.emptyList();
    }
  }

  @GetMapping("/logistics/restockSupply")
  public List<RestockSupply> loadSupplyDelivery() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      ResponseEntity<List<RestockSupply>> restockSupply =
          restTemplate.exchange(
              "http://logistics-service/restockSupply",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<RestockSupply>>() {});
      log.debug("Logistics service response: {}", restockSupply.getBody());
      return restockSupply.getBody();
    } catch (RestClientException e) {
      log.error("exchange exception, e:", e);
      return Collections.emptyList();
    }
  }

  @PostMapping("/logistics/restockSupply")
  public List<RestockSupply> changeSupply(@RequestBody List<RestockSupply> supplies) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    ResponseEntity<List<RestockSupply>> restockSupply;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(supplies), headers);
      restockSupply =
          restTemplate.exchange(
              "http://logistics-service/restockSupply",
              HttpMethod.POST,
              entity,
              new ParameterizedTypeReference<List<RestockSupply>>() {});
    } catch (JsonProcessingException e) {
      log.error("Json processing error", e);
      return Collections.emptyList();
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return Collections.emptyList();
    }
    log.debug("Supply service response: {}", restockSupply.getBody());
    return restockSupply.getBody();
  }

  @PutMapping("/flightcontrol")
  public Flight updateFlight(@RequestBody Flight flight) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    ResponseEntity<Flight> flightResp;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(flight), headers);
      flightResp =
          restTemplate.exchange(
              "http://flight-schedule-service/flight", HttpMethod.PUT, entity, Flight.class);
    } catch (JsonProcessingException e) {
      log.error("Json processing error", e);
      throw new Exception("Invalid json");
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      throw new Exception("Communication error");
    }
    log.debug("Supply service response: {}", flightResp.getBody());
    return flightResp.getBody();
  }

  @PostMapping("/flightcontrol")
  public FlightControlRequest addFlight(@RequestBody FlightControlRequest flight) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    ResponseEntity<FlightControlRequest> flightResp;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(flight), headers);
      flightResp =
          restTemplate.exchange(
              "http://flight-schedule-service/flight",
              HttpMethod.POST,
              entity,
              FlightControlRequest.class);
    } catch (JsonProcessingException e) {
      log.error("Json processing error", e);
      throw new Exception("Invalid json");
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      throw new Exception("Communication error");
    }
    log.debug("Supply service response: {}", flightResp.getBody());
    return flightResp.getBody();
  }

  @PostMapping("/flight")
  public FlightInput addFlight(@RequestBody Flight flight) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    ResponseEntity<FlightInput> flightResp;

    FlightInput flightInput =
        new FlightInput(
            flight.getIdPlane(),
            flight.getIdFlightSchedule(),
            flight.getDelay(),
            flight.getStatus(),
            flight.getRunway());

    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(flightInput), headers);
      flightResp =
          restTemplate.exchange(
              "http://flight-schedule-service/flight", HttpMethod.POST, entity, FlightInput.class);
    } catch (JsonProcessingException e) {
      log.error("Json processing error", e);
      throw new Exception("Invalid json");
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      throw new Exception("Communication error");
    }
    log.debug("Supply service response: {}", flightResp.getBody());
    return flightResp.getBody();
  }
}
