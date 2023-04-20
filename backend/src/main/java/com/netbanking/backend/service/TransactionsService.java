// Transaction service
package com.netbanking.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.netbanking.backend.model.TransactionRecord;
import com.netbanking.backend.model.UserRecord;
import com.netbanking.backend.repository.TransactionRecordRepository;
import com.netbanking.backend.repository.UserRecordRepository;

@Service
public class TransactionsService {

    @Autowired
    private UserRecordRepository userRepository;

    @Autowired
    private TransactionRecordRepository transactionsRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public List<TransactionRecord> getAllTransactions(String status) {
        return transactionsRepository.findTransactionsWithStatus(status);
    }

    public List<TransactionRecord> getAllTransactionsByUser(UserRecord user) {
        return userRepository.findTransactionRecordByAccountNumber(user.getAccountNumber());
    }

    public TransactionRecord getTransactionById(String id) {
        return transactionsRepository.findById(id).get();
    }

    public void addTransaction(TransactionRecord transaction) {
        applicationEventPublisher.publishEvent(transaction);
        transactionsRepository.save(transaction);
    }
}
