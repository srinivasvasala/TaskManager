package com.srinivas.taskmanager.repository;

import com.srinivas.taskmanager.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {
   Optional<Users> findByEmail(String email);
}
