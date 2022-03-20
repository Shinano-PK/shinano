package com.pk.weather.advice;

import com.pk.weather.models.ErrMsg;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException e,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    log.error("Validation error, message: {}", e);
    return new ResponseEntity<>(new ErrMsg("Cannot parse http message"), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrMsg numberFormatExceptionExceptionHandler(NumberFormatException e) {
    log.error("Provided invalid parameter (not valid Double), message: {}", e.getMessage());
    return new ErrMsg(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrMsg allExceptionsHandler(Exception e) {
    log.error("Internal error, message: {}", e.getMessage());
    return new ErrMsg("Internal processing error");
  }
}
