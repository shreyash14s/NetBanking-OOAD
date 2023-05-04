package com.netbanking.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.netbanking.backend.model.TaskRecord;

import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TasksRepository extends MongoRepository<TaskRecord, String> {
    @Query("{ 'taskId' : ?0 }")
    Optional<TaskRecord> findByTaskId(String taskId);

    @Query("{'done': false}}")
    List<TaskRecord> findPendingTask();

}
