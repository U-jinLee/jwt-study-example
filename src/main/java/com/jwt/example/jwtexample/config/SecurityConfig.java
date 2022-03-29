package com.jwt.example.jwtexample.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /**
                 * jwt 로그인 방식의 기본 설정
                 * */
                .csrf().disable()
                /*세션 X stateless 방식*/
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                /*Form 태그 로그인 X*/
                .formLogin().disable()
                /*기본적인 http 로그인 방식 X*/
                /***/
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/user/**").hasAnyRole("ROLE_USER","ROLE_MANAGER","ROLE_ADMIN")
                .antMatchers("/api/v1/admin/**").hasRole("ROLE_ADMIN")
                .anyRequest().permitAll();
    }
}
