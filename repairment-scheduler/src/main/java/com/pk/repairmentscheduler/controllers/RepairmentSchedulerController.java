package com.pk.repairmentscheduler.controllers;

import com.pk.repairmentscheduler.models.RepairmentScheduler;
import com.pk.repairmentscheduler.models.RepairmentSchedulerInput;
import com.pk.repairmentscheduler.services.RepairmentSchedulerService;
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
public class RepairmentSchedulerController {
  private RepairmentSchedulerService repairmentSchedulerService;

  @GetMapping("/repairmentScheduler")
  public RepairmentScheduler getRepairmentScheduler(@RequestParam Integer id) {
    return repairmentSchedulerService.getRepairmentSchedulerById(id);
  }

  @GetMapping("/repairmentSchedulerByPlaneAndUser")
  public List<RepairmentScheduler> getRepairmentSchedulerByPlaneAndUser(
      @RequestParam String idPlane, @RequestParam Integer idUser) {
    return repairmentSchedulerService.getRepairmentSchedulerByPlaneAndUser(idPlane, idUser);
  }

  @PostMapping("/repairmentScheduler")
  public Integer saveRepairmentScheduler(
      @Valid @RequestBody RepairmentSchedulerInput input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return repairmentSchedulerService.saveRepairmentScheduler(input);
  }

  @PutMapping("/repairmentScheduler")
  public Boolean updateRepairmentScheduler(
      @Valid @RequestBody RepairmentScheduler input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return repairmentSchedulerService.updateRepairmentScheduler(input);
  }

  @DeleteMapping("/repairmentScheduler")
  public Boolean deleteRepairmentScheduler(@RequestParam Integer id) {
    return repairmentSchedulerService.deleteRepairmentScheduler(id);
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
