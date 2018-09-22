package com.ef.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ef.model.Duration;
import com.ef.model.entity.AccessLog;
import com.ef.model.entity.BlockedRequest;


public interface ParserService {
    
    List<AccessLog> parse(String filePath);
    Iterable<AccessLog> save(List<AccessLog> logs);
    List<AccessLog> find(LocalDateTime start, Duration limit, int threshHold);
    Long count();
    void saveBlockedRequest(List<BlockedRequest> blockedRequests);
    
    
}
