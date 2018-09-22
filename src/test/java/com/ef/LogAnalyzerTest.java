package com.ef;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ef.model.Duration;
import com.ef.model.entity.AccessLog;
import com.ef.util.LogAnalyzer;


public class LogAnalyzerTest {
    
    private LogAnalyzer logAnalyzer;
    
    @Before
    public void init() {
        
        final List<AccessLog> logs = generate();
        logAnalyzer = new LogAnalyzer(logs, LocalDateTime.now(), Duration.HOURLY, 1);
    }
    
    @Test
    public void when_valid_access_logs_return_range() {
        
        List<AccessLog> logs = logAnalyzer.getAccessLogsWithin();
        Assert.assertTrue("log is empty", !logs.isEmpty());
        
    }
    
    @Test
    public void when_valid_access_logs_group_by_ip_map() {
        HashMap<String, List<AccessLog>> listHashMap = logAnalyzer.groupAccessLogByIp();
        Assert.assertTrue("listHashMap is empty", !listHashMap.isEmpty());
        Assert.assertTrue("ips with localhost  is 3", listHashMap.get("localhost").size() == 3);
    }
    
    
    @Test
    public void when_valid_access_logs_return_logs_exceeding_thresh_hold() {
       HashMap<String, List<AccessLog>> listHashMap =  logAnalyzer.getAccessLogsExceedingThreshHold();
        Assert.assertTrue("log is empty", !listHashMap.isEmpty());
    
    }
    
   
    @Test
    public void when_valid_access_logs_return_logs_blocked_access() {
        List<AccessLog> blockedAccessLogs =  logAnalyzer.getBlockedAccessLogs();
        Assert.assertTrue("log is empty", !blockedAccessLogs.isEmpty());
    
    }
    
    
    private List<AccessLog> generate() {
        
        List<AccessLog> logs = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            logs.add(random());
        }
        
        //simulate request from same ip
        logs.add(randomWithIp("localhost"));
        logs.add(randomWithIp("localhost"));
        logs.add(randomWithIp("localhost"));
        
        //simulate future request
        logs.add(randomWithAccessTime(LocalDateTime.now().plusMonths(1)));
        return logs;
    }
    
    private AccessLog randomWithIp(String ip) {
        AccessLog log = random();
        log.setIp(ip);
        return log;
    }
    
    private AccessLog randomWithAccessTime(LocalDateTime dateTime) {
        AccessLog log = random();
        log.setAccessTime(dateTime);
        return log;
    }
    
    private AccessLog random() {
        AccessLog log = new AccessLog();
        log.setRequestLine("a line");
        log.setUserAgent("iPhone");
        log.setStatus(200);
        log.setIp(RandomStringUtils.randomAlphanumeric(5));
        log.setAccessTime(LocalDateTime.now().plusMinutes(5));
        return log;
    }
    
}
