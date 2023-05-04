package com.netbanking.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Document("transactionRecord")
@Data
@Builder
public class TransactionRecord {
    @Id
    private String transactionId;
    private Date dateTime;
    private String type;
    private Integer amount;
    private String status;
    private String description;
    private String destinationAccountNumber;
    private String sourceAccountNumber;
}
