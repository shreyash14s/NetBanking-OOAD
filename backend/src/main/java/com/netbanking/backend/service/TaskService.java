package com.netbanking.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netbanking.backend.model.TaskRecord;
import com.netbanking.backend.repository.TasksRepository;

@Service
public class TaskService {
    
    @Autowired
    private TasksRepository tasksRepository;

    public List<TaskRecord> getAllPendingTask() {
        return tasksRepository.findPendingTask();
    }

    public Optional<TaskRecord> getTaskById(String id) {
        return tasksRepository.findById(id);
    }

    public void addTask(TaskRecord task) {
        tasksRepository.save(task);
    }
}
