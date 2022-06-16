package com.pk.workschedule.controller;

import com.pk.workschedule.model.WorkSchedule;
import com.pk.workschedule.model.WorkScheduleInput;
import com.pk.workschedule.service.WorkScheduleService;
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
public class WorkScheduleController {
  private WorkScheduleService flightScheduleService;

  @GetMapping("/workSchedule")
  public WorkSchedule getWorkSchedule(@RequestParam Integer id) {
    return flightScheduleService.getWorkScheduleById(id);
  }

  @PostMapping("/workSchedule")
  public Integer saveWorkSchedule(@Valid @RequestBody WorkScheduleInput input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return flightScheduleService.saveWorkSchedule(input);
  }

  @PutMapping("/workSchedule")
  public Boolean updateWorkSchedule(@Valid @RequestBody WorkSchedule input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return flightScheduleService.updateWorkSchedule(input);
  }

  @DeleteMapping("/workSchedule")
  public Boolean deleteWorkSchedule(@RequestParam Integer id) {
    return flightScheduleService.deleteWorkSchedule(id);
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
