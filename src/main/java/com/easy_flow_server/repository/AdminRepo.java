package com.easy_flow_server.repository;

import com.easy_flow_server.entity.Administrator;
import jakarta.transaction.Transactional;

@Transactional
public interface AdminRepo extends AbstractRepo<Administrator> {

}
