package com.netbanking.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Document("transactionRecord")
@Data
@Builder
public class TransactionRecord {
    @Id
    private UUID transactionId;
    private String transactionDateTime;
    private String transactionType;
    private String transactionAmount;
    private String transactionStatus;
    private String transactionDescription;
    private String transactionAccountNumber;
    private String transactionAccountName;
}
