package com.easy_flow_server.service.utils;

public class Utility {
    public static long stringToMilleSecond(String date) {
        long result = 0;
        result += Long.parseLong(date.substring(0, 4)) * 31556952000L;
        result += Long.parseLong(date.substring(5, 7)) * 2629746000L;
        result += Long.parseLong(date.substring(8, 10)) * 86400000L;
        result += Long.parseLong(date.substring(11, 13)) * 3600000L;
        result += Long.parseLong(date.substring(14, 16)) * 60000L;
        result += Long.parseLong(date.substring(17, 19)) * 1000L;
        return result;
    }


    public static String getRandomTag() {
        return String.valueOf((int) (System.currentTimeMillis() % 10000000 + 1));
    }
}
