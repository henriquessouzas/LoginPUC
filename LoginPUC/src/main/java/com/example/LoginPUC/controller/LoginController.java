package com.example.LoginPUC.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login") //http://localhost:8080/login
    public String Login() {
        return "login";
    }
}