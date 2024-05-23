package org.mjulikelion.messengerapplication.authentication;

import org.apache.tomcat.util.buf.Utf8Encoder;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class JwtEncoder {

    public static String encodeJwtBearerToken(String token){
        return URLEncoder.encode("Bearer " + token, StandardCharsets.UTF_8).replaceAll("\\+","%20");
    }

    public static String decodeJwtBearerToken(String cookie){
        return URLDecoder.decode(cookie, StandardCharsets.UTF_8).substring(7);
    }



}
