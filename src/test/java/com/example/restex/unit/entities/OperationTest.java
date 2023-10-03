package com.example.restex.unit.entities;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.restex.entities.Operation;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class OperationTest {

  @Test
  public void testConstructor() {
    Double firstNumber = 2.0;
    Double secondNumber = 3.0;
    Double percentage = 0.5;
    Double result = 7.5;

    Operation operation = new Operation(firstNumber, secondNumber, percentage, result);

    assertThat(operation.getFirstNumber()).isEqualTo(firstNumber);
    assertThat(operation.getSecondNumber()).isEqualTo(secondNumber);
    assertThat(operation.getPercentage()).isEqualTo(percentage);
    assertThat(operation.getResult()).isEqualTo(result);
  }
}