package com.ef.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * it was observed that date format for cmd arg --startDate is different from date format in log entry
 * this method can help parse date with pertinent date formats
 */
public final class DateUtil {
    
    private DateUtil(){} //invoke statically
    
    public static LocalDateTime toDate(String date, String format) {
        return LocalDateTime.parse(date,  DateTimeFormatter.ofPattern(format));
        
    }
    
}
