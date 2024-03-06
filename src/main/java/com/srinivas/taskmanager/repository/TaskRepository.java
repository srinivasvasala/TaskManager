package com.srinivas.taskmanager.repository;

import com.srinivas.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByUsersId(long userId);
}
