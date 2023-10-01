package com.example.restex.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class Operation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double firstNumber;

  private Double secondNumber;

  private Double percentage;

  private Double result;

  private LocalDateTime timestamp;

  public Operation() {
    this.timestamp = LocalDateTime.now();
  }

  public Operation(Double firstNumber, Double secondNumber, Double percentage, Double result) {
    this.firstNumber = firstNumber;
    this.secondNumber = secondNumber;
    this.percentage = percentage;
    this.result = result;
    this.timestamp = LocalDateTime.now();
  }
}