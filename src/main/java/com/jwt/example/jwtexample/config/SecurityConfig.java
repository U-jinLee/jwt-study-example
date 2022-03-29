package com.jwt.example.jwtexample.config;

import com.jwt.example.jwtexample.jwt.JwtAuthenticationFilter;
import com.jwt.example.jwtexample.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /**
                 * jwt 로그인 방식의 기본 설정
                 * */
                //.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class) //이렇게 걸어도 되지만 다른 방법도 있다. MyFilter 클래스로 이동
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) //AuthenticationManager를 던져줘야 한다.
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                /*세션 X stateless 방식*/
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                /*요청에 cors필터를 첨부 Cross Origin 요청이 와도 다 허용*/
                .addFilter(corsFilter) // 인증이 있을 때는 필터에 등록을 해줘야 함, 인증이 없을 때는 @CrossOring을 사용하는 방법이 있음
                /*Form 태그 로그인 X*/
                .formLogin().disable()
                /*기본적인 http 로그인 방식 X*/
                .httpBasic().disable()
                /***/
                .authorizeRequests()
                .antMatchers("/api/v1/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();

    }
}