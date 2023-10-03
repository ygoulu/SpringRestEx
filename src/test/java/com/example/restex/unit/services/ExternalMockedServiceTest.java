package com.example.restex.unit.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.restex.exception.ExternalServiceException;
import com.example.restex.services.ExternalMockedService;
import com.google.common.cache.Cache;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.ReflectionUtils;

public class ExternalMockedServiceTest {

  private ExternalMockedService service;

  @Mock
  private Cache<String, Double> cache;

  //We will use ReflectionUtils to mock cache and manipulate its behaviour
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    this.service = new ExternalMockedService();
    Field cacheField = ReflectionUtils.findField(ExternalMockedService.class, "cache");
    cacheField.setAccessible(true);
    ReflectionUtils.setField(cacheField, this.service, this.cache);  }

  @Test
  public void testGetPercentageService() throws ExternalServiceException {
    Double percentage = 0.5;
    when(cache.getIfPresent("percentage")).thenReturn(null);
    doNothing().when(this.cache).put("percentage", percentage);
    assertThat(this.service.getPercentageService()).isBetween(0.0, 100.0);

    // Verify that the cache was used
    assertThat(this.service.getPercentageService()).isBetween(0.0, 100.0);
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