package com.example.prac.config;


import com.example.prac.filter.UrlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    
    public FilterConfig(){
        System.out.println("=============FilterConfig==============");
    }

    @Bean
    public FilterRegistrationBean<UrlFilter> urlFilter(){
        FilterRegistrationBean<UrlFilter> bean = new FilterRegistrationBean<>(new UrlFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(0); // 젤 처음 실행되도록
        return bean;
    }
}
