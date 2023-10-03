package com.example.restex.unit.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.restex.controllers.SumController;
import com.example.restex.entities.History;
import com.example.restex.entities.Operation;
import com.example.restex.enums.Endpoint;
import com.example.restex.enums.HTTPMessage;
import com.example.restex.exception.ExternalServiceException;
import com.example.restex.exception.TooManyRequestsException;
import com.example.restex.repositories.HistoryRepository;
import com.example.restex.repositories.OperationRepository;
import com.example.restex.services.ExternalMockedService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SumControllerTest {

  private SumController sumController;

  @Mock
  private ExternalMockedService externalService;

  @Mock
  private OperationRepository operationRepository;

  @Mock
  private HistoryRepository historyRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    sumController = new SumController(externalService, operationRepository, historyRepository);
  }

  @Test
  public void testSumEndpoint() {
    Double first = 2.0;
    Double second = 3.0;
    Double percentage = 0.5;
    Double expectedResult = (first + second) * (1 + percentage / 100);
    Operation newOperation = new Operation(first, second, percentage, expectedResult);
    History history = new History(Endpoint.SUM, newOperation.getTimestamp(), newOperation);

    when(externalService.getPercentageService()).thenReturn(percentage);
    when(operationRepository.save(newOperation)).thenReturn(newOperation);
    when(historyRepository.save(history)).thenReturn(history);

    ResponseEntity<Double> actualResponse = sumController.sum(first, second);

    assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(actualResponse.getBody()).isEqualTo(expectedResult);
  }

  @Test
  public void testSumEndpointWithExternalServiceException() {
    Double first = 2.0;
    Double second = 3.0;
    ExternalServiceException ex = new ExternalServiceException("External service error");
    Operation lastOperation = new Operation(1.0, 2.0, 0.0, 3.0);
    when(externalService.getPercentageService()).thenThrow(ex);
    when(operationRepository.findLastHistory()).thenReturn(lastOperation);
    when(historyRepository.save(new History(Endpoint.SUM, LocalDateTime.now(), lastOperation))).thenReturn(new History(Endpoint.SUM, LocalDateTime.now(), lastOperation));

    ResponseEntity<Double> actualResponse = sumController.sum(first, second);

    assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(actualResponse.getBody()).isEqualTo(lastOperation.getResult());
  }

  @Test
  public void testSumEndpointWithExternalServiceExceptionAndNoLastOperation() {
    Double first = 2.0;
    Double second = 3.0;
    ExternalServiceException ex = new ExternalServiceException("External service error");
    when(externalService.getPercentageService()).thenThrow(ex);
    when(operationRepository.findLastHistory()).thenReturn(null);
    when(historyRepository.save(new History(Endpoint.SUM, LocalDateTime.now(), ex))).thenReturn(new History(Endpoint.SUM, LocalDateTime.now(), ex));

    try {
      sumController.sum(first, second);
    } catch (ExternalServiceException e) {
      assertThat(e.getMessage()).isEqualTo("External service error");
    }
  }

  @Test
  public void testSumEndpointRateLimiting() {
    Double first = 2.0;
    Double second = 3.0;
    ExternalServiceException ex = new ExternalServiceException("External service error");
    Operation lastOperation = new Operation(1.0, 2.0, 0.0, 3.0);
    when(externalService.getPercentageService()).thenThrow(ex);
    when(operationRepository.findLastHistory()).thenReturn(lastOperation);
    when(historyRepository.save(new History(Endpoint.SUM, LocalDateTime.now(), lastOperation))).thenReturn(new History(Endpoint.SUM, LocalDateTime.now(), lastOperation));

    TooManyRequestsException tmrExc = null;
    try {
      sumController.sum(first, second);
      sumController.sum(first, second);
      sumController.sum(first, second);
      sumController.sum(first, second);
    } catch (TooManyRequestsException exception) {
      tmrExc = exception;
    }

    assertThat(tmrExc).isNotNull();
    assertThat(tmrExc.getMessage()).isEqualTo("0 available tokens of 3 per minute");
  }

  @Test
  public void testHistoryEndpoint() {
    List<History> histories = new ArrayList<>();
    histories.add(new History(Endpoint.SUM, LocalDateTime.now(), new Operation(1.0, 2.0, 0.0, 3.0)));
    when(historyRepository.findAllWithOperation(Pageable.unpaged())).thenReturn(new PageImpl<>(histories));

    ResponseEntity<Page<History>> actualResponse = sumController.history(Pageable.unpaged());

    assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(actualResponse.getBody().getContent()).isEqualTo(histories);
  }

  @Test
  public void testHistoryEndpointRateLimiting() {
    List<History> histories = new ArrayList<>();
    histories.add(new History(Endpoint.SUM, LocalDateTime.now(), new Operation(1.0, 2.0, 0.0, 3.0)));
    when(historyRepository.findAllWithOperation(Pageable.unpaged())).thenReturn(new PageImpl<>(histories));

    TooManyRequestsException tmrExc = null;
    try {
      sumController.history(Pageable.unpaged());
      sumController.history(Pageable.unpaged());
      sumController.history(Pageable.unpaged());
      sumController.history(Pageable.unpaged());
    } catch (TooManyRequestsException exception) {
      tmrExc = exception;
    }

    assertThat(tmrExc).isNotNull();
    assertThat(tmrExc.getMessage()).isEqualTo("0 available tokens of 3 per minute");  }
}