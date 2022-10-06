package com.github.mysterix5.template.model.error;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class CustomErrorDTO {
    private String message;
    private List<String> subMessages = new ArrayList<>();

    public CustomErrorDTO(CustomException e){
        message = e.getMessage();
        subMessages = e.getSubMessages();
    }

    public CustomErrorDTO(Exception e){
        message = e.getMessage();
    }

    public CustomErrorDTO(String message, String ...subMessages){
        this.message = message;
        Collections.addAll(this.subMessages, subMessages);
    }
}
