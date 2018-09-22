package com.ef.util;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ef.exception.CommandLineException;
import com.ef.model.CommandLineArgument;
import com.ef.model.Duration;


/**
 * this class validates cmd params and converts into a model that can be easily referenced
 * within the main program.
 */
@Component
public class CommandLineParser {
    
    private  Logger logger = LoggerFactory.getLogger(CommandLineParser.class);
    
    @Value("${application.default.cmd.dateformat}")
    private  String cmdDateFormat;
   
    
    public CommandLineArgument parse(final String[] args) throws CommandLineException {
        
        logger.debug("About to validateAndParse cmd arg {}", new Object[] {args});
        CommandLineArgument argument = new CommandLineArgument();
        for (String arg : Arrays.asList(args)) {
            final String value = arg.split("=")[1];
            
            if (arg.contains("accesslog")) {
                argument.setAccessLog(value);
                continue;
            }
            if (arg.contains("startDate")) {
                logger.debug("Setting Date, Using date format{}", cmdDateFormat);
                argument.setStartDate(DateUtil.toDate(value, cmdDateFormat));
                continue;
            }
            if (arg.contains("duration")) {
                argument.setDuration(Duration.valueOf(value.toUpperCase()));
                continue;
            }
            if (arg.contains("threshold")) {
                argument.setThreshold(Integer.valueOf(value));
            }
        }
    
        return argument;
        
    }
    
}
