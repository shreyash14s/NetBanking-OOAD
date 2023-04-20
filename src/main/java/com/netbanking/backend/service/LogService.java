package com.netbanking.backend.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netbanking.backend.model.LogsRecord;
import com.netbanking.backend.repository.LogsRepository;

@Service
public class LogService {

    @Autowired
    private LogsRepository logsRepository;

    public void addLog(String log) {
        LogsRecord logRecord = LogsRecord.builder()
                .description(log)
                .Time(new Date())
                .build();
        logsRepository.save(logRecord);
    }
}
