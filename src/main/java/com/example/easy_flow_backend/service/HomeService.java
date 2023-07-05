package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.RegisterModel;
import com.example.easy_flow_backend.dto.Models.ResetPassword;
import com.example.easy_flow_backend.error.ResponseMessage;

public interface HomeService {

    ResponseMessage Register(RegisterModel registerModel);

    ResponseMessage sendResetPasswordToken(String email);

    ResponseMessage resetPassword(String key, ResetPassword newPassword);
}
