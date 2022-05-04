package com.jwt.app1.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    private static final Integer EXPIRATION= 600000;

    @Value("${jwt.secret}")
    private String secret;

    public String generate(String username) {

        List<String> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
                "ROLE_USER, ROLE_ADMIN"
        ).stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return "Bearer " + Jwts.builder()
                .setId("code1").setSubject(username)
                .setIssuedAt( new Date(System.currentTimeMillis()))
                .setExpiration( new Date(System.currentTimeMillis() + EXPIRATION) )
                .signWith( SignatureAlgorithm.HS512, this.secret.getBytes() )
                .claim("authority", authorities)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token Mal formado");
        }  catch (UnsupportedJwtException e) {
            logger.error("Token no soportado " + e.getMessage());
        }  catch (ExpiredJwtException e) {
            logger.error("Token expirado");
        }catch (IllegalArgumentException e) {
            logger.error("Token vacio");
        } catch (SignatureException e) {
            logger.error("Fail en la forma");
        }
        return false;
    }
}
