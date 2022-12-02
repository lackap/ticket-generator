package com.forum.ticketgenerator.exception;

import lombok.Data;

@Data
public class DiplomeCreationException extends Exception {

    private String errorMessage;

    public DiplomeCreationException (String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
