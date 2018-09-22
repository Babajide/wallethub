package com.ef.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ef.model.Duration;
import com.ef.model.entity.AccessLog;
import com.ef.model.entity.BlockedRequest;
import com.ef.repository.AccessLogRepository;
import com.ef.repository.BlockedRequestRepository;
import com.ef.util.FileReader;


@Service
public class ParserServiceImpl implements ParserService {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final FileReader fileReader;
    private final AccessLogRepository accessLogRepository;
    private final BlockedRequestRepository blockedRequestRepository;
    
    @Autowired
    public ParserServiceImpl(final FileReader fileReader,
                             final AccessLogRepository accessLogRepository,
                             BlockedRequestRepository blockedRequestRepository) {
        this.fileReader = fileReader;
        this.accessLogRepository = accessLogRepository;
        this.blockedRequestRepository = blockedRequestRepository;
    }
    
    @Override
    public List<AccessLog> parse(String filePath) {
        logger.info("parse file from {}", filePath);
        List<AccessLog> accessLogs = fileReader.read(filePath);
        System.out.println(accessLogs.size());
        return accessLogs;
    }
    
    @Override
    @Transactional
    public Iterable<AccessLog> save(List<AccessLog> logs) {
        logger.info("Saving  logs to db");
        return accessLogRepository.saveAll(logs);
    }
    
    @Override
    public List<AccessLog> find(LocalDateTime start, Duration limit, int threshHold) {
        LocalDateTime end = start.plusHours(limit.getTime());
        return accessLogRepository.find(start, end, threshHold);
    }
    
    @Override
    public Long count() {
        return accessLogRepository.count();
    }
    
    @Override
    public void saveBlockedRequest(List<BlockedRequest> blockedRequests) {
        logger.info("Saving  blocked ips to db");
        blockedRequestRepository.saveAll(blockedRequests);
    }
    
}
