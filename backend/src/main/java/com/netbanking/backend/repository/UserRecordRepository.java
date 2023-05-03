package com.netbanking.backend.repository;

import com.netbanking.backend.model.TransactionRecord;
import com.netbanking.backend.model.UserRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserRecordRepository extends MongoRepository<UserRecord, String> {
    @Query("{accountNumber:'?0'}")
    List<TransactionRecord> findTransactionRecordByAccountNumber(String accountNumber);

    @Query("{accountNumber:'?0'}")
    Optional<UserRecord> findUserByAccountNumber(String accountNumber);

    @Query("{userEmail:'?0'}")
    Optional<UserRecord> findUserByEmail(String userEmail);

    @Query("{accountEmail:'?0', accountPassword:'?1'}")
    Optional<UserRecord> findUserRecord(String accountEmail, String accountPassword);
}
