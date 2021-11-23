package com.example.SpringMVC.Controller;

import com.example.SpringMVC.Model.Role;
import com.example.SpringMVC.Model.User;
import com.example.SpringMVC.Repository.UserRepository;
import com.example.SpringMVC.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("registration")

public class RegistrationController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

        @GetMapping
            public String show () {
            return "registration";
        }
        @PostMapping
        public String addUser (@RequestParam (name = "user",required = false, defaultValue = "") String user,
                               @RequestParam (name ="password", required = false, defaultValue = "") String password,
                               @RequestParam(name = "email", required = false, defaultValue = "") String email, Model model) {
            User user1 = new User(user,password,email);

            if (!userService.addUser(user1)) {
                return "registration";
            }
            return "login";

        }

        @GetMapping("activation/{code}")
        public String activation (@PathVariable String code, Model model) {
            boolean x = userService.activeUser(code);
            if(x) {
                model.addAttribute("Message", "user not activated");
            }
            else {
                model.addAttribute("Message","user activated");
            }

            return "redirect: /newsPage";
        }




}
