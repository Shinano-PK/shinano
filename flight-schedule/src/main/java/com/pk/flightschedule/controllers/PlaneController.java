package com.pk.flightschedule.controllers;

import com.pk.flightschedule.models.Plane;
import com.pk.flightschedule.services.PlaneService;
import javax.validation.Valid;
import javax.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class PlaneController {
  private PlaneService planeService;

  @GetMapping("/plane/check")
  public Boolean checkPlaneExists(@RequestParam String id) {
    return planeService.getPlaneById(id) == null;
  }

  @GetMapping("/plane")
  public Plane getPlane(@RequestParam String id) {
    return planeService.getPlaneById(id);
  }

  @PostMapping("/plane")
  public String saveController(@Valid @RequestBody Plane input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return planeService.savePlane(input);
  }

  @PutMapping("/plane")
  public Boolean updatePlane(@Valid @RequestBody Plane input, BindingResult bindingResult) throws Exception {
    validateInput(bindingResult);
    return planeService.updatePlane(input);
  }

  @DeleteMapping("/plane")
  public Boolean deletePlane(@RequestParam String id) {
    return planeService.deletePlane(id);
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
