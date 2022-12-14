package com.forum.ticketgenerator.exception;

import lombok.Data;

@Data
public class ModelCreationException extends Exception {

    private String errorMessage;

    public ModelCreationException (String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
