package com.example.restex.controllers;

import com.example.restex.entities.History;
import com.example.restex.repositories.HistoryRepository;
import com.example.restex.services.ExternalMockedService;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SumController {

  private final ExternalMockedService externalService;
  private final HistoryRepository historyRepository;
  private final RateLimiter rateLimiter;

  public SumController(ExternalMockedService externalService, HistoryRepository historyRepository) {
    this.externalService = externalService;
    this.historyRepository = historyRepository;
    this.rateLimiter = RateLimiter.create(0.05); // 3 requests per minute
  }

  @PostMapping("/sum")
  public ResponseEntity<Double> sum(@RequestParam Double num1, @RequestParam Double num2) throws Exception {
    if (!rateLimiter.tryAcquire()) {
      return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
    }
    Double percentage = externalService.getPercentage();
    Double result = (num1 + num2) * (1 + percentage / 100);
    historyRepository.save(new History(num1, num2, percentage, result));
    return ResponseEntity.ok(result);
  }
}