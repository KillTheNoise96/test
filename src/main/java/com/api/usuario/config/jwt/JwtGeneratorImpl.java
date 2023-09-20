package com.api.usuario.config.jwt;

import com.api.usuario.model.entity.Token;
import com.api.usuario.model.entity.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtGeneratorImpl {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${token.time}")
    private int time;

    public Token generateToken(Users users) {
        String jwtToken = "";
        jwtToken = Jwts.builder().
                setSubject(users.getName()).
                setIssuedAt(new Date()).
                setExpiration(DateUtils.addMinutes(new Date(), time)).
                signWith(SignatureAlgorithm.HS256, secret).
                compact();
        return new Token(jwtToken);
    }
}
