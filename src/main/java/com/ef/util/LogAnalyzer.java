package com.ef.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.model.Duration;
import com.ef.model.entity.AccessLog;


/**
 * this class is used to analyze logs based on program input
 */
public class LogAnalyzer {
    
    private static Logger logger = LoggerFactory.getLogger(LogAnalyzer.class);
    private List<AccessLog> accessLogs;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int threshHold;
    
    public LogAnalyzer(List<AccessLog> accessLogs, LocalDateTime startDateTime, Duration duration, int threshHold) {
        this.accessLogs = accessLogs;
        this.startDateTime = startDateTime;
        this.endDateTime = startDateTime.plusHours(duration.getTime());
        this.threshHold = threshHold;
    }
    
    /**
     * Any logs after the allowed threshhold is assumed to be a blocked access.
     * @return
     */
    public List<AccessLog> getBlockedAccessLogs() {
        HashMap<String, List<AccessLog>> map = getAccessLogsExceedingThreshHold();
        Optional<String> key = map.keySet().stream().findFirst();
        return key.map(s -> map.get(s).subList(threshHold, map.get(s).size() )).orElseGet(ArrayList::new);
    }
    
    public HashMap<String, List<AccessLog>> getAccessLogsExceedingThreshHold() {
        return (HashMap<String, List<AccessLog>>) groupAccessLogByIp()
                .entrySet()
                .stream()
                .filter(e -> e.getValue().size() > threshHold)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    

    
    public HashMap<String, List<AccessLog>> groupAccessLogByIp() {
        return (HashMap<String, List<AccessLog>>) getAccessLogsWithin().stream()
                .collect(Collectors.groupingBy(AccessLog::getIp));
    }
    
    public List<AccessLog> getAccessLogsWithin() {
        final Predicate<AccessLog> predicate = e -> e.getAccessTime().isAfter(startDateTime)
                && e.getAccessTime().isBefore(endDateTime);
        return filter(predicate).collect(Collectors.toList());
    }
    
    private Stream<AccessLog> filter(Predicate<AccessLog> predicate) {
        return accessLogs.stream().filter(predicate);
        
    }
    
}
