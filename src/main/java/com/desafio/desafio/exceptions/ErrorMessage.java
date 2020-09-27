package com.desafio.desafio.exceptions;

public class ErrorMessage {

    private int errorCode;
    private String message;

    public ErrorMessage() {
        
    }

    public ErrorMessage(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
    
}
