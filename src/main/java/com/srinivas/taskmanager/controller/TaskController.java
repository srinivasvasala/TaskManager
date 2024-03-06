package com.srinivas.taskmanager.controller;

import com.srinivas.taskmanager.payLoad.TaskDTO;
import com.srinivas.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //saving the task
    @PostMapping("/{userid}/tasks")
    public ResponseEntity<TaskDTO>saveTask(@PathVariable(name="userid")long userId, @RequestBody TaskDTO taskDTO){
        return new ResponseEntity<>(taskService.saveTask(userId,taskDTO), HttpStatus.CREATED);
    }

    //get All Tasks

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<TaskDTO>>getAllTasks(
            @PathVariable("userId")long userId
    ){
        return new ResponseEntity<>(taskService.getAllTasks(userId),HttpStatus.OK);
    }

   //get individual task
    @GetMapping("/{userid}/tasks/{taskid}")
    public ResponseEntity<TaskDTO>getTask(@PathVariable(name="userid")long userId,@PathVariable(name="taskid")long taskId){
        return new ResponseEntity<>(taskService.getTask(userId,taskId),HttpStatus.OK);
    }

    //delete individual Task
    @DeleteMapping("/{userid}//{taskid}")
    public ResponseEntity<String>deleteTask(@PathVariable(name="userid")long userId,@PathVariable(name="taskid")long taskId){
        return new ResponseEntity<>("Task Deleted Successfully",HttpStatus.OK);
    }

}
