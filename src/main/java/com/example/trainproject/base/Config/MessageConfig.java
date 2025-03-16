package com.example.trainproject.base.Config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfig {
    // this class is created due to the config the the file messages_fa to use the persian word
    // in this project

    @Bean
    public MessageSource messageSource() {
        // this is mandatory to have this class and body in this class
        // This tells Spring Boot to load messages_fa.properties from src/main/resources and use UTF-8 encoding.
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // be care ful about the path : if you have the path src/main/resources/i18n/messages_fa.properties
        // paste the path after resources/ in the setBasename . if not you get exception
        messageSource.setBasename("classpath:i18n/messages_fa");  // Specify the correct path here
        messageSource.setDefaultEncoding("UTF-8");  // ensure UTF-8 encoding is used
        return messageSource;
    }

}
