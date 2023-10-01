package com.example.restex.services;

import com.example.restex.exception.ExternalServiceException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class ExternalMockedService {
  private static final int MIN_DELAY_MS = 100;
  private static final int MAX_DELAY_MS = 1000;
  private static final double FAILURE_PROBABILITY = 0.1;
  private static final String CACHE_KEY = "percentage";
  private final Cache<String, Double> cache;
  private final Random random;

  public ExternalMockedService() {
    this.cache = CacheBuilder.newBuilder()
        .expireAfterWrite(30, TimeUnit.MINUTES) // Cache for 30 minutes
        .build();
    this.random = new Random();
  }

  //Method that returns the percentage value from an external service, using a cache to avoid unnecessary calls.
  public Double getPercentageService() throws ExternalServiceException {
    Double percentage = this.cache.getIfPresent(CACHE_KEY);
    if (percentage == null) {
      percentage = getPercentageMocked();
      this.cache.put(CACHE_KEY, percentage);
    }
    return percentage;
  }

  //Mock method that imitates a call to an external service, including a random delay and a random failure probability.
  @Retryable(maxAttempts = 3, include = {ExternalServiceException.class})
  private Double getPercentageMocked() throws ExternalServiceException {
    try {
      Thread.sleep(this.random.nextInt(MAX_DELAY_MS - MIN_DELAY_MS) + MIN_DELAY_MS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    if (this.random.nextDouble() < FAILURE_PROBABILITY) {
      throw new ExternalServiceException("Failed to obtain percentage value from external service");
    }
    return this.random.nextDouble(100);
  }

}