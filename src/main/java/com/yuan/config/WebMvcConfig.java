package com.yuan.config;

import com.yuan.interceptor.LoginInterceptor;
import com.yuan.interceptor.OtherInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }
    @Bean
    public OtherInterceptor getOtherInterceptor(){
        return new OtherInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(getOtherInterceptor()).addPathPatterns("/**");
    }
}
