package com.idle.kb_i_dle_backend.member.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public final class JwtProcessor {
    static private final long TOKEN_VALID_MILISECOND = 1000L * 60 * 60; // 60 minutes

    // For development use
    private String secretKey = "as13dfoipqieunxc_vm.hblih37y21.904-5jkd_glbovocipx98-58495ht9348hif0hg98dfugoih345n78t53yfihgkjcvhaa8sdr9wehurhlkcjxhz";
    private Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    // JWT generation
    public String generateToken(String subject, Integer uid,String nickname) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("uid", uid)
                .claim("nickname", nickname)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + TOKEN_VALID_MILISECOND))
                .signWith(key)
                .compact();
    }

    // Extract username (subject) from JWT
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate JWT
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false; // Token is invalid
        }
    }
}