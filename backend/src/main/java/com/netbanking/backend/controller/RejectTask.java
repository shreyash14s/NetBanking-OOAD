package com.netbanking.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netbanking.backend.model.TaskRecord;
import com.netbanking.backend.model.TransactionRecord;
import com.netbanking.backend.model.UserRecord;
import com.netbanking.backend.repository.TasksRepository;
import com.netbanking.backend.service.TaskService;
import com.netbanking.backend.service.TransactionsService;
import com.netbanking.backend.service.UserService;

class RejectTaskRequest {
    public String taskId;
}

class RejectTaskResponse {
    public String message;
    public String status;
}

@RestController
public class RejectTask {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionsService transactionsService;
    
    @PostMapping("/api/admin/rejectTask")
    public ResponseEntity<RejectTaskResponse> approveTask(@RequestBody RejectTaskRequest request) {
        Optional<TaskRecord> task_opt = taskService.getTaskById(request.taskId);
        if (!task_opt.isPresent()) {
            RejectTaskResponse response = new RejectTaskResponse();
            response.message = "Task not found";
            response.status = "error";
            return ResponseEntity.badRequest().body(response);
        }
        TaskRecord task = task_opt.get();

        if ("approveUser".equals(task.getType())) {
            UserRecord user = userService.getUserByEmail(task.getUserEmail()).get();
            task.setStatus("rejected");
            userService.deleteUser(user);
            taskService.addTask(task);
        } else if ("approveTransaction".equals(task.getType())) {
            TransactionRecord transaction = transactionsService.getTransactionById(task.getTransactionId());
            transaction.setStatus("rejected");
            taskService.addTask(task);
            transactionsService.addTransaction(transaction);
        }

        RejectTaskResponse response = new RejectTaskResponse();
        response.message = "Task rejected";
        response.status = "success";
        return ResponseEntity.ok(response);
    }
}
