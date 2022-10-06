package com.github.mysterix5.template.model.error;

import java.util.List;

public class CustomException extends RuntimeException {
    private List<String> subMessages;

    public CustomException(String message){
        super(message);
    }

    public CustomException(String message, Throwable cause){
        super(message,cause);
    }

    public CustomException(String message, List<String> subMessages, Throwable cause){
        super(message,cause);
        this.subMessages = subMessages;
    }
    public CustomException(String message, List<String> subMessages){
        super(message);
        this.subMessages = subMessages;
    }
    public List<String> getSubMessages() {
        return this.subMessages;
    }
}
