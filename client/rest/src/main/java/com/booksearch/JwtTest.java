package com.booksearch;

import com.booksearch.model.User;
import com.booksearch.util.FileUtils;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;

@Slf4j
@Component
public class JwtTest {

    private final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(FileUtils.getProperty("jwt.secret.key")));

    private final long expireTime = Long.parseLong(FileUtils.getProperty("jwt.expire.time"));

    public JwtTest() {
    }

    public String createAccessToken(User user) {
        return createToken(user);
    }

    private String createToken(User user) {

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expiredTime = now.plusSeconds(expireTime);

        return Jwts.builder()
                .header()
                .keyId("asdada")
                .and()
                .subject("zxczxc")
                .signWith(key)
                .compact();

    }

    public String parseClaims(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
        } catch (JwtException e) {
            throw new RuntimeException();
        }
    }
}
