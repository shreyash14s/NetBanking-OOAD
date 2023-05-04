package com.netbanking.backend.observer.events;

import org.springframework.context.ApplicationEvent;

import com.netbanking.backend.model.UserRecord;

public class NewUserEvent extends ApplicationEvent {

    private UserRecord userRecord;

    public NewUserEvent(final Object source, final UserRecord user) {
        super(source);
        this.userRecord = user;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }
}
