package me.aco.marketplace.controller;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.aco.marketplace.dto.LoginReq;
import me.aco.marketplace.dto.TokenResp;
import me.aco.marketplace.dto.UserRegReq;
import me.aco.marketplace.dto.UserResp;
import me.aco.marketplace.model.User;
import me.aco.marketplace.repository.UsersRepository;
import me.aco.marketplace.service.AuthService;
import me.aco.marketplace.util.JWTUtil;

@Async("asyncExecutor")
@RequestMapping("/Auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UsersRepository usersRepository;

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}

    @PostMapping(value = "/login")
    public CompletableFuture<TokenResp> login(@RequestBody LoginReq req) {
        User loadedUser = usersRepository.findByUsername(req.getUsername()).get(0);
		if (loadedUser == null) 
			return null;
		else if (!authService.checkPassword(req, loadedUser))
			return null;
		TokenResp resp = new TokenResp(JWTUtil.createToken(loadedUser), authService.createAndSaveRefreshToken(req));
		return CompletableFuture.completedFuture(resp);
    }

    @PostMapping(value = "/register")
    public CompletableFuture<ResponseEntity<UserResp>> register(@RequestBody UserRegReq req) throws NoSuchAlgorithmException {
		if (usersRepository.findByUsername(req.getUsername()).size() > 0)
		return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
		var addedUser = usersRepository.save(req.toUser());
		if (addedUser == null)
			return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
		return CompletableFuture.completedFuture(ResponseEntity.ok(new UserResp(addedUser)));
	}
    

}