package com.pk.frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.frontend.model.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@AllArgsConstructor
public class FrontController {
  RestTemplate restTemplate;
  ObjectMapper objectMapper;

  @GetMapping("/schedule")
  public String loadSchedule(Model model) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      ResponseEntity<List<FlightControlRequest>> arrivals =
          restTemplate.exchange(
              "http://internal-entry-service/flightSchedule/arrival",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<FlightControlRequest>>() {});
      ResponseEntity<List<FlightControlRequest>> departures =
          restTemplate.exchange(
              "http://internal-entry-service/flightSchedule/departure",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<FlightControlRequest>>() {});
      log.debug("Flight service response: {}", arrivals.getBody());
      log.debug("Flight service response: {}", departures.getBody());
      model.addAttribute("arrivals", arrivals.getBody());
      model.addAttribute("departures", departures.getBody());
      return "schedule";
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      // FIXME error page
      return "ERROR_PAGE";
    }
  }

  @GetMapping("/flight-control")
  public String loadFlightControl(Model model) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      ResponseEntity<List<FlightControlRequest>> arrivals =
          restTemplate.exchange(
              "http://internal-entry-service/flightSchedule/arrival",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<FlightControlRequest>>() {});
      ResponseEntity<List<FlightControlRequest>> departures =
          restTemplate.exchange(
              "http://internal-entry-service/flightSchedule/departure",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<FlightControlRequest>>() {});
      log.debug("Flight service response: {}", arrivals.getBody());
      log.debug("Flight service response: {}", departures.getBody());
      model.addAttribute("arrivals", arrivals.getBody());
      model.addAttribute("departures", departures.getBody());
      return "flight-control";
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      // FIXME error page
      return "ERROR_PAGE";
    }
  }

  @GetMapping("flight-creator")
  public String loadFlightCreator(Model model) {
    return "flight-creator";
  }

  @GetMapping("/")
  public String loadIndex(Model model) {
    return "index";
  }

  @GetMapping("/order-supply-delivery")
  public String loadOrderSupplyDelivery(Model model) {
    try {
      ResponseEntity<List<RestockSupply>> supplies =
          restTemplate.exchange(
              "http://internal-entry-service/logistics/restockSupply",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<RestockSupply>>() {});
      log.debug("Logistics service response: {}", supplies.getBody());
      model.addAttribute("supplies", supplies.getBody());
      return "order-supply-delivery";
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      // FIXME error page
      return "ERROR_PAGE";
    }
  }

  @GetMapping("/reservation")
  public String loadReservation(Model model) {
    return "reservation";
  }

  @GetMapping("/plane-restock-supply")
  public String loadPlaneRestockSupply(Model model) {
    try {
      ResponseEntity<List<RestockSupply>> supplies =
          restTemplate.exchange(
              "http://internal-entry-service/logistics/restockSupply",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<RestockSupply>>() {});
      log.debug("Logistics service response: {}", supplies.getBody());
      model.addAttribute("supplies", supplies.getBody());
      return "plane-restock-supply";
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      // FIXME error page
      return "ERROR_PAGE";
    }
  }

  @GetMapping("/restock-supply")
  public String loadRestockSupply(Model model) {
    try {
      ResponseEntity<List<RestockSupply>> supplies =
          restTemplate.exchange(
              "http://internal-entry-service/logistics/restockSupply",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<RestockSupply>>() {});
      log.debug("Logistics service response: {}", supplies.getBody());
      model.addAttribute("supplies", supplies.getBody());
      return "restock-supply";
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      // FIXME error page
      return "ERROR_PAGE";
    }
  }

  @GetMapping("/service-form")
  public String loadServiceForm(Model model) {
    return "service-form";
  }

  @GetMapping("/tickets")
  public String loadTickets(Model model) {
    try {
      ResponseEntity<List<Ticket>> tickets =
          restTemplate.exchange(
              "http://internal-entry-service/ticket",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<Ticket>>() {});
      log.debug("Tickets service response: {}", tickets.getBody());
      model.addAttribute("tickets", tickets.getBody());
      return "tickets";
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      // FIXME error page
      return "ERROR_PAGE";
    }
  }

  @GetMapping("/users-management")
  public String loadUsersManagement(Model model) {
    try {
      ResponseEntity<List<User>> users =
          restTemplate.exchange(
              "http://internal-entry-service/management/allUsers",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<User>>() {});
      log.debug("Users service response: {}", users.getBody());
      System.out.println(users.getBody());
      model.addAttribute("users", users.getBody());
      return "users-management";
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      // FIXME error page
      return "ERROR_PAGE";
    }
  }

  @GetMapping("/weather")
  public String loadWeather(Model model) {
    return "weather";
  }

  @GetMapping("login")
  public String loadLogin(Model model) {
    return "login-page";
  }

  @GetMapping("/register")
  public String loadRegister(Model model) {
    return "register-page";
  }

  @PostMapping("/restock-supply")
  public void updateRestockSupply(@RequestBody List<RestockSupply> restockSupply) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(restockSupply), headers);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new Exception("restockSupply data is malformed");
    }
    ResponseEntity<List<RestockSupply>> response =
        restTemplate.exchange(
            "http://internal-entry-service/logistics/restockSupply",
            HttpMethod.POST,
            entity,
            new ParameterizedTypeReference<List<RestockSupply>>() {});
    if (response.getBody() == null) {
      throw new Exception("body is null, internal service malfunctioning");
    }
  }

  @PutMapping("/flight-control")
  public void updateFlightControl(@RequestBody List<FlightControlRequest> flightControlRequest)
      throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    for (FlightControlRequest req : flightControlRequest) {
      try {
        entity = new HttpEntity<>(objectMapper.writeValueAsString(req), headers);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        throw new Exception("flightControlRequest data is malformed");
      }
      ResponseEntity<AuthResp> response =
          restTemplate.exchange(
              "http://internal-entry-service/flightcontrol",
              HttpMethod.PUT,
              entity,
              AuthResp.class);
      if (response.getBody() == null) {
        throw new Exception("body is null, internal service malfunctioning");
      }
    }
  }

  @PostMapping("/login")
  public AuthResp login(@RequestBody LoginData loginData) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(loginData), headers);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new Exception("Login data is malformed");
    }
    ResponseEntity<AuthResp> response =
        restTemplate.exchange(
            "http://internal-entry-service/new/login", HttpMethod.POST, entity, AuthResp.class);
    if (response.getBody() == null) {
      throw new Exception("body is null, internal service malfunctioning");
    }
    return response.getBody();
  }

  @PostMapping("/register")
  public ErrMsg register(@RequestBody User user) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new Exception("User is malformed");
    }
    ResponseEntity<ErrMsg> response =
        restTemplate.exchange(
            "http://internal-entry-service/new/register", HttpMethod.POST, entity, ErrMsg.class);
    if (response.getBody() == null) {
      throw new Exception("body is null, internal service malfunctioning");
    }
    return response.getBody();
  }

  @PutMapping("/users-management")
  public void updateUsers(@RequestBody List<User> users) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    for (User user : users) {
      try {
        entity = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        throw new Exception("Updated users are malformed");
      }
      ResponseEntity<User> response =
          restTemplate.exchange(
              "http://internal-entry-service/management/user", HttpMethod.PUT, entity, User.class);
      log.info("Got response: {}", response.getBody());
      if (response.getBody() == null) {
        throw new Exception("body is null, internal service malfunctioning");
      }
    }
  }

  @PostMapping("/flight-creator")
  public void createFlight(@RequestBody Flight flight) {

  }
}
