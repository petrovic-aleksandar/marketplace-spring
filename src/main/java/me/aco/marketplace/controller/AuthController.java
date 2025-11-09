package me.aco.marketplace.controller;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public CompletableFuture<ResponseEntity<TokenResp>> login(@RequestBody LoginReq req) {
        return CompletableFuture.supplyAsync(() -> 
            usersRepository.findSingleByUsername(req.getUsername())
                .map(loadedUser -> {
                    if (!authService.checkPassword(req, loadedUser)) {
                        return ResponseEntity.badRequest().<TokenResp>build();
                    }
                    TokenResp resp = new TokenResp(
                        JWTUtil.createToken(loadedUser), 
                        authService.createAndSaveRefreshToken(req)
                    );
                    return ResponseEntity.ok(resp);
                })
                .orElse(ResponseEntity.notFound().<TokenResp>build())
        );
    }

    @PostMapping(value = "/register")
    public CompletableFuture<ResponseEntity<UserResp>> register(@RequestBody UserRegReq req) {
        return CompletableFuture.supplyAsync(() ->
            usersRepository.findSingleByUsername(req.getUsername())
                .map(existingUser -> ResponseEntity.badRequest().<UserResp>build())
                .orElseGet(() -> {
                    try {
                        User addedUser = usersRepository.save(req.toUser());
                        return ResponseEntity.ok(new UserResp(addedUser));
                    } catch (NoSuchAlgorithmException e) {
                        return ResponseEntity.internalServerError().<UserResp>build();
                    }
                })
        );
    }
    

}