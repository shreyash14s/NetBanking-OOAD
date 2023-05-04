package com.netbanking.backend.observer.events;

import org.springframework.context.ApplicationEvent;

import com.netbanking.backend.model.TaskRecord;
import com.netbanking.backend.model.UserRecord;

public class ApprovalEvent extends ApplicationEvent {

    private TaskRecord taskRecord;
    private UserRecord userRecord;

    public ApprovalEvent(final Object source, final TaskRecord task, final UserRecord user) {
        super(source);
        this.taskRecord = task;
        this.userRecord = user;
    }

    public TaskRecord getTaskRecord() {
        return taskRecord;
    }

    public UserRecord getAdminUserRecord() {
        return userRecord;
    }
}
