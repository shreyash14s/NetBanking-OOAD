package com.netbanking.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Document("logRecord")
@Data
@Builder

public class LogsRecord {
    @Id
    private UUID id;
    private String description;
    private String Time;
}
