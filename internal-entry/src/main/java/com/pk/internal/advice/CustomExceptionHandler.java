package com.pk.internal.advice;

import javax.validation.ValidationException;

import com.pk.internal.model.ErrMsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.security.access.AccessDeniedException;

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

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrMsg userNotFoundExceptionHandler(BadCredentialsException e) {
    log.error("Bad creds: {}", e.getMessage());
    return new ErrMsg("User not found");
  }

  // @ExceptionHandler(EmailNotConfirmed.class)
  // @ResponseStatus(HttpStatus.BAD_REQUEST)
  // private ErrMsg emailNotConfirmedExceptionHandler() {
  //   log.error("Email is not confirmed");
  //   return new ErrMsg("Email not confirmed");
  // }

  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrMsg validationExceptionHandler(ValidationException e) {
    log.error("Validation error, message: {}", e.getMessage());
    return new ErrMsg(e.getMessage());
  }

  // @ExceptionHandler(ElementNotFound.class)
  // @ResponseStatus(HttpStatus.BAD_REQUEST)
  // private ErrMsg elementNotFoundExceptionHandler(ElementNotFound e) {
  //   log.error("Element not found in database, message: {}", e.getMessage());
  //   return new ErrMsg(e.getMessage());
  // }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  private ErrMsg securityException(AccessDeniedException e) {
    log.error("Security exception, message: {}", e.getMessage());
    return new ErrMsg(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrMsg allExceptionsHandler(Exception e) {
    log.error("Internal error, message: {}", e.getMessage());
    return new ErrMsg("Internal processing error");
  }
}
