package com.srinivas.taskmanager.serviceImpl;

import com.srinivas.taskmanager.entity.Users;
import com.srinivas.taskmanager.payLoad.UserDTO;
import com.srinivas.taskmanager.repository.UsersRepository;
import com.srinivas.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        //userDTO is not an entity into users
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Users users = userDtoToEntity(userDTO); //coverted userDto to entity
        Users savedUsers = usersRepository.save(users);
        return entityToUserDto(savedUsers);
    }


    public Users userDtoToEntity(UserDTO userDTO){
        Users users = new Users();
        users.setName(userDTO.getName());
        users.setEmail(userDTO.getEmail());
        users.setPassword(userDTO.getPassword());
        return users;
    }

    public UserDTO entityToUserDto(Users savedUsers){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUsers.getId());
        userDTO.setName(savedUsers.getName());
        userDTO.setEmail(savedUsers.getEmail());
        userDTO.setPassword(savedUsers.getPassword());
        return userDTO;
    }

}
