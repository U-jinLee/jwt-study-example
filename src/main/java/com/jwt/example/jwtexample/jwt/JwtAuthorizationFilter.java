package com.jwt.example.jwtexample.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 시큐리티가 가진 filter 중 BasicAuthentication Filter가 존재
 * 권한이나 인증이 필요한 특정 주소를 요청 시 위 필터를 탄다.
 * 권한이나 인증이 필요한 주소가 아니면 이 필터를 타지 않도록 한다.
 * */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

}
