package com.netbanking.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Document("userRecord")
@Data
@Builder
public class UserRecord {
    // @Id
    // private long id;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String accountNumber;
    private String accountType;
    private Integer accountBalance;
    private String accountStatus;
    private Date accountCreationDate;
    private String phone;
    private String dob;
    private String address;
    private String ssn;
    private String userToken;
    private List<TransactionRecord> transactionRecords;
}
