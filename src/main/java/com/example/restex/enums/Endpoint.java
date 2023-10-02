package com.example.restex.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Endpoint {
  SUM("get_sum"),
  HISTORY("get_history");

  private final String value;
}