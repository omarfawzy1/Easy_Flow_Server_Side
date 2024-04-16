package com.easy_flow_server.service;

import com.easy_flow_server.dto.model.ResetPassword;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.dto.model.RegisterModel;

public interface HomeService {

    ResponseMessage Register(RegisterModel registerModel);

    ResponseMessage sendResetPasswordToken(String email);

    ResponseMessage resetPassword(String key, ResetPassword newPassword);
}
