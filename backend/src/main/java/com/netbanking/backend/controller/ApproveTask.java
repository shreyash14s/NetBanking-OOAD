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

class ApproveTaskRequest {
    public String taskId;
}

class ApproveTaskResponse {
    public String message;
    public String status;
}

@RestController
public class ApproveTask {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionsService transactionsService;
    
    @PostMapping("/api/admin/approveTask")
    public ResponseEntity<ApproveTaskResponse> approveTask(@RequestBody ApproveTaskRequest request) {
        Optional<TaskRecord> task_opt = taskService.getTaskById(request.taskId);
        if (!task_opt.isPresent()) {
            ApproveTaskResponse response = new ApproveTaskResponse();
            response.message = "Task not found";
            response.status = "error";
            return ResponseEntity.badRequest().body(response);
        }
        TaskRecord task = task_opt.get();

        if ("approveUser".equals(task.getType())) {
            UserRecord user = userService.getUserByEmail(task.getUserEmail()).get();
            user.setAccountStatus("active");
            task.setDone(true);
            task.setStatus("approved");
            taskService.addTask(task);
            userService.updateUser(user);
        } else if ("approveTransaction".equals(task.getType())) {
            TransactionRecord transaction = transactionsService
                    .getTransactionById(task.getTransactionId());
            UserRecord user = userService.getUserByEmail(task.getUserEmail()).get();
            int amount = transaction.getAmount();
            user.setAccountBalance(user.getAccountBalance() - amount);

            Optional<UserRecord> toUser_opt = userService
                    .getUserByAccountNumber(transaction.getDestinationAccountNumber());
            if (!toUser_opt.isPresent()) {
                ApproveTaskResponse response = new ApproveTaskResponse();
                response.message = "Destination account not found";
                response.status = "error";
                return ResponseEntity.badRequest().body(response);
            }

            UserRecord toUser = toUser_opt.get();
            toUser.setAccountBalance(toUser.getAccountBalance() + amount);
            userService.updateUser(toUser);

            transaction.setStatus("approved");
            task.setDone(true);
            task.setStatus("approved");
            taskService.addTask(task);
            userService.updateUser(user);
            transactionsService.addTransaction(transaction);
        }

        ApproveTaskResponse response = new ApproveTaskResponse();
        response.message = "Task approved";
        response.status = "success";
        return ResponseEntity.ok(response);
    }
}
