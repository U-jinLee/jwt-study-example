package com.jwt.example.jwtexample.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jwt.example.jwtexample.auth.PrincipalDetails;
import com.jwt.example.jwtexample.entity.User;
import com.jwt.example.jwtexample.entity.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    //인증이나 권한이 필요한 주소 요청이 있을 때 해당 필터를 타게 된다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //super.doFilterInternal(request, response, chain);
        log.info("인증이나 권한이 필요로 하는 주소");

        String jwtHeader = request.getHeader("Authorization");
        log.info("Authorization jwt Header {}", jwtHeader);

        //header가 있는지 확인
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
        }

        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        log.info(jwtToken);
        String userName =
                JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken).getClaim("id").asString();
        log.info("username: {}", userName);

        if(userName != null) {
            User userEntity = userRepository.findByUsername(userName);

            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            //Jwt 토큰 서명을 통해 서명이 정상일 시 Authentication 객체를 만들어 준다.
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            //강제로 Security 세션에 접근해 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }

    }

}
