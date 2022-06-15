package com.pk.frontend.controller;

import com.pk.frontend.model.*;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@AllArgsConstructor
public class FrontController {
  RestTemplate restTemplate;

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

  @PostMapping("/restockSupply")
  public void updateRestockSupply(@RequestBody List<RestockSupply> restockSupply){

  }

  @PostMapping("/flightControl")
  public void updateFlightControl(@RequestBody List<FlightControlRequest> flightControlRequest){

  }

  @PostMapping("/login")
  public AuthResp login(@RequestBody LoginData loginData){

  }

  @PostMapping("/register")
  public ErrMsg updateRestockSupply(@RequestBody User user){

  }

  @PostMapping("/users-management")
  public void updateUsers(@RequestBody List<User> users) {

  }
}
