package me.aco.marketplace.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import me.aco.marketplace.dto.UserReq;
import me.aco.marketplace.dto.UserResp;
import me.aco.marketplace.enums.UserRole;
import me.aco.marketplace.repository.UsersRepository;
import me.aco.marketplace.service.UserService;

@Async("asyncExecutor")
@RequestMapping("/User")
@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersRepository usersRepository;
    private final UserService userService;

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserResp>> getUserById(@PathVariable("id") long id) {

        // id is primitive long and cannot be null

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
    public CompletableFuture<ResponseEntity<UserResp>> createUser(@RequestBody UserReq req) {
        if (req == null) 
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());

        var sameUsernameUser = usersRepository.findSingleByUsername(req.getUsername());
        if (sameUsernameUser.isPresent())
            return CompletableFuture.completedFuture(ResponseEntity.status(409).build());

        var newUser = userService.toUser(req);
        if (newUser == null)
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        var addedUser = usersRepository.save(newUser);

        var resp = new UserResp(addedUser);
        return CompletableFuture.completedFuture(ResponseEntity.ok(resp));
    }

    @PostMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserResp>> updateUser(@PathVariable("id") long id, @RequestBody UserReq req) {
        if (req == null) 
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());

        var user = usersRepository.findById(id).orElse(null);
        if (user == null)
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());

        var updatedUser = userService.update(req, user);
        var resp = new UserResp(updatedUser);
        return CompletableFuture.completedFuture(ResponseEntity.ok(resp));
    }

    @PostMapping("/deactivate/{id}")
    public CompletableFuture<ResponseEntity<UserResp>> deactivateUser(@PathVariable("id") long id) {
        var user = usersRepository.findById(id).orElse(null);
        if (user == null)
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());

        user.setActive(false);
        var updatedUser = usersRepository.save(user);
        var resp = new UserResp(updatedUser);
        return CompletableFuture.completedFuture(ResponseEntity.ok(resp));
    }

    @PostMapping("/activate/{id}")
    public CompletableFuture<ResponseEntity<UserResp>> activateUser(@PathVariable("id") long id) {
        var user = usersRepository.findById(id).orElse(null);
        if (user == null)
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());

        user.setActive(true);
        var updatedUser = usersRepository.save(user);
        var resp = new UserResp(updatedUser);
        return CompletableFuture.completedFuture(ResponseEntity.ok(resp));
    }

    @GetMapping("/roles")
    public CompletableFuture<ResponseEntity<List<String>>> getUserRoles() {
        List<String> result = new ArrayList<>();
		UserRole[] roles =UserRole.values();
		for (UserRole userRole : roles)
			result.add(userRole.toString());
		return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(result));
	}

    
}
