package com.pk.flightschedule.controllers;

import com.pk.flightschedule.models.FlightSchedule;
import com.pk.flightschedule.models.FlightScheduleInput;
import com.pk.flightschedule.services.FlightScheduleService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class FlightScheduleController {
  private FlightScheduleService flightScheduleService;

  @GetMapping("/flightSchedule")
  public FlightSchedule getFlightSchedule(@RequestParam Integer id) {
    return flightScheduleService.getFlightScheduleById(id);
  }

  @GetMapping("/flightSchedules")
  public List<FlightSchedule> getFlightSchedules() {
    return flightScheduleService.getFlightSchedules();
  }

  @PostMapping("/flightSchedule")
  public FlightSchedule saveFlightSchedule(
      @Valid @RequestBody FlightScheduleInput input, BindingResult bindingResult) throws Exception {
    validateInput(bindingResult);
    return flightScheduleService.saveFlightSchedule(input);
  }

  @PutMapping("/flightSchedule")
  public Boolean updateFlightSchedule(
      @Valid @RequestBody FlightSchedule input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return flightScheduleService.updateFlightSchedule(input);
  }

  @DeleteMapping("/flightSchedule")
  public Boolean deleteFlightSchedule(@RequestParam Integer id) {
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
