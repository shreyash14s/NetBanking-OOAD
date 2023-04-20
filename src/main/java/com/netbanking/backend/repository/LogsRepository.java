package com.netbanking.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.netbanking.backend.model.LogsRecord;

@Repository
public interface LogsRepository extends MongoRepository<LogsRecord, String> {
}
