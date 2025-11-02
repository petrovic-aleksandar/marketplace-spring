package me.aco.marketplace.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import me.aco.marketplace.dto.LoginReq;
import me.aco.marketplace.model.User;
import me.aco.marketplace.util.SecurityUtil;

@Service
public class AuthService {

    @PersistenceUnit(unitName = "marketplace")
	private EntityManagerFactory emf;

    private EntityManager em;

    public AuthService() {
        this.em = emf.createEntityManager();
    }

    public boolean checkPassword(LoginReq req, User u) {
		if (u.getPassword().equals(SecurityUtil.get_SHA_512_SecurePassword(req.getPassword(), u.getSalt())))
			return true;
		else
			return false;		
	}
	
	public String createAndSaveRefreshToken(LoginReq req) {
		String refreshToken = RandomStringUtils.secureStrong().nextAlphanumeric(32);
		User loadedUser = em.createNamedQuery(User.getByUsername, User.class).setParameter("username", req.getUsername()).getResultList().get(0);
		loadedUser.setRefreshToken(refreshToken);
		loadedUser.setRefreshTokenExpiry(LocalDateTime.now().plus(7, ChronoUnit.DAYS));
		em.merge(loadedUser);
		return refreshToken;
	}
    
}
