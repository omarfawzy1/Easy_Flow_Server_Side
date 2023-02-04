package com.example.easy_flow_backend.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class restResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    /*@ExceptionHandler(RegisterErrorException.class)
    public ResponseEntity<ErrorMessage> RegisterErrorException(RegisterErrorException exception, WebRequest request) {
       System.out.println(request.toString());
        ErrorMessage message = new ErrorMessage(HttpStatus.FAILED_DEPENDENCY,exception.getMessage());
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(message);
    }*/

}
