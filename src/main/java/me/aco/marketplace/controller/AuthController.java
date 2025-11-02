package me.aco.marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.aco.marketplace.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String Login() {
        return "auth!";
    }
    

}