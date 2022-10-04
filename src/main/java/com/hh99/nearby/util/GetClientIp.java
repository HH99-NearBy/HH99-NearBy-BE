package com.hh99.nearby.util;

import javax.servlet.http.HttpServletRequest;

public class GetClientIp {

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        else if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        else if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        else if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        else if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
