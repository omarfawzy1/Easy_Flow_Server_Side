package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.ResetPassword;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.dto.Models.RegisterModel;
import com.example.easy_flow_backend.error.ResponseMessage;
import org.springframework.web.bind.annotation.RequestBody;

public interface HomeService {

    ResponseMessage Register(RegisterModel registerModel) throws NotFoundException;
    ResponseMessage sendResetPasswordToken(@RequestBody String email) throws NotFoundException;

    ResponseMessage resetPassword(String key, ResetPassword newPassword);
}
