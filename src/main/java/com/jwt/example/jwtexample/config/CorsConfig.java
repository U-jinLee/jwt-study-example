package com.jwt.example.jwtexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답 시 json을 js에서 처리할 수 있는지를 설정 ex) ajax로 Data를 보낼 때 응답이 가능한지
        config.addAllowedOrigin("*"); // 모든 IP에 응답 허용
        config.addAllowedHeader("*"); // 모든 Header에 응답 허용
        config.addAllowedMethod("*"); // 모든 post, get, put, delete, patch 요청에 허용하겠다.

        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
