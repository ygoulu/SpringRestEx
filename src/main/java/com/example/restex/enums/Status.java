package com.example.restex.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
  FAILED("failed"),
  SUCCEED("succeed");

  private final String value;
}