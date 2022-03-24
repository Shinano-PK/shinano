package com.pk.flightschedule.controllers;

import java.util.List;

import javax.validation.ValidationException;

import com.pk.flightschedule.models.FlightSchedule;
import com.pk.flightschedule.models.FlightScheduleInput;
import com.pk.flightschedule.models.FlightScheduleRequest;
import com.pk.flightschedule.services.FlightScheduleService;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
public class FlightScheduleController {
  private FlightScheduleService flightScheduleService;

  @GetMapping("/flightSchedule")
  public List<FlightSchedule> getFlightSchedule(FlightScheduleRequest request,
      BindingResult bindingResult) {
    validateInput(bindingResult);
    return flightScheduleService.getFlightScheduleForDates(request);
  }

  public Integer saveFlightSchedule(FlightScheduleInput input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return flightScheduleService.saveFlightSchedule(input);
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
