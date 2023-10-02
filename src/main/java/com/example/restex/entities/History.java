package com.example.restex.entities;

import com.example.restex.enums.Endpoint;
import com.example.restex.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class History {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Endpoint endpoint;

  private LocalDateTime timestamp;

  private Status status;

  private Exception exception;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "operation_id", nullable = true)
  private Operation operation;

  public History() {
    this.timestamp = LocalDateTime.now();
  }

  //History for a succeed operation call
  public History(Endpoint endpoint, LocalDateTime timestamp, Operation operation) {
    this.endpoint = endpoint;
    this.timestamp = timestamp;
    this.status = Status.SUCCEED;
    this.operation = operation;
  }

  //History for a succeed history call
  public History(Endpoint endpoint, LocalDateTime timestamp) {
    this.endpoint = endpoint;
    this.timestamp = timestamp;
    this.status = Status.SUCCEED;
  }

  //History for a failed call
  public History(Endpoint endpoint, LocalDateTime timestamp, Exception exception) {
    this.endpoint = endpoint;
    this.timestamp = timestamp;
    this.status = Status.FAILED;
    this.exception = exception;
  }
}