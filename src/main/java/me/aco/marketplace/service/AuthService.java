package me.aco.marketplace.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.aco.marketplace.dto.LoginReq;
import me.aco.marketplace.model.User;
import me.aco.marketplace.repository.UsersRepository;
import me.aco.marketplace.util.SecurityUtil;

@Service
public class AuthService {
	
	@Autowired
	private UsersRepository usersRepository;

    public boolean checkPassword(LoginReq req, User u) {
		if (u.getPassword().equals(SecurityUtil.get_SHA_512_SecurePassword(req.getPassword(), u.getSalt())))
			return true;
		else
			return false;		
	}
	
	public String createAndSaveRefreshToken(LoginReq req) {
		String refreshToken = RandomStringUtils.secureStrong().nextAlphanumeric(32);
		User loadedUser = usersRepository.findByUsername(req.getUsername()).get(0);
		loadedUser.setRefreshToken(refreshToken);
		loadedUser.setRefreshTokenExpiry(LocalDateTime.now().plus(7, ChronoUnit.DAYS));
		usersRepository.save(loadedUser);
		return refreshToken;
	}
    
}
