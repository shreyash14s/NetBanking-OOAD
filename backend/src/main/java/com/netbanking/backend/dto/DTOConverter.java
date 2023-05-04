package com.netbanking.backend.dto;

import com.netbanking.backend.model.UserRecord;

public class DTOConverter {
    public static UserDTO from(UserRecord record) {
        UserDTO dto = new UserDTO();
        dto.setUserName(record.getUserName());
        dto.setUserEmail(record.getUserEmail());
        dto.setAccountNumber(record.getAccountNumber());
        dto.setAccountType(record.getAccountType());
        dto.setAccountBalance(record.getAccountBalance());
        dto.setAccountStatus(record.getAccountStatus());
        dto.setAccountCreationDate(record.getAccountCreationDate());
        dto.setPhone(record.getPhone());
        dto.setDob(record.getDob());
        dto.setAddress(record.getAddress());
        dto.setSsn(record.getSsn());
        dto.setUserToken(record.getUserToken());
        dto.setAdmin(record.isAdmin());
        return dto;
    }
}
