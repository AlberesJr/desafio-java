package com.desafio.desafio.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {

    private int errorCode;
    private String message;

    public ErrorMessage() {
        
    }

    public ErrorMessage(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
    
}
