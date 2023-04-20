package com.netbanking.backend.repository;

import com.netbanking.backend.model.TransactionRecord;
import com.netbanking.backend.model.UserRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface UserRecordRepository extends MongoRepository<UserRecord, String> {
    @Query("{accountNumber:'?0'}")
    List<TransactionRecord> findTransactionRecordByAccountNumber(String accountNumber);

    @Query("{accountEmail:'?0', accountPassword:'?1'}")
    UserRecord findUserRecord(String accountEmail, String accountPassword);

}
