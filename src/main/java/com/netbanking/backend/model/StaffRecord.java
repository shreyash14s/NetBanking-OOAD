package com.netbanking.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Document("staffRecord")
@Data
@Builder

public class StaffRecord {
    @Id
    private UUID staffId;
    private String staffName;
    private String staffEmail;
    private String staffPassword;
}
