package me.aco.marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.aco.marketplace.dto.LoginReq;
import me.aco.marketplace.dto.TokenResp;
import me.aco.marketplace.model.User;
import me.aco.marketplace.service.AuthService;
import me.aco.marketplace.service.UserService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public TokenResp login(LoginReq req) {
        User loadedUser = userService.getByUsername(req.getUsername());
		if (loadedUser == null)
			return null;
		else if (!authService.checkPassword(req, loadedUser))
			return null;
		return new TokenResp(JWTUtil.createToken(loadedUser), authService.createAndSaveRefreshToken(req));
    }
    

}