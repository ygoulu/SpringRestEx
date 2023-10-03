package com.example.restex.unit.entities;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.restex.entities.History;
import com.example.restex.entities.Operation;
import com.example.restex.enums.Endpoint;
import com.example.restex.enums.Status;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class HistoryTest {

  @Test
  public void testConstructorWithOperation() {
    LocalDateTime timestamp = LocalDateTime.now();
    Operation operation = new Operation(2.0, 3.0, 0.5, 7.5);

    History history = new History(Endpoint.SUM, timestamp, operation);

    assertThat(history.getEndpoint()).isEqualTo(Endpoint.SUM);
    assertThat(history.getTimestamp()).isEqualTo(timestamp);
    assertThat(history.getStatus()).isEqualTo(Status.SUCCEED);
    assertThat(history.getOperation()).isEqualTo(operation);
  }

  @Test
  public void testConstructorWithoutOperation() {
    LocalDateTime timestamp = LocalDateTime.now();

    History history = new History(Endpoint.HISTORY, timestamp);

    assertThat(history.getEndpoint()).isEqualTo(Endpoint.HISTORY);
    assertThat(history.getTimestamp()).isEqualTo(timestamp);
    assertThat(history.getStatus()).isEqualTo(Status.SUCCEED);
    assertThat(history.getOperation()).isNull();
  }

  @Test
  public void testConstructorWithException() {
    LocalDateTime timestamp = LocalDateTime.now();
    Exception exception = new Exception("Test exception");

    History history = new History(Endpoint.SUM, timestamp, exception);

    assertThat(history.getEndpoint()).isEqualTo(Endpoint.SUM);
    assertThat(history.getTimestamp()).isEqualTo(timestamp);
    assertThat(history.getStatus()).isEqualTo(Status.FAILED);
    assertThat(history.getException()).isEqualTo(exception);
    assertThat(history.getOperation()).isNull();
  }
}