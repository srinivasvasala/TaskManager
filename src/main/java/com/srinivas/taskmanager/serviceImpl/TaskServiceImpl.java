package com.srinivas.taskmanager.serviceImpl;

import com.srinivas.taskmanager.Exception.APIException;
import com.srinivas.taskmanager.Exception.TaskNotFound;
import com.srinivas.taskmanager.Exception.UserNotFound;
import com.srinivas.taskmanager.entity.Task;
import com.srinivas.taskmanager.entity.Users;
import com.srinivas.taskmanager.payLoad.TaskDTO;
import com.srinivas.taskmanager.repository.TaskRepository;
import com.srinivas.taskmanager.repository.UsersRepository;
import com.srinivas.taskmanager.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public TaskDTO saveTask(long userId, TaskDTO taskDTO) {
       Users user = usersRepository.findById(userId).orElseThrow(
               () ->{
                   return new UserNotFound(String.format("user Id %d Not Found", userId));
               }
       );
        Task task = modelMapper.map(taskDTO,Task.class);
        task.setUsers(user);
        //After set the user data inserting data into the database
        Task savedTask = taskRepository.save(task);
        return modelMapper.map(savedTask,TaskDTO.class);
    }

    @Override
    public List<TaskDTO> getAllTasks(long userId) {
        usersRepository.findById(userId).orElseThrow(
                () ->{
                    return new UserNotFound(String.format("user Id %d Not Found", userId));
                }
        );
        List<Task> tasks = taskRepository.findAllByUsersId(userId);
        return tasks.stream().map(
                task -> modelMapper.map(task,TaskDTO.class)
                ).collect(Collectors.toList());
    }

    @Override
    public TaskDTO getTask(long userId, long taskId) {
       Users users= usersRepository.findById(userId).orElseThrow(
                () ->{
                    return new UserNotFound(String.format("user Id %d Not Found", userId));
                }
        );
       Task task = taskRepository.findById(taskId).orElseThrow(
               () ->{
                   return new TaskNotFound(String.format("Task Id %d Not Found",taskId));
               }
       );
       if(users.getId()!= task.getId()){
              throw new  APIException(String.format("Task Id %d is not belongs to user Id %d",userId,taskId));
       }
       return modelMapper.map(task,TaskDTO.class);
    }

    @Override
    public void deleteTask(long userId, long taskId) {
        Users users= usersRepository.findById(userId).orElseThrow(
                () ->{
                    return new UserNotFound(String.format("user Id %d Not Found", userId));
                }
        );
        Task task = taskRepository.findById(taskId).orElseThrow(
                () ->{
                    return new TaskNotFound(String.format("Task Id %d Not Found",taskId));
                }
        );
        if(users.getId()!= task.getId()){
            throw new  APIException(String.format("Task Id %d is not belongs to user Id %d",userId,taskId));
        }
        taskRepository.deleteById(taskId);
    }

}
