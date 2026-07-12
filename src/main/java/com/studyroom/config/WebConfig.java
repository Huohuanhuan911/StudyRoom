package com.studyroom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/auth/login").setViewName("forward:/api/auth/login");
        registry.addViewController("/auth/register").setViewName("forward:/api/auth/register");
        registry.addViewController("/auth/userinfo").setViewName("forward:/api/auth/userinfo");
        registry.addViewController("/campus/buildings").setViewName("forward:/api/campus/buildings");
        registry.addViewController("/campus/classrooms").setViewName("forward:/api/campus/classrooms");
        registry.addViewController("/campus/seats").setViewName("forward:/api/campus/seats");
        registry.addViewController("/{path:[^\\.]*}")
                .setViewName("forward:/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

}