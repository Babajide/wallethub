package com.ef.model;

/**
 * represents Duration with interval in hour(s)
 */
public enum Duration {
    
    HOURLY(1),
    DAILY(24);
    
    private int hours;
    
    Duration(int hours){
        this.hours = hours;
    }
    
    public int getTime() {
        return hours;
    }
}
