package com.example.easy_flow_backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.easy_flow_backend.entity.User;
import com.example.easy_flow_backend.repos.UserRepositry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepositry userRepositry;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager
            , UserRepositry userRepositry) {
        super(authenticationManager);
        this.userRepositry = userRepositry;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        //if header does not contain BEARER or it is null delegate to spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        //If header is present, try grab user principle from Database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //Contain filter execution
        chain.doFilter(request, response);
     }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER_STRING);
        if (token != null) {
            //parse the token and validate it
            String userName = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(token.replace(JwtProperties.TOKEN_PREFIX, ""))
                    .getSubject();
            //Search in DB if we find the user by token subject (username)
            //If so,then grab user details and create spring auth using username, pass, authorities/roles
            if (userName != null) {

                User user = userRepositry.findUserByUsername(userName);
                UserPrinciple principle = new UserPrinciple(user);
                return new UsernamePasswordAuthenticationToken(userName, null, principle.getAuthorities());
            }
        }
        return null;
    }
}
