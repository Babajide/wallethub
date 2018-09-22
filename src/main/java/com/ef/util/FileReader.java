package com.ef.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ef.model.entity.AccessLog;


@Component
public class FileReader {
    
    @Value("${application.default.delimiter}")
    private Character delimiter;
    
    @Value("${application.default.log.dateformat}")
    private String logDateFormat;
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    
    public List<AccessLog> read(String path) {
        logger.debug("About to read file from {}", path);
        List<AccessLog> accessLogs = new ArrayList<>();
        try {
            accessLogs = Files.lines(Paths.get(path)).map(mapToItem).collect(Collectors.toList());
        } catch (IOException e) {
           logger.error("error reading file:", e);
        }
        logger.debug("Found totoal {} logs", accessLogs.size());
        return accessLogs;
    }
    
    private Function<String, AccessLog> mapToItem = (line) -> {
        List<String> lines = Arrays.asList(line.replaceAll("\"", "").split(Pattern.quote(delimiter + "")));
        AccessLog item = new AccessLog();
        item.setAccessTime(DateUtil.toDate(lines.get(0), logDateFormat));
        item.setIp(lines.get(1));
        item.setRequestLine(lines.get(2));
        item.setStatus(Integer.valueOf(lines.get(3)));
        item.setUserAgent(lines.get(4));
        return item;
    };
    
}
