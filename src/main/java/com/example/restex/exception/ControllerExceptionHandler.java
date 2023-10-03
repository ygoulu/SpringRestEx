package com.example.restex.exception;

import com.example.restex.enums.HTTPMessage;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

  //Not found exception
  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseBody
  public ResponseEntity<ErrorMessage> requestNotFoundHandler(Exception ex) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        HTTPMessage.NOT_FOUND.getValue(),
        ex.getMessage());

    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
  }

  //Illegal Argument Exception.
  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseBody
  public ResponseEntity<ErrorMessage> badRequestException(MissingServletRequestParameterException ex) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.BAD_REQUEST.value(),
        new Date(),
        HTTPMessage.BAD_REQUEST.getValue(),
        ex.getMessage());

    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

  //Not found exception
  @ExceptionHandler(TooManyRequestsException.class)
  @ResponseBody
  public ResponseEntity<ErrorMessage> tooManyRequestsHandler(Exception ex) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.TOO_MANY_REQUESTS.value(),
        new Date(),
        HTTPMessage.TOO_MANY_REQUESTS.getValue(),
        ex.getMessage());

    return new ResponseEntity<>(message, HttpStatus.TOO_MANY_REQUESTS);
  }

  //Default exception
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}