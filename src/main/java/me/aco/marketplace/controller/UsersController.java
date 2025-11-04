package me.aco.marketplace.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.aco.marketplace.dto.UserReq;
import me.aco.marketplace.dto.UserResp;
import me.aco.marketplace.repository.UsersRepository;

@Async  ("asyncExecutor")
@RequestMapping("/User")
@RestController
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserResp>> getUserById(@PathVariable("id") Long id) {

        if (id == null)
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());

        if (!usersRepository.existsById(id))
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());

        return CompletableFuture.supplyAsync(() -> {
            var user = usersRepository.findById(id).get();
            var resp = new UserResp(user);
            return ResponseEntity.ok(resp);
        });
        
    }

    @GetMapping("/")
    public CompletableFuture<ResponseEntity<List<UserResp>>> getAllUsers() {
        return CompletableFuture.supplyAsync(() -> {
            var users = usersRepository.findAll();
            var resp = users.stream().map(UserResp::new).toList();
            return ResponseEntity.ok(resp);
        });
    }

    @PostMapping("/")
    public CompletableFuture<ResponseEntity<UserResp>> createUser(UserReq req) throws NoSuchAlgorithmException {
        if (req == null) 
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());

        var user = usersRepository.save(req.toUser());
        if (user == null)
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());

        var resp = new UserResp(user);
        return CompletableFuture.completedFuture(ResponseEntity.ok(resp));
    }

    @PostMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserResp>> updateUser(@PathVariable("id") Long id, UserReq req) throws NoSuchAlgorithmException {
        if (req == null) 
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());

        var user = usersRepository.findById(id).orElse(null);
        if (user == null)
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());

        var resp = new UserResp(user);
        return CompletableFuture.completedFuture(ResponseEntity.ok(resp));
    }

    
}
