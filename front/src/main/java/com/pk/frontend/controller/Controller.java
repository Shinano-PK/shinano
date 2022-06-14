package com.pk.frontend.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class Controller {

  @GetMapping
  public String loadSchedule(Model model) {

    //wywołanie do backendu

    return "schedule";
  }

  @GetMapping
  public String loadFlightControl(Model model) {

    //wywołanie do backendu

    return "flight-control";
  }

  @GetMapping
  public String loadFlightCreator(Model model) {

    //wywołanie do backendu

    return "flight-creator";
  }

  @GetMapping
  public String loadIndex(Model model) {

    //wywołanie do backendu

    return "index";
  }

  @GetMapping
  public String loadLogs(Model model) {

    //wywołanie do backendu

    return "logs";
  }

  @GetMapping
  public String loadOrderSupplyDelivery(Model model) {

    //wywołanie do backendu

    return "order-supply-selivery";
  }

  @GetMapping
  public String loadPlaneRestockSupply(Model model) {

    //wywołanie do backendu

    return "plane-restock-supply";
  }

  @GetMapping
  public String loadReservation(Model model) {

    //wywołanie do backendu

    return "reservation";
  }

  @GetMapping
  public String loadRestockSupply(Model model) {

    //wywołanie do backendu

    return "restock-supply";
  }

  @GetMapping
  public String loadServiceForm(Model model) {

    //wywołanie do backendu

    return "service-form";
  }

  @GetMapping
  public String loadTickets(Model model) {

    //wywołanie do backendu

    return "tickets";
  }

  @GetMapping
  public String loadUsersManagement(Model model) {

    //wywołanie do backendu

    return "users-management";
  }

  @GetMapping
  public String loadWeather(Model model) {

    //wywołanie do backendu

    return "weather";
  }

}
