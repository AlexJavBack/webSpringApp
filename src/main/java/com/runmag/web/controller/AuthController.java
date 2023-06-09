package com.runmag.web.controller;

import com.runmag.web.dto.RegistrationDto;
import com.runmag.web.models.User;
import com.runmag.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model){
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("register/save")
    public String register(@Valid @ModelAttribute("user")RegistrationDto user,
                           BindingResult bindingResult, Model model){
        User userEmail  = userService.findByEmail(user.getEmail());
        if(userEmail != null && userEmail.getEmail() != null && !userEmail.getEmail().isEmpty()){
            //bindingResult.rejectValue("email", "There is already a user with this email or username");
            return "redirect:/register?fail";
        }
        User userName = userService.findByUserName(user.getUserName());

        if(userName != null && userName.getUserName() != null && !userName.getUserName().isEmpty()){
            //bindingResult.rejectValue("username", "There is already a user with this email or username");
            return "redirect:/register?fail";
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("user", user);
            return "register";
        }

        userService.saveUser(user);
        return "redirect:/clubs?success";
    }
}
