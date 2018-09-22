package com.ef.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ef.model.entity.AccessLog;


public interface AccessLogRepository extends CrudRepository<AccessLog, Long> {
    
    @Query("select new com.ef.model.AggregateResult( a.ip, a.accessTime,  count(a.ip) as total) from  AccessLog a  where a.accessTime between :startTime and  :endTime group by a.ip having total > :threshHold" )
    List<AccessLog> find(@Param("startTime") LocalDateTime start, @Param("endTime") LocalDateTime end, @Param("threshHold") int threshHold);
    
    List<AccessLog> findByIp(String ip);
}
