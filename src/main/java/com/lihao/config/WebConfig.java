
package com.lihao.config;

import com.lihao.handler.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(){
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        JwtFilter jwtFilter = new JwtFilter();
        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}

