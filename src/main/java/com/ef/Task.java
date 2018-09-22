package com.ef;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.model.entity.AccessLog;
import com.ef.service.ParserService;

/*
Simple task loader
 */
public class Task implements Runnable {
    
    private ParserService parserService;
    private List<AccessLog> logs;
    private Logger logger = LoggerFactory.getLogger(Task.class);
    
    
    public Task(List<AccessLog> logs, final ParserService parserService) {
        this.logs = logs;
        this.parserService = parserService;
    }
    
    @Override
    public void run() {
    logger.info(String.format("running %s with total logs: %d", Thread.currentThread().getName(), logs.size()));
        parserService.save(logs);
        
    }
}
