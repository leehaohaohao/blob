
package com.lihao.config;

import com.lihao.handler.MyJwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public FilterRegistrationBean<MyJwtFilter> jwtFilter(){
        FilterRegistrationBean<MyJwtFilter> registrationBean = new FilterRegistrationBean<>();
        MyJwtFilter myJwtFilter = new MyJwtFilter();
        registrationBean.setFilter(myJwtFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}

