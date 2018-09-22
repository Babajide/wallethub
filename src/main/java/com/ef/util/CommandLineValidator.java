package com.ef.util;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class CommandLineValidator {
    
    @Value("${application.default.help.text}")
    private String helpText;
    
    private static Logger logger = LoggerFactory.getLogger(CommandLineValidator.class);
    
    public Optional<String> validate(final String[] args){
        logger.debug("validating program arg...");
        if (args == null || args.length == 0 ) {
            logger.error("No argument supplied, aborting...");
            return Optional.of(String.format(" Please enter program argument in format: %s", helpText));
        }
        if (args.length < 4) {
            logger.error("Not enough arg supplied, aborting...");
            return Optional.of(String.format(" Please enter program argument in format: %s", helpText));
        }
        logger.info("validating program arg: pass");
        return Optional.empty();
    }
    
}
