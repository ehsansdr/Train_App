package com.example.trainproject.base.Config;

import com.example.trainproject.base.Util.ApiKeyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public ApiKeyFilter apiKeyFilter() {
        return new ApiKeyFilter(); // Explicitly create ApiKeyFilter instance
    }

    @Bean
    public FilterRegistrationBean<ApiKeyFilter> apiKeyFilterRegistrationBean(ApiKeyFilter apiKeyFilter) {
        FilterRegistrationBean<ApiKeyFilter> registrationBean = new FilterRegistrationBean<>(apiKeyFilter);
        registrationBean.addUrlPatterns("/secured/*");
        return registrationBean;
    }
}
