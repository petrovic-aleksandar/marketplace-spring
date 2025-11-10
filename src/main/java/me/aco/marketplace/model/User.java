package me.aco.marketplace.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import me.aco.marketplace.enums.UserRole;

@Entity
@Table(name = "appuser")
@NamedQueries(value = {
		@NamedQuery(name = User.getAll, query = "select u from User u order by u.id desc"),
		@NamedQuery(name = User.getById, query = "select u from User u where u.id = :id"),
		@NamedQuery(name = User.getByUsername, query = "select u from User u where u.username = :username")
		})
@Getter
@Setter
public class User {
	
	public static final String getAll = "GetAllUsers";
	public static final String getById = "GetUserById";
	public static final String getByUsername = "GetUserByUsername";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
	@SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq")
	private long id;
	@Column(unique = true)
	private String username;
	private String password;
	private String name;
	private String email;
	private String phone;
	private double balance;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	private boolean active;
	
	private String refreshToken;
	private LocalDateTime refreshTokenExpiry;
    
}
