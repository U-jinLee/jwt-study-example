package com.jwt.example.jwtexample.config;

import com.jwt.example.jwtexample.filter.MyFilter;
import com.jwt.example.jwtexample.filter.MyFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 선 Security filter -> FilterConfig 방식
 * */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter> filter1() {
        FilterRegistrationBean<MyFilter> bean = new FilterRegistrationBean<>(new MyFilter());
        bean.addUrlPatterns("/*"); // 허용하는 URL
        bean.setOrder(0); // 우선순위 부여

        return bean;
    }

    @Bean
    public FilterRegistrationBean<MyFilter2> filter2() {
        FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
        bean.addUrlPatterns("/*"); // 허용하는 URL
        bean.setOrder(1); // 우선순위 부여

        return bean;
    }
}
