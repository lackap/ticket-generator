package com.forum.ticketgenerator.exception;

import lombok.Data;

@Data
public class PosteCreationException extends Exception {

    private String errorMessage;

    public PosteCreationException (String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
