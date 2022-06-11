package com.pk.flightschedule.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.ValidationException;

import com.pk.flightschedule.models.FlightSchedule;
import com.pk.flightschedule.models.FlightScheduleInput;
import com.pk.flightschedule.models.FlightScheduleRequest;
import com.pk.flightschedule.services.FlightScheduleService;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
public class FlightScheduleController {
  private FlightScheduleService flightScheduleService;

  @GetMapping("/flightScheduleTest")
  public String test(@RequestParam String temp) {
    return "Request handled " + temp + "\n";
  }

  @GetMapping("/flightSchedule")
  public FlightSchedule getFlightSchedule(Integer id) {
    return flightScheduleService.getFlightScheduleById(id);
  }

  @GetMapping("/flightSchedulePeriod")
  public List<FlightSchedule> getFlightSchedulePeriod(@Valid FlightScheduleRequest request,
      BindingResult bindingResult) {
    validateInput(bindingResult);
    return flightScheduleService.getFlightScheduleForDates(request);
  }

  @PostMapping("/flightSchedule")
  public Integer saveFlightSchedule(@Valid FlightScheduleInput input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return flightScheduleService.saveFlightSchedule(input);
  }

  @PutMapping("/flightSchedule")
  public Boolean updateFlightSchedule(@Valid FlightSchedule input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return flightScheduleService.updateFlightSchedule(input);
  }

  @DeleteMapping("/flightSchedule")
  public Boolean deleteFlightSchedule(Integer id) {
    return flightScheduleService.deleteFlightSchedule(id);
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
