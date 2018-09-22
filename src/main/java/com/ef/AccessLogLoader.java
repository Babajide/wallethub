package com.ef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.ef.exception.CommandLineException;
import com.ef.model.CommandLineArgument;
import com.ef.model.entity.AccessLog;
import com.ef.model.entity.BlockedRequest;
import com.ef.service.ParserService;
import com.ef.util.CommandLineParser;
import com.ef.util.CommandLineValidator;
import com.ef.util.LogAnalyzer;


@Component
public class AccessLogLoader implements CommandLineRunner {
    
    private Logger logger = LoggerFactory.getLogger(AccessLogLoader.class);
    private final CommandLineValidator commandLineValidator;
    private final CommandLineParser commandLineParser;
    private final ParserService parserService;
    
    private java.util.logging.Logger log = java.util.logging.Logger.getLogger(getClass().getName());
    
    @Autowired
    public AccessLogLoader(final CommandLineValidator commandLineValidator,
                           final CommandLineParser commandLineParser,
                           final ParserService parserService) {
        this.commandLineValidator = commandLineValidator;
        this.commandLineParser = commandLineParser;
        this.parserService = parserService;
    }
    
    @Override
    public void run(String... args) throws CommandLineException {
        
        logger.info("starting parser...");
        long start = System.currentTimeMillis();
        final Optional<String> validationMessage = commandLineValidator.validate(args);
        
        if (validationMessage.isPresent()) {
            throw new CommandLineException(validationMessage.get());
        }
        CommandLineArgument commandLineArgument = commandLineParser.parse(args);
        
        //Load the logs from file.
        List<AccessLog> accessLogs = parserService.parse(commandLineArgument.getAccessLog());
        
        //        //Save the logs to database if db is empty.
        //         System.out.printf("Saving %s files to DB please wait....", accessLogs.size());
        //        if(parserService.count() == 0L){
        //            log.log(Level.ALL, "");
        //            parserService.save(accessLogs);
        //        }
        
        /*
            since this is a large file, let's partition the logs and then run in a multi-threaded context
        
        ExecutorService executor = Executors.newFixedThreadPool(50);
        List<List<AccessLog>> slices = ListUtils.partition(accessLogs, 1000);
        List<Future<?>> futures = new ArrayList<>();
        System.out.println(slices.size());
        for (List<AccessLog> slice : slices) {
            futures.add(executor.submit(new Task(slice, parserService)));
        }
    
    
        boolean allDone = true;
        for(Future<?> future : futures){
            allDone &= future.isDone(); // check if future is done
        }
        
         */
        
        /*
            It is not clear whether the manipulation of data should be done using persistence layer or in memory
            so I have added additional @LogAnalyzer using powerful java 8 streams feature.
         */
        
        final LogAnalyzer logAnalyzer = new LogAnalyzer(
                accessLogs,
                commandLineArgument.getStartDate(),
                commandLineArgument.getDuration(),
                commandLineArgument.getThreshold());
        
        final List<AccessLog> list = new ArrayList<>();
        final HashMap<String, List<AccessLog>> filtered = logAnalyzer.getAccessLogsExceedingThreshHold();
        filtered.forEach((key, value) -> list.addAll(value));
        
        list.forEach(j -> log.info(j + ""));
        List<AccessLog> blockedAccessLogs = logAnalyzer.getBlockedAccessLogs();
        
        List<BlockedRequest> blockedRequests = blockedAccessLogs.stream()
                .map(d ->
                        new BlockedRequest(d.getIp(), HttpStatus.valueOf(d.getStatus()).getReasonPhrase()))
                .collect(Collectors.toList());
        
        parserService.saveBlockedRequest(blockedRequests);
        parserService.save(list);
        logger.info(String.format("Total logs is %d", list.size()));
        logger.info(String.format("%d milli seconds taken to parse log file!", System.currentTimeMillis() - start));
    }
}
