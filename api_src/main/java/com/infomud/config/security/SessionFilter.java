package com.infomud.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONException;

/*
This filter maps to /session and tries to validate the username and password
*/
public class SessionFilter extends AbstractAuthenticationProcessingFilter {

    private AuthService authService;

    protected SessionFilter(String urlMapping, AuthenticationManager authenticationManager, AuthService authService) {
        super(new AntPathRequestMatcher(urlMapping));
        setAuthenticationManager(authenticationManager);
        this.authService = authService;
    }

    @Override
    @ApiOperation(value = "Get token, which is needed to access all protected apis")
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException, JSONException {
        try{
            String jsonString = IOUtils.toString(request.getInputStream());
            /* using org.json */
            JSONObject userJSON = new JSONObject(jsonString);
            System.out.format("\n[SessionFilter.attemptAuthentication] username=%s\n", userJSON.getString("username"));
            final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(userJSON.getString("username"), userJSON.getString("password"));
            if(loginToken == null) {
                throw new AuthenticationServiceException("Invalid Token");
            }
            //final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken("demo", "demo");
            return getAuthenticationManager().authenticate(loginToken);
        }
        catch( JSONException | AuthenticationException e){
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        System.out.format("\n[SessionFilter.successfulAuthentication] principal=%s", authentication.getPrincipal());
        this.authService.addAuthentication(response, (TokenUser) authentication.getPrincipal());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
