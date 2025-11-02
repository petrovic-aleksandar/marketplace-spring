package me.aco.marketplace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import me.aco.marketplace.model.User;

@Service
public class UserService {

    @PersistenceUnit(unitName = "marketplace")
	private EntityManagerFactory emf;

   public User getByUsername(String username) {
	EntityManager em = emf.createEntityManager();
		List<User> users = em.createNamedQuery(User.getByUsername, User.class).setParameter("username", username).getResultList();
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}
	
	public User saveUser(User user) {
		EntityManager em = emf.createEntityManager();
		return em.merge(user);
	} 
    
}
