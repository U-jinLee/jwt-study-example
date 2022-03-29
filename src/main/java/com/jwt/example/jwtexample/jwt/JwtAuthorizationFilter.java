package com.jwt.example.jwtexample.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 시큐리티가 가진 filter 중 BasicAuthentication Filter가 존재
 * 권한이나 인증이 필요한 특정 주소를 요청 시 위 필터를 탄다.
 * 권한이나 인증이 필요한 주소가 아니면 이 필터를 타지 않도록 한다.
 * */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        logger.info("인증이나 권한이 필요로 하는 주소");
    }

    //인증이나 권한이 필요한 주소 요청이 있을 때 해당 필터를 타게 된다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilterInternal(request, response, chain);
        logger.info("인증이나 권한이 필요로 하는 주소");
    }

}
