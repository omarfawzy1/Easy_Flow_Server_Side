package com.example.easy_flow_backend.service.utils;

import java.text.ParseException;

public class Utility {
    public static long stringToMilleSecond(String date) throws ParseException {
        long result=0;
        result+=Long.parseLong(date.substring(0,4)) * 31556952000L;
        result+=Long.parseLong(date.substring(5,7)) * 2629746000L;
        result+=Long.parseLong(date.substring(8,10)) * 86400000L;
        result+=Long.parseLong(date.substring(11,13)) * 3600000L;
        result+=Long.parseLong(date.substring(14,16)) * 60000L;
        result+=Long.parseLong(date.substring(17,19)) * 1000L;
        return result;
    }
}
