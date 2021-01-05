package com.bookhub.util;

import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SessionValidator {
    public static String validateSessionID(HttpServletRequest request, StringRedisTemplate template) {
        Cookie[] cookies = request.getCookies();
        String sessionID = null;
        if (cookies == null){
            return null;
        }
        for(Cookie item : cookies){
            if("sessionID".equals(item.getName())){
                sessionID = item.getValue();
            }
        }
        if(sessionID==null) return null;
        return template.opsForValue().get(sessionID);
    }
}
