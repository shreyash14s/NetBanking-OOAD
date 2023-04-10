package com.netbanking.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import java.util.Map;
import java.util.UUID;

@Document("userRecord")
@Data
@Builder
public class UserRecord {
    @Id
    private UUID id;
    private String userName;
    private String accountNumber;
    private String accountType;
    private String accountBalance;
    private String accountStatus;
    private Map<String, TransactionRecords> transactionRecords;
}
