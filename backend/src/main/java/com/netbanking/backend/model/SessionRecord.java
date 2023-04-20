package com.netbanking.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Document("sessionRecord")
@Data
@Builder
public class SessionRecord {
    @Id
    private UUID id;
    private String token;
}
