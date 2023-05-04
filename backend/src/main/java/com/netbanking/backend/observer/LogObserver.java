package com.netbanking.backend.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.netbanking.backend.observer.events.NewTransactionEvent;
import com.netbanking.backend.service.LogService;

@Component
public class LogObserver {

    @Autowired
    private LogService logService;

    @EventListener
    public void handleNewTransactions(final NewTransactionEvent event) {
        logService.addLog("New transaction: " + event.getTransactionRecord().getType());
    }
}
