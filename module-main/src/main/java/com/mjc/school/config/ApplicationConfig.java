package com.mjc.school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mjc.school.helper.MenuHelper;
import com.mjc.school.helper.QueryParamsParser;

@Configuration(proxyBeanMethods = false)
public class ApplicationConfig {

    // MenuHelper is created using @Bean annotation by the following reasons:
    // 1. Just to show that beans can be created
    //    not only by @Component, @Service and other stereotype annotations
    //    but also with help of @Bean annotations in configuration classes (marked as @Configuration).
    // 2. To show that bean can be created and initialized programmatically.
    @Bean
    public MenuHelper menuHelper() {
        return new MenuHelper(System.out, queryParamsParser());
    }


    private QueryParamsParser queryParamsParser() {
        return new QueryParamsParser();
    }
}
