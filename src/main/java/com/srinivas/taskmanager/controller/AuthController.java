package com.srinivas.taskmanager.controller;

import com.srinivas.taskmanager.payLoad.LoginDTO;
import com.srinivas.taskmanager.payLoad.UserDTO;
import com.srinivas.taskmanager.pojo.AuthRequest;
import com.srinivas.taskmanager.service.UserService;
import com.srinivas.taskmanager.serviceImpl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/auth/")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    //@PreAuthorize(value = "Role_Admin")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser( @RequestBody UserDTO userDTO){
        return  new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String>loginUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User Logged Successfully",HttpStatus.OK);

    }

    @PostMapping ("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest){
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
       if(authentication.isAuthenticated()) {
           return jwtService.generateToken(authRequest.getUsername());
       }else {
           throw  new UsernameNotFoundException("user not found");
       }
    }
}
