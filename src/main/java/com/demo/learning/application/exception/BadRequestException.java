package com.demo.learning.application.exception;

public class BadRequestException extends RuntimeException {

  private static final long serialVersionUID = 9112185109432716834L;
  private final String errorCode;

  public BadRequestException(final String errorCode, final String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
