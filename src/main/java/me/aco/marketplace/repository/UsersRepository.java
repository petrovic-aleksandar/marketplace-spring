package me.aco.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.aco.marketplace.model.User;

public interface UsersRepository extends JpaRepository<User, Integer> {
    List<User> findByUsername(String username);
}