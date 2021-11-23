package com.example.SpringMVC.Controller;

import com.example.SpringMVC.Model.Role;
import com.example.SpringMVC.Model.User;
import com.example.SpringMVC.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;




    @GetMapping()
    public String getUsers (Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users",users);
        return "userPage";
    }

    @GetMapping("{id}")
    public String edit (@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user",user);
        return "userEdit";

    }
    @PostMapping
        public String editUser (@RequestParam(name = "id",required = false,defaultValue = "") String id,
                                @RequestParam (name = "username",required = false,defaultValue = "") String name,
                                @RequestParam(name = "password",required = false,defaultValue = "") String password,
                                @RequestParam (name = "user",required = false,defaultValue = "") String role,
                                @RequestParam(name = "admin",required = false,defaultValue = "") String role1, Model model) {

        Set<Role> set = new HashSet<>();
        if(role.equals("on")) {
            set.add(Role.USER);
        }
        if(role1.equals("on")) {
            set.add(Role.ADMIN);
        }

       User user = new User(Long.parseLong(id),name,password,set);
       userRepository.save(user);
       //model.addAttribute("user",user);
        return "redirect:/admin";

    }




}
