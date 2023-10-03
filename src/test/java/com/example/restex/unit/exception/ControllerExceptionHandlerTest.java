package com.example.restex.unit.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.restex.enums.HTTPMessage;
import com.example.restex.exception.ControllerExceptionHandler;
import com.example.restex.exception.ErrorMessage;
import com.example.restex.exception.TooManyRequestsException;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

public class ControllerExceptionHandlerTest {

  private final ControllerExceptionHandler handler = new ControllerExceptionHandler();

  @Test
  public void testRequestNotFoundHandler() {
    NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/test", null);

    ResponseEntity<ErrorMessage> response = handler.requestNotFoundHandler(ex);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(Objects.requireNonNull(response.getBody()).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo(HTTPMessage.NOT_FOUND.getValue());
  }

  @Test
  public void testBadRequestException() {
    MissingServletRequestParameterException ex = new MissingServletRequestParameterException("param", "type");

    ResponseEntity<ErrorMessage> response = handler.badRequestException(ex);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(Objects.requireNonNull(response.getBody()).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo(HTTPMessage.BAD_REQUEST.getValue());
  }

  @Test
  public void testTooManyRequestsHandler() {
    TooManyRequestsException ex = new TooManyRequestsException("Too many requests");

    ResponseEntity<ErrorMessage> response = handler.tooManyRequestsHandler(ex);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.TOO_MANY_REQUESTS);
    assertThat(Objects.requireNonNull(response.getBody()).getStatusCode()).isEqualTo(HttpStatus.TOO_MANY_REQUESTS.value());
    assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo(HTTPMessage.TOO_MANY_REQUESTS.getValue());
  }

  @Test
  public void testGlobalExceptionHandler() {
    Exception ex = new Exception("Test exception");
    WebRequest request = mock(WebRequest.class);
    MockHttpServletResponse response = new MockHttpServletResponse();

    when(request.getDescription(false)).thenReturn("test description");
    ResponseEntity<ErrorMessage> result = handler.globalExceptionHandler(ex, request);

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(Objects.requireNonNull(result.getBody()).getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    assertThat(Objects.requireNonNull(result.getBody()).getMessage()).isEqualTo("Test exception");
    assertThat(Objects.requireNonNull(result.getBody()).getDescription()).isEqualTo("test description");
  }
}