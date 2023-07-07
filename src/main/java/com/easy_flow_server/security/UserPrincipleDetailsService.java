package com.easy_flow_server.security;

import com.easy_flow_server.entity.User;
import com.easy_flow_server.repos.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipleDetailsService implements UserDetailsService {
    @Autowired
    private UserRepositry userRepositry;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=this.userRepositry.findUserByUsername(s);
        return new UserPrinciple(user);
    }
}
