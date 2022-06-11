package com.pk.logistics.controller;

import com.pk.logistics.model.Product;
import com.pk.logistics.model.ProductRequest;
import com.pk.logistics.service.ProductService;
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
public class ProductController {

  private ProductService productService;

  @GetMapping("/product")
  public Product getProduct(Integer id) {
    return productService.getProductById(id);
  }

  @PostMapping("/product")
  public Integer saveProduct(@Valid ProductRequest input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return productService.saveProduct(input);
  }

  @PutMapping("/product")
  public Boolean updateProduct(@Valid Product input, BindingResult bindingResult) {
    validateInput(bindingResult);
    return productService.updateProduct(input);
  }

  @DeleteMapping("/product")
  public Boolean deleteProduct(Integer id) {
    return productService.deleteProduct(id);
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
