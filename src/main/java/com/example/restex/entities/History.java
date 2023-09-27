package com.example.restex.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class History {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double num1;

  private Double num2;

  private Double percentage;

  private Double result;

  private LocalDateTime timestamp;

  public History() {
    this.timestamp = LocalDateTime.now();
  }

  public History(Double num1, Double num2, Double percentage, Double result) {
    this.num1 = num1;
    this.num2 = num2;
    this.percentage = percentage;
    this.result = result;
    this.timestamp = LocalDateTime.now();
  }
}