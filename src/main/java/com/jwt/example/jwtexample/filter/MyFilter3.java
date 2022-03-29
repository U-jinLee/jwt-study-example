package com.jwt.example.jwtexample.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MyFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        //Authorization:cos -> Id, pw 정상적으로 로그인 완료 시 토큰 생성 후 응답
        // 요청 때 마다 header에 Authorization의 value값으로 토큰을 가지고 온다
        // 그 때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지 검증만 하면 된다.
        log.info("Test Filter=======================================================");
        if(httpServletRequest.getMethod().equals("POST")) {
            String headerAuth = httpServletRequest.getHeader("Authorization");
            log.info("POST Request| Authorization: "+headerAuth);
            if(headerAuth.equals("cos")) {
                chain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                PrintWriter out = response.getWriter();
                out.println("No Authentication");
                log.info("No Authentication");
            }
        }
    }
}
