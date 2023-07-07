package com.easy_flow_server.security;

public class JwtProperties {
    public static final String SECRET = "Haridy123";
    public static final long EXPIRATION_TIME = 864000000;// 10 Days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING="Authorization";
}
