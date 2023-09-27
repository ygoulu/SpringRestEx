package com.example.restex.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ExternalMockedService {

  private static final double PERCENTAGE = 10.0;
  private static final int MIN_DELAY_MS = 100;
  private static final int MAX_DELAY_MS = 1000;
  private static final double FAILURE_PROBABILITY = 0.1;

  private final Random random = new Random();

  public Double getPercentage() throws Exception {
    try {
      Thread.sleep(random.nextInt(MAX_DELAY_MS - MIN_DELAY_MS) + MIN_DELAY_MS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    if (random.nextDouble() < FAILURE_PROBABILITY) {
      throw new Exception("Failed to obtain percentage value from external service");
    }
    return PERCENTAGE;
  }

}