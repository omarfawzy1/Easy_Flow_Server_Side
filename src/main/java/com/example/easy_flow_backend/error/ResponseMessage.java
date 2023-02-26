package com.example.easy_flow_backend.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    private HttpStatus status;
    private String message;

    public ResponseMessage(String message, HttpStatus status) {
        this.status = status;
        this.message = message;
    }
}
