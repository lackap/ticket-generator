package com.forum.ticketgenerator.exception;

import lombok.Data;

@Data
public class UserCreationException extends Exception {

    private String errorMessage;

    public UserCreationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
