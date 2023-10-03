package com.example.restex.unit.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.restex.exception.ExternalServiceException;
import com.example.restex.services.ExternalMockedService;
import com.google.common.cache.Cache;
import java.lang.reflect.Field;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.ReflectionUtils;

public class ExternalMockedServiceTest {

  private ExternalMockedService service;

  @Mock
  private Cache<String, Double> cache;

  @Mock
  private Random random;

  //We will use ReflectionUtils to mock cache and manipulate its behaviour
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    this.service = new ExternalMockedService();
    Field cacheField = ReflectionUtils.findField(ExternalMockedService.class, "cache");
    Field randomField = ReflectionUtils.findField(ExternalMockedService.class, "random");
    cacheField.setAccessible(true);
    randomField.setAccessible(true);
    ReflectionUtils.setField(cacheField, this.service, this.cache);
    ReflectionUtils.setField(randomField, this.service, this.random);
  }

  @Test
  public void testGetPercentageServiceOk() throws ExternalServiceException {
    Double percentage = 0.5;
    when(cache.getIfPresent("percentage")).thenReturn(null);
    when(random.nextDouble()).thenReturn(0.5);
    when(random.nextDouble(100)).thenReturn(percentage);
    doNothing().when(this.cache).put("percentage", percentage);

    // Verify that the cache was used
    assertThat(this.service.getPercentageService()).isEqualTo(percentage);
  }

  @Test
  public void testGetPercentageServiceThrowException() throws ExternalServiceException {
    Double percentage = 0.5;
    when(cache.getIfPresent("percentage")).thenReturn(null);
    when(random.nextDouble()).thenReturn(0.05);
    doNothing().when(this.cache).put("percentage", percentage);

    try {
      this.service.getPercentageService();
    } catch (ExternalServiceException ex) {
      assertThat(ex.getMessage()).isEqualTo("Failed to obtain percentage value from external service");
    }
  }

  @Test
  public void testGetPercentageServiceWithCache() throws ExternalServiceException {
    Double percentage = 0.5;
    when(this.cache.getIfPresent("percentage")).thenReturn(percentage);

    assertThat(this.service.getPercentageService()).isEqualTo(percentage);

    // Verify that the cache was used
    assertThat(this.service.getPercentageService()).isEqualTo(percentage);
  }
}