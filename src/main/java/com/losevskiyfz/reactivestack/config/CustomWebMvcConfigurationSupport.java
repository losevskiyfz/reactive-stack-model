package com.losevskiyfz.reactivestack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

public class CustomWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

    @Bean
    public PageRequest defaultPageRequest() {
        return PageRequest.of(0, 10);
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        SortHandlerMethodArgumentResolver argumentResolver = new SortHandlerMethodArgumentResolver();
        argumentResolver.setSortParameter("sort");
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver(argumentResolver);
        resolver.setFallbackPageable(defaultPageRequest());
        resolver.setPageParameterName("page");
        resolver.setSizeParameterName("size");
        argumentResolvers.add(resolver);
    }

}
