package com.netbanking.backend.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
    public String userName;
    public String userEmail;
    public String accountNumber;
    public String accountType;
    public Integer accountBalance;
    public String accountStatus;
    public Date accountCreationDate;
    public boolean isAdmin;
    public String phone;
    public String dob;
    public String address;
    public String ssn;
    public String userToken;
    // private List<TransactionRecordDto> transactionRecords;
}
