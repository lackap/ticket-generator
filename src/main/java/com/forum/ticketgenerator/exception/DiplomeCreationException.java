package com.forum.ticketgenerator.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class DiplomeCreationException extends Exception {

    private String errorMessage;

    public DiplomeCreationException (String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
