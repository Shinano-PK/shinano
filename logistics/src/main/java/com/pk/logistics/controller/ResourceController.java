package com.pk.logistics.controller;

import com.pk.logistics.model.Resource;
import com.pk.logistics.model.ResourceRequest;
import com.pk.logistics.service.ResourceService;
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
@AllArgsConstructor
@Slf4j
public class ResourceController {

  private ResourceService resourceService;

  @GetMapping("/resource")
  public Resource getResource(@RequestParam Integer id) {
    return resourceService.getResourceById(id);
  }

  @PostMapping("/resource")
  public Integer saveResource(@Valid @RequestBody ResourceRequest input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return resourceService.saveResource(input);
  }

  @PutMapping("/resource")
  public Boolean updateResource(@Valid @RequestBody Resource input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return resourceService.updateResource(input);
  }

  @DeleteMapping("/resource")
  public Boolean deleteResource(@RequestParam Integer id) {
    return resourceService.deleteResource(id);
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
