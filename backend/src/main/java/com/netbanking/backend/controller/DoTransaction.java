// Spring controller for transactions
package com.netbanking.backend.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netbanking.backend.model.TaskRecord;
import com.netbanking.backend.model.TransactionRecord;
import com.netbanking.backend.model.UserRecord;
import com.netbanking.backend.security.UserDetailsImpl;
import com.netbanking.backend.service.TaskService;
import com.netbanking.backend.service.TransactionsService;
import com.netbanking.backend.service.UserService;


class TransactionRequest {
    public String toAccountNumber;
    public String transactionAmount;
    public String transactionType;
    // public String transactionDescription;
}

class TransactionResponse {
    public String message;
    public String status;
    public String accountBalance;
    public boolean pending;
}

@RestController
@RequestMapping("/api/transactions")
public class DoTransaction {

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TransactionResponse> doTransaction(@RequestBody TransactionRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        UserRecord user = userDetails.getUser();

        int amount = 0;
        try {
            amount = Integer.parseInt(request.transactionAmount);
            if (amount <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            TransactionResponse response = new TransactionResponse();
            response.message = "Invalid amount";
            response.status = "error";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        boolean pending = false;
        String desc = "";
        String destAccount = "";
        String sourAccount = user.getAccountNumber();

        if (request.transactionType.equals("transfer")) {
            Optional<UserRecord> receiver_opt = userService.getUserByAccountNumber(request.toAccountNumber);
            if (receiver_opt.isEmpty()) {
                TransactionResponse response = new TransactionResponse();
                response.message = "Invalid account number";
                response.status = "error";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            UserRecord receiver = receiver_opt.get();
            if (user.getAccountBalance() < amount) {
                TransactionResponse response = new TransactionResponse();
                response.message = "Insufficient funds";
                response.status = "error";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (amount > 500) {
                pending = true;
            } else {
                user.setAccountBalance(user.getAccountBalance() - amount);
                receiver.setAccountBalance(receiver.getAccountBalance() + amount);
            }

            desc = "Debited " + amount + " from " + user.getAccountNumber() + " to " + receiver.getAccountNumber();
            destAccount = receiver.getAccountNumber();
        } else if (request.transactionType.equals("deposit")) {
            user.setAccountBalance(user.getAccountBalance() + amount);
            destAccount = user.getAccountNumber();
            sourAccount = "";

            desc = "Deposited " + amount + " to " + user.getAccountNumber();
        } else {
            TransactionResponse response = new TransactionResponse();
            response.message = "Invalid transaction type";
            response.status = "error";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        TransactionRecord transaction = TransactionRecord.builder()
            .dateTime(new Date())
            .type(request.transactionType)
            .amount(amount)
            .status(pending ? "pending" : "success")
            .description(desc)
            .sourceAccountNumber(sourAccount)
            .destinationAccountNumber(destAccount)
            .build();
        
        transactionsService.addTransaction(transaction);
        
        user.getTransactionRecords().add(transaction.getTransactionId());

        if (pending) {
            TaskRecord task = TaskRecord.builder()
                .description("Transfer " + amount + " from " + user.getAccountNumber() + " to " + request.toAccountNumber)
                .type("approveTransaction")
                .status("pending")
                .done(false)
                .userEmail(user.getUserEmail())
                .transactionId(transaction.getTransactionId())
                .build();

            taskService.addTask(task);
        }

        if (request.transactionType.equals("transfer")) {
            Optional<UserRecord> receiver_opt = userService.getUserByAccountNumber(request.toAccountNumber);
            UserRecord receiver = receiver_opt.get();

            if (!pending) {
                receiver.setAccountBalance(receiver.getAccountBalance() + amount);
            }
            receiver.getTransactionRecords().add(transaction.getTransactionId());
            userService.updateUser(receiver);
        }

        userService.updateUser(user);

        TransactionResponse response = new TransactionResponse();
        response.message = "Transaction successful";
        response.status = "success";
        response.accountBalance = String.valueOf(user.getAccountBalance());
        response.pending = pending;
        return ResponseEntity.ok(response);
    }
}
