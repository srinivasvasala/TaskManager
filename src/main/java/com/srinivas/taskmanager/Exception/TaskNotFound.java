package com.srinivas.taskmanager.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class TaskNotFound extends RuntimeException{
    private String message;
    public TaskNotFound(String message){
        super(message);
        this.message=message;
    }
}
