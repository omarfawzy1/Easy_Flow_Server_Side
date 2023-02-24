package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.Dto.Models.RegisterModel;
import org.springframework.http.ResponseEntity;

public interface HomeService {

    ResponseEntity<String> Register(RegisterModel registerModel) throws NotFoundException;

}
