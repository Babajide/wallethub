package com.ef.model;

import java.time.LocalDateTime;


public class CommandLineArgument {
    
    private String accessLog;
    private LocalDateTime startDate;
    private Duration duration;
    private int threshold;
    
    public String getAccessLog() {
        return accessLog;
    }
    
    public void setAccessLog(String accessLog) {
        this.accessLog = accessLog;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public Duration getDuration() {
        return duration;
    }
    
    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    
    public int getThreshold() {
        return threshold;
    }
    
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
