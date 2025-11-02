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

    private EntityManager em;

    public UserService() {
        this.em = emf.createEntityManager();
    }

   public User getByUsername(String username) {
		List<User> users = em.createNamedQuery(User.getByUsername, User.class).setParameter("username", username).getResultList();
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}
	
	public User saveUser(User user) {
		return em.merge(user);
	} 
    
}
