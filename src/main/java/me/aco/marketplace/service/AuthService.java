package me.aco.marketplace.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.aco.marketplace.exception.AuthenticationException;
import me.aco.marketplace.exception.ResourceNotFoundException;

import me.aco.marketplace.dto.LoginReq;
import me.aco.marketplace.model.User;
import me.aco.marketplace.repository.UsersRepository;
import me.aco.marketplace.util.SecurityUtil;

@Service
@Transactional
public class AuthService {

    private static final int REFRESH_TOKEN_LENGTH = 32;
    private static final int REFRESH_TOKEN_VALIDITY_DAYS = 7;
    
    private final UsersRepository usersRepository;

    public AuthService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

	public User authenticate(LoginReq loginRequest) {

        User user = usersRepository.findSingleByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!validatePassword(loginRequest, user))
            throw new AuthenticationException("Invalid credentials");

        return user;
    }

    public boolean validatePassword(LoginReq loginRequest, User user) {
        return SecurityUtil.verifyPassword(loginRequest.getPassword(), user.getPassword());
    }

    public String createAndSaveRefreshToken(User user) {

        String refreshToken = generateRefreshToken();
        updateUserRefreshToken(user, refreshToken);
        return refreshToken;
    }

    private void updateUserRefreshToken(User user, String refreshToken) {

        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiry(LocalDateTime.now().plus(REFRESH_TOKEN_VALIDITY_DAYS, ChronoUnit.DAYS));
        usersRepository.save(user);
    }

    private String generateRefreshToken() {
        
        return RandomStringUtils.secureStrong().nextAlphanumeric(REFRESH_TOKEN_LENGTH);
    }
}
