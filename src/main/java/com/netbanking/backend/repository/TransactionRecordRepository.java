package com.netbanking.backend.repository;

import com.netbanking.backend.model.TransactionRecord;
import com.netbanking.backend.model.UserRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.netbanking.backend.model.UserRecord;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TransactionRecordRepository extends MongoRepository<TransactionRecord, String> {

    @Query("{transactionStatus:'?0'}")
    List<TransactionRecord> findTransactionsWithStatus(String status);
}
