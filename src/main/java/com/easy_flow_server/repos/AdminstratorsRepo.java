package com.easy_flow_server.repos;

import com.easy_flow_server.entity.Administrator;
import jakarta.transaction.Transactional;

@Transactional
public interface AdminstratorsRepo extends AbstractRepo<Administrator> {

}
