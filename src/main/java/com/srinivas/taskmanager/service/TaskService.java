package com.srinivas.taskmanager.service;

import com.srinivas.taskmanager.payLoad.TaskDTO;

import java.util.List;

public interface TaskService {
    public TaskDTO saveTask(long userId, TaskDTO taskDTO);
    public List<TaskDTO> getAllTasks(long userId );
    public TaskDTO getTask(long userId,long taskId);

    public void deleteTask(long userId,long taskId);
}
