package com.netbanking.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document("taskRecord")
@Data
@Builder
public class TaskRecord {
    @Id
    private String id;
    private String description;
    private String type;
    private boolean done;
    private String status;
    private String userEmail;
    private String transactionId;
}
