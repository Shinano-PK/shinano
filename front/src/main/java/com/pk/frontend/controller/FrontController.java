package com.pk.frontend.controller;

import com.pk.frontend.model.FlightControlRequest;
import com.pk.frontend.model.Ticket;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@AllArgsConstructor
public class FrontController {
  RestTemplate restTemplate;

  @GetMapping("/schedule")
  public String loadSchedule(Model model) {

    // wywołanie do backendu
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
    return "flight-control";
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
      ResponseEntity<List<FlightControlRequest>> supplies =
          restTemplate.exchange(
              "http://internal-entry-service/logistics/restockSupply",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<FlightControlRequest>>() {});
      log.debug("Logistics service response: {}", supplies.getBody());
      // TODO do sth with it
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
      ResponseEntity<List<FlightControlRequest>> supplies =
          restTemplate.exchange(
              "http://internal-entry-service/logistics/restockSupply",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<FlightControlRequest>>() {});
      log.debug("Logistics service response: {}", supplies.getBody());
      // TODO do sth with it
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
      ResponseEntity<List<FlightControlRequest>> supplies =
          restTemplate.exchange(
              "http://internal-entry-service/logistics/restockSupply",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<FlightControlRequest>>() {});
      log.debug("Logistics service response: {}", supplies.getBody());
      // TODO do sth with it
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
      // TODO do sth with it
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
      ResponseEntity<List<Ticket>> tickets =
          restTemplate.exchange(
              "http://internal-entry-service/management/allUsers",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<Ticket>>() {});
      log.debug("Users service response: {}", tickets.getBody());
      // TODO do sth with it
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
}
