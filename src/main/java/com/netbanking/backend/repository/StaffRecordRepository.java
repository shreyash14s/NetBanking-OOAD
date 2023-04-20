package com.netbanking.backend.repository;

import com.netbanking.backend.model.StaffRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;

public interface StaffRecordRepository extends MongoRepository<StaffRecord, String> {
    @Query("{staffEmail:'?0', staffPassword:'?1'}")
    StaffRecord findStaffRecord(String staffEmail, String staffPassword);
}
