package com.netbanking.backend.observer.events;

import org.springframework.context.ApplicationEvent;

import com.netbanking.backend.model.TransactionRecord;

public class NewTransactionEvent extends ApplicationEvent {

    private TransactionRecord transactionRecord;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public NewTransactionEvent(final Object source, final TransactionRecord tran) {
        super(source);
        this.transactionRecord = tran;
    }

    public TransactionRecord getTransactionRecord() {
        return transactionRecord;
    }
}
