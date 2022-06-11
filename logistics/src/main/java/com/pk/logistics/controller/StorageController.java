package com.pk.logistics.controller;

import com.pk.logistics.model.Storage;
import com.pk.logistics.model.StorageRequest;
import com.pk.logistics.service.StorageService;
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
@AllArgsConstructor
@Slf4j
public class StorageController {

  private StorageService storageService;

  @GetMapping("/storage")
  public Storage getProduct(Integer id) {
    return storageService.getStorageById(id);
  }

  @PostMapping("/storage")
  public Integer saveProduct(@Valid StorageRequest input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return storageService.saveStorage(input);
  }

  @PutMapping("/storage")
  public Boolean updateProduct(@Valid Storage input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return storageService.updateStorage(input);
  }

  @DeleteMapping("/storage")
  public Boolean deleteProduct(Integer id) {
    return storageService.deleteStorage(id);
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
