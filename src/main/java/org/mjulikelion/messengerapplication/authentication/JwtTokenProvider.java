package org.mjulikelion.messengerapplication.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.mjulikelion.messengerapplication.exception.AuthorizeException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final long validityInMilliSecond;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.expire-length}") final long validityInMillySecond){
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliSecond = validityInMillySecond;
    }

    public String createToken(String payload){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliSecond); //만료시간 = 생성시간 + 유지시간

        return Jwts.builder()
                .setSubject(payload)
                .setExpiration(expiration)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getPayload(final String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)//jws이다... 서명을 파싱
                    .getBody()
                    .getSubject();
        }catch (JwtException e){
            throw new AuthorizeException(ErrorCode.TOKEN_INVALID,e.getMessage());
        }
    }

}
