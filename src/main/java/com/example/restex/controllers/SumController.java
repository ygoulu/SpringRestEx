package com.example.restex.controllers;

import com.example.restex.entities.Operation;
import com.example.restex.exception.ExternalServiceException;
import com.example.restex.repositories.OperationRepository;
import com.example.restex.services.ExternalMockedService;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SumController {

  private final ExternalMockedService externalService;
  private final OperationRepository operationRepository;
  private final RateLimiter rateLimiter;

  public SumController(ExternalMockedService externalService, OperationRepository operationRepository) {
    this.externalService = externalService;
    this.operationRepository = operationRepository;
    this.rateLimiter = RateLimiter.create(0.05); // 3 requests per minute
  }

  @GetMapping("/sum")
  public ResponseEntity<Double> sum(@RequestParam Double first, @RequestParam Double second) throws Exception {
    if (!rateLimiter.tryAcquire()) {
      return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
    }

    Double percentage;
    Double result;
    try {
      percentage = externalService.getPercentageService();

      result = (first + second) * (1 + percentage / 100);
      operationRepository.save(new Operation(first, second, percentage, result));
      return ResponseEntity.ok(result);

    } catch (ExternalServiceException ex) {
      Operation lastOperation = operationRepository.findLastHistory();

      if (lastOperation == null){
        throw ex;
      }

      result = lastOperation.getResult();
      return ResponseEntity.ok(result);
    }

  }
}