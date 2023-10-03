package com.example.restex.exception;

public class ExternalServiceException extends RuntimeException {

  public ExternalServiceException(String message) {
    super(message);
  }
}