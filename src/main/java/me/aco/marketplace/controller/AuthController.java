package me.aco.marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import me.aco.marketplace.dto.LoginReq;
import me.aco.marketplace.dto.TokenResp;
import me.aco.marketplace.dto.UserRegReq;
import me.aco.marketplace.model.User;
import me.aco.marketplace.service.AuthService;
import me.aco.marketplace.service.UserService;
import me.aco.marketplace.util.JWTUtil;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public TokenResp login(LoginReq req) {
        User loadedUser = userService.getByUsername(req.getUsername());
		if (loadedUser == null)
			return null;
		else if (!authService.checkPassword(req, loadedUser))
			return null;
		return new TokenResp(JWTUtil.createToken(loadedUser), authService.createAndSaveRefreshToken(req));
    }

    @PostMapping("/register")
    public String register(UserRegReq req) {
		if (userService.getByUsername(req.getUsername()) != null)
			return "Username already taken!";
		try {
			userService.saveUser(req.toUser());
			return "User created!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Ok";
		}
	}
    

}