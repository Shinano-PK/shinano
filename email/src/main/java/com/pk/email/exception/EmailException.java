package com.pk.email.exception;

public class EmailException extends RuntimeException {
  public EmailException(String errorMessage) {
    super(errorMessage);
  }
}
