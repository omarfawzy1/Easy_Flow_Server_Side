package com.example.easy_flow_backend.security;

import com.example.easy_flow_backend.entity.User;
import com.example.easy_flow_backend.repos.UserRepositry;
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

        UserPrinciple userPrinciple=new UserPrinciple(user);
        return userPrinciple;
    }
}
