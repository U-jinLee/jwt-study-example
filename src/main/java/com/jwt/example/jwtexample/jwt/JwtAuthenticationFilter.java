package com.jwt.example.jwtexample.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.example.jwtexample.auth.PrincipalDetails;
import com.jwt.example.jwtexample.entity.User;
import com.jwt.example.jwtexample.entity.UserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 스프링 시큐리티의 UsernamePasswordAuthenticationFilter
 * '/login' 요청해서 username, password 전송 시 동작(POST)
 * FormLogin이 disalble 돼 비활성화 상태 -> addFilter로 JWtAuthentication 필터 재활성화
 * */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    /**
     * 로그인 시도 함수 -> '/login' 요청 시 실행되는 함수
     * DB에 있는 UserName과 password가 일치함
     * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper object = new ObjectMapper();

        try {
            User user = object.readValue(request.getInputStream(), User.class);
            log.info("userDto Info: {}, {}", user.getUsername(), user.getPassword());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            //PrincipalDetailsService의 loadUserByUsername() 함수가 실행
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("Login Success principalDetails Info = {}", principalDetails.getUser().getUsername());

            // 리턴될 때 authentication 객체가 session 영역에 저장된다 -> 로그인을 의미함
            // 리턴의 이유는 버젼 관리를 security가 대신 해주기 때문에 편하려고 하는 거임
            // 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음, 단지 권한 처리 때문에 Session을 넣어 준다.
            // JWT
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 1. username, password를 받아
         * 2. 정상인지 로그인 시도를 진행 | AuthenticationManager로 로그인 시도 -> PrincipalDetailsService 호출
         * 3. PrinciapalDetails를 세션에 담은 후(권한 관리를 위해)
         * 4. JWT토큰을 만들어 응답
         * */
        return null;
    }

    /**
     * attemptAuthentication 실행 후 인증이 정상적으로 완료됐으면 successfulAuthentication 함수 실행
     * JWT 토큰을 만들어 request 요청한 사용자에 JWT토큰을 response 해주면 된다.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        //RSA X Hash암호 방식
        String jwtToken = JWT.create()
                        .withSubject("cos_token")
                        .withExpiresAt(new Date(System.currentTimeMillis()+(10*60*1000)))
                        .withClaim("id", principalDetails.getUsername()).withClaim("password", principalDetails.getPassword())
                        .sign(Algorithm.HMAC512("cos"));

        log.info("인증 완료 유무 확인");
        response.addHeader("Authorization", "Bearer " + jwtToken);
        /**
         * 1. username, password login success
         * 2. Create JWT Token
         * 3. Response JWT Token to client
         * 4. 요청할 때마다 JWT토큰을 가지고 요청 -> 서버는 JWT토큰이 유효한지를 판단하는 일 필요(Filter가 답)
         * */
    }

}
