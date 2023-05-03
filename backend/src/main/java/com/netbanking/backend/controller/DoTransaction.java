// Spring controller for transactions
package com.netbanking.backend.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netbanking.backend.model.TransactionRecord;
import com.netbanking.backend.model.UserRecord;
import com.netbanking.backend.service.TransactionsService;
import com.netbanking.backend.service.UserService;


class TransactionRequest {
    public String userToken;
    public String accountNumber;
    public String transactionAmount;
    public String transactionType;
    // public String transactionDescription;
}

class TransactionResponse {
    public String message;
    public String status;
    public String accountBalance;
}

@RestController
@RequestMapping("/transactions")
// @CrossOrigin(origins = "http://localhost:4200")
public class DoTransaction {

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private UserService userService;
    
    // @GetMapping("/transactions")
    // public List<TransactionRecord> getAllTransactions(){
    //     return transactionsService.getAllTransactions();
    // }
    
    // @GetMapping("/transactions/{id}")
    // public Transactions getTransaction(@PathVariable int id) {
    //     return transactionsService.getTransaction(id);
    // }
    
    @PostMapping("/create")
    public TransactionResponse doTransaction(@RequestBody TransactionRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();

        Optional<UserRecord> user_opt = userService.getUserByEmail(email);
        if (user_opt.isEmpty()) {
            TransactionResponse response = new TransactionResponse();
            response.message = "Invalid login details";
            response.status = "error";
            return response;
        }
        UserRecord user = user_opt.get();

        int amount = 0;
        try {
            amount = Integer.parseInt(request.transactionAmount);
        } catch (NumberFormatException e) {
            TransactionResponse response = new TransactionResponse();
            response.message = "Invalid amount";
            response.status = "error";
            return response;
        }

        boolean pending = false;
        String desc = "";
        String destAccount = "";

        if (request.transactionType.equals("debit")) {
            Optional<UserRecord> receiver_opt = userService.getUserByAccountNumber(request.accountNumber);
            if (receiver_opt.isEmpty()) {
                TransactionResponse response = new TransactionResponse();
                response.message = "Invalid account number";
                response.status = "error";
                return response;
            }
            UserRecord receiver = receiver_opt.get();
            if (user.getAccountBalance() < amount) {
                TransactionResponse response = new TransactionResponse();
                response.message = "Insufficient funds";
                response.status = "error";
                return response;
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
            user.setAccountBalance(user.getAccountBalance() + Integer.parseInt(request.transactionAmount));

            desc = "Deposited " + amount + " to " + user.getAccountNumber();
        } else {
            TransactionResponse response = new TransactionResponse();
            response.message = "Invalid transaction type";
            response.status = "error";
            return response;
        }

        TransactionRecord transaction = TransactionRecord.builder()
            .transactionDateTime(new Date())
            .transactionType(request.transactionType)
            .transactionAmount(amount)
            .transactionStatus(pending ? "pending" : "success")
            .transactionDescription(desc)
            .transactionSourceAccountNumber(user.getAccountNumber())
            .transactionDestinationAccountNumber(destAccount)
            .build();
        
        user.getTransactionRecords().add(transaction);

        if (request.transactionType.equals("debit") && !pending) {
            Optional<UserRecord> receiver_opt = userService.getUserByAccountNumber(request.accountNumber);
            UserRecord receiver = receiver_opt.get();
            TransactionRecord transaction2 = TransactionRecord.builder()
                    .transactionDateTime(new Date())
                    .transactionType("credit")
                    .transactionAmount(amount)
                    .transactionStatus("success")
                    .transactionDescription(desc)
                    .transactionSourceAccountNumber(destAccount)
                    .transactionDestinationAccountNumber(user.getAccountNumber())
                    .build();

            receiver.getTransactionRecords().add(transaction2);
            userService.updateUser(receiver);
        }

        userService.updateUser(user);

        TransactionResponse response = new TransactionResponse();
        response.message = "Transaction successful";
        response.status = "success";
        response.accountBalance = String.valueOf(user.getAccountBalance());
        return response;
    }
}
