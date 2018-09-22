package com.ef.repository;

import org.springframework.data.repository.CrudRepository;

import com.ef.model.entity.BlockedRequest;


public interface BlockedRequestRepository extends CrudRepository<BlockedRequest, Long> {
    
}
