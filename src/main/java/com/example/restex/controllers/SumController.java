package com.example.restex.controllers;

import com.example.restex.entities.History;
import com.example.restex.entities.Operation;
import com.example.restex.enums.Endpoint;
import com.example.restex.exception.ExternalServiceException;
import com.example.restex.repositories.HistoryRepository;
import com.example.restex.repositories.OperationRepository;
import com.example.restex.services.ExternalMockedService;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SumController {

  private final ExternalMockedService externalService;
  private final OperationRepository operationRepository;
  private final HistoryRepository historyRepository;

  public SumController(ExternalMockedService externalService, OperationRepository operationRepository, HistoryRepository historyRepository) {
    this.externalService = externalService;
    this.operationRepository = operationRepository;
    this.historyRepository = historyRepository;
  }

  @GetMapping("/sum")
  public ResponseEntity<Double> sum(@RequestParam Double first, @RequestParam Double second) {

    Double percentage;
    Double result;
    try {
      percentage = externalService.getPercentageService();

      result = (first + second) * (1 + percentage / 100);

      Operation newOperation = new Operation(first, second, percentage, result);
      operationRepository.save(newOperation);

      historyRepository.save(new History(Endpoint.SUM, newOperation.getTimestamp(), newOperation));
      return ResponseEntity.ok(result);

    } catch (ExternalServiceException ex) {
      Operation lastOperation = operationRepository.findLastHistory();

      if (lastOperation == null) {
        historyRepository.save(new History(Endpoint.SUM, LocalDateTime.now(), ex));
        throw ex;
      }

      result = lastOperation.getResult();
      historyRepository.save(new History(Endpoint.SUM, LocalDateTime.now(), lastOperation));
      return ResponseEntity.ok(result);
    }
  }

  @GetMapping("/history")
  public ResponseEntity<Page<History>> history(Pageable pageable) {
    Page<History> history = historyRepository.findAllWithOperation(pageable);
    //save history call after seeing the history
    historyRepository.save(new History(Endpoint.HISTORY, LocalDateTime.now()));
    return ResponseEntity.ok(history);
  }
}