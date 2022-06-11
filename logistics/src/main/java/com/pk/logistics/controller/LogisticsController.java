package com.pk.logistics.controller;

import com.pk.logistics.model.RestockSupply;
import com.pk.logistics.service.LogisticsService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogisticsController {
  private LogisticsService logisticsService;

  @GetMapping("/restockSupply")
  public List<RestockSupply> getRestockResuply() {
    return logisticsService.getRestockResuply();
  }

  @PostMapping("/restockSupply")
  public List<RestockSupply> postRestockResuply(List<RestockSupply> list) {
    return logisticsService.resupply(list);
  }
}
