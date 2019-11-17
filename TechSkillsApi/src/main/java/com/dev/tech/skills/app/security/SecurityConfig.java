package com.dev.tech.skills.app.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/persons/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/v1/persons/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/persons/**/address").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/pets/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/v1/pets/**").hasRole("USER")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }


}

