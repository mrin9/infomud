package com.infomud.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.core.annotation.Order;

//Manually Added
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
@Order(1)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthService tokenAuthService;

    public AppSecurityConfig() {
        super(true);
    }


	@Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .exceptionHandling().and()
        .anonymous().and()
        // Disable Cross site references
		.csrf().disable()
        .authorizeRequests()
		// Allow anonymous resource requests
		.antMatchers("/", "/resources/**", "/statis/**", "/public/**"
        , "/configuration/**", "/swagger-ui/**",  "/swagger-ui-dark/**", "/swagger-ui-new/**", "/swagger-resources/**","/api-docs/**", "/v2/api-docs/**"
        , "/webjars/springfox-swagger-ui/**"
        , "/login" , "/session"
        , "/home"
        , "/console/*"
        , "/webui/**"
        , "/**/*.html" ,"/**/*.css","/**/*.js","/**/*.png","/**/*.ttf","/**/*.woff").permitAll()
        // All other request need to be authenticated
        .anyRequest().authenticated().and()
        // Allow CORS
        .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
		    // Custom Token based authentication based on the header previously given to the client
        .addFilterBefore(new TokenFilter(tokenAuthService), UsernamePasswordAuthenticationFilter.class)
        // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
        .addFilterBefore(new SessionFilter("/session", authenticationManager(), tokenAuthService), UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

}
