package com.usbtc.kiosk.configurations;

import com.usbtc.kiosk.apiresponse.ApiResponse;
import com.usbtc.kiosk.exceptions.CustomException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * RequestExceptionHandler
 */
@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<Object> handleCustomException(CustomException ce) {
    ApiResponse ar = new ApiResponse(ce.getStatus());
    ar.setError(ce.getMessage());
    return buildResponseEntity(ar);
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException mx, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    ApiResponse ar = new ApiResponse(HttpStatus.BAD_REQUEST);
    ar.addValidationError(mx.getBindingResult().getAllErrors());
    ar.setError("Validation Error");
    return buildResponseEntity(ar);
  }

  private ResponseEntity<Object> buildResponseEntity(ApiResponse apiResponse) {
    return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
  }
}