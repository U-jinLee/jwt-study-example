package com.jwt.example.jwtexample.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class MyFilter2 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Test Filter2=======================================================");
        /*필터를 등록*/
        chain.doFilter(request, response);
    }
}
