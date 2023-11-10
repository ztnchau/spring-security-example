package com.demo.learning.application.exception;

public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = 9112185109432716834L;
  private final String errorCode;

  public NotFoundException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
