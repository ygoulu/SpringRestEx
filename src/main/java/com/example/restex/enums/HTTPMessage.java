package com.example.restex.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HTTPMessage {
  NOT_FOUND("Requested resource is not found"),
  TOO_MANY_REQUESTS("Too many request, you exceeded the available threshold"),
  BAD_REQUEST("Bad request, you are missing or mistyping parameters");

  private final String value;
}