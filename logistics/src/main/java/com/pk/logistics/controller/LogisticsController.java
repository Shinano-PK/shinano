package com.pk.logistics.controller;

import com.pk.logistics.model.RestockSupply;
import com.pk.logistics.service.LogisticsService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LogisticsController {
  private LogisticsService logisticsService;

  @GetMapping("/restockSupply")
  public List<RestockSupply> getRestockResuply() {
    return logisticsService.getRestockResuply();
  }

  @PostMapping("/restockSupply")
  public List<RestockSupply> postRestockResuply(@RequestBody List<RestockSupply> list) {
    return logisticsService.resupply(list);
  }
}
