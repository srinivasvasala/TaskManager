package com.srinivas.taskmanager.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFound extends RuntimeException {
    private  String message;
    public UserNotFound(String message){
        super(message);
        this.message = message;
    }

}
