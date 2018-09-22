package com.ef;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.ef.util.CommandLineValidator;

@Component
public class Config {
    @Bean
    CommandLineValidator validator(){
        return new CommandLineValidator();
    }
    
}
