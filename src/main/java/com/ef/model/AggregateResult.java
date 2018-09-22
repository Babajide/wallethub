package com.ef.model;

import java.time.LocalDateTime;
import java.util.Date;


public class AggregateResult {
    
    public AggregateResult(String ip, LocalDateTime date, Long total) {
        this.ip = ip;
        this.total = total;
    }
    
    private String ip;
    private long total;
    private Date date;
    
    public String getIp() {
        return ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public long getTotal() {
        return total;
    }
    
    public void setTotal(long total) {
        this.total = total;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
}
