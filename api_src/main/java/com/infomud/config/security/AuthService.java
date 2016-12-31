package com.infomud.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.io.PrintWriter;
import com.infomud.model.security.User;

@Service
public class AuthService {

    //private static final long VALIDITY_TIME_MS = 10 * 24 * 60 * 60 * 1000;// 10 days Validity
    private static final long VALIDITY_TIME_MS =  2 * 60 * 60 * 1000; // 2 hours  validity
    private static final String AUTH_HEADER_NAME = "Authorization";

    private String secret="mrin";

    public String addAuthentication(HttpServletResponse response, TokenUser tokenUser) {
        String token = createTokenForUser(tokenUser.getUser());
        response.addHeader(AUTH_HEADER_NAME, token);
        response.setContentType("application/json");
         try {
            PrintWriter out = response.getWriter();
            out.println("{\"token\": \""+token + "\"}");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null && !token.isEmpty()) {
            final TokenUser user = parseUserFromToken(token.replace("Bearer","").trim());
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }

    //Get User Info from the Token
    public TokenUser parseUserFromToken(String token){
        String userJSON = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
        return new TokenUser(fromJSON(userJSON));
    }

    public String createTokenForUser(User user) {
        return Jwts.builder()
            .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
            .setSubject(toJSON(user))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    private User fromJSON(final String userJSON) {
        try {
            return new ObjectMapper().readValue(userJSON, User.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String toJSON(User user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
