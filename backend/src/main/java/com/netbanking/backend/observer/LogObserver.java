package com.netbanking.backend.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.netbanking.backend.observer.events.ApprovalEvent;
import com.netbanking.backend.observer.events.NewTransactionEvent;
import com.netbanking.backend.observer.events.NewUserEvent;
import com.netbanking.backend.observer.events.RejectEvent;
import com.netbanking.backend.service.LogService;

@Component
public class LogObserver {

    @Autowired
    private LogService logService;

    @EventListener
    public void handleNewTransactions(final NewTransactionEvent event) {
        logService.addLog("New transaction: " + event.getTransactionRecord());
    }

    @EventListener
    public void handleNewUser(final NewUserEvent event) {
        logService.addLog("New user: " + event.getUserRecord());
    }

    @EventListener
    public void handleApproval(final ApprovalEvent event) {
        logService.addLog("Admin '" + event.getAdminUserRecord().getUserEmail() + "' approved task-" + event.getTaskRecord());
    }

    @EventListener
    public void handleReject(final RejectEvent event) {
        logService.addLog("Admin '" + event.getAdminUserRecord().getUserEmail() + "' rejected task-" + event.getTaskRecord());
    }
}
