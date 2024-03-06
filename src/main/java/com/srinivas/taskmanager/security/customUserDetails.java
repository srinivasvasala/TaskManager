package com.srinivas.taskmanager.security;

import com.srinivas.taskmanager.Exception.UserNotFound;
import com.srinivas.taskmanager.entity.Users;
import com.srinivas.taskmanager.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class customUserDetails implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email).orElseThrow(
        ()->new UserNotFound(String.format("user with email: %d is not found", email)));
        Set<String> roles = new HashSet<>();
        roles.add("Role_Admin");
        return new User(user.getEmail(),user.getPassword(),userAuthorities(roles));
    }
    private Collection<? extends GrantedAuthority> userAuthorities(Set<String>roles){
        return roles.stream().map(
                role -> new SimpleGrantedAuthority(role)
        ).collect(Collectors.toList());
    }
}
