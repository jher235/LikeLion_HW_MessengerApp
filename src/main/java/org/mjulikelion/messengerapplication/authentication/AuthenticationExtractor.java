package org.mjulikelion.messengerapplication.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.websocket.AuthenticationException;
import org.mjulikelion.messengerapplication.exception.AuthorizeException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;

import java.util.Arrays;

public class AuthenticationExtractor {

    private static final String TOKEN_COOKIE_NAME = "AccessToken";

    public static String extract(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies!=null){
            for(Cookie cookie :cookies){
                if (cookie.getName().equals(TOKEN_COOKIE_NAME)){
                    return JwtEncoder.decodeJwtBearerToken(cookie.getValue());
                }
            }
        }
        throw new AuthorizeException(ErrorCode.TOKEN_NOT_FOUND);
    }
}
