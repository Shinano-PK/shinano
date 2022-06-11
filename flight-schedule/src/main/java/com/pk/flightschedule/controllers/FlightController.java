package com.pk.flightschedule.controllers;

import com.pk.flightschedule.models.Flight;
import com.pk.flightschedule.models.FlightControlRequest;
import com.pk.flightschedule.models.FlightInput;
import com.pk.flightschedule.services.FlightService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class FlightController {
  private FlightService flightService;

  @GetMapping("/flight/check")
  public Boolean checkFlightExists(Integer id) {
    return flightService.getFlightById(id) == null;
  }

  @GetMapping("/flight/control/departure")
  public List<FlightControlRequest> getDeparture(FlightControlRequest input) {
    return flightService.getPlaneControlDeparture(Date.valueOf(LocalDate.now()));
  }

  @GetMapping("/flight/control/arrival")
  public List<FlightControlRequest> getArrival() {
    return flightService.getPlaneControlArrival(Date.valueOf(LocalDate.now()));
  }

  @GetMapping("/flight")
  public Flight getPlane(Integer id) {
    return flightService.getFlightById(id);
  }

  @PostMapping("/flight")
  public Integer saveFlight(@Valid FlightInput input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return flightService.saveFlight(input);
  }

  @PutMapping("/flight")
  public Boolean updateFlight(@Valid Flight input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return flightService.updateFlight(input);
  }

  @DeleteMapping("/flight")
  public Boolean deleteFlight(Integer id) {
    return flightService.deleteFlight(id);
  }

  private void validateInput(BindingResult bindingResult) {
    if (!bindingResult.hasErrors()) {
      return;
    }
    log.debug(bindingResult.getAllErrors().toString());
    StringBuilder stringBuilder = new StringBuilder();
    for (ObjectError error : bindingResult.getAllErrors()) {
      stringBuilder.append("Error: ");
      stringBuilder.append(error.getCode());
      stringBuilder.append(" ");
      stringBuilder.append(error.getDefaultMessage());
      stringBuilder.append("\n");
    }
    throw new ValidationException(stringBuilder.toString());
  }
}