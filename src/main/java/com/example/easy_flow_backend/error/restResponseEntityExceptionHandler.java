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
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> RegisterErrorException(NotFoundException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> BadRequestException(BadRequestException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);

    }
}
