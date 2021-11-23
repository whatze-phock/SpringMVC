package com.example.SpringMVC.Service;

import com.example.SpringMVC.Model.Role;
import com.example.SpringMVC.Model.User;
import com.example.SpringMVC.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmilService emilService;


    public boolean addUser (User user) {
        if(userRepository.findByUsername(user.getUsername()) != null) {
           return false;
        }

        Set<Role> set = new HashSet<>();
        set.add(Role.USER);
        //set.add(Role.ADMIN);
        user.setActive(true);
        user.setRoles(set);
        user.setConfirmationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        String active = "Hello"+" "+user.getUsername()+" "+"check your email for a confirmation link"+" "+"http://localhost:8080/registration/activation/"+user.getConfirmationCode();
        emilService.sendEmail(user.getEmail(),"Confirmation",active);
        return true;
    }

    public boolean activeUser (String code) {
       User user =  userRepository.findByConfirmationCode(code);
       if(user ==null) {
           return false;
       }
       user.setConfirmationCode(null);
       userRepository.save(user);
       return true;

    }

}
