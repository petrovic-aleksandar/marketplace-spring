package me.aco.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.aco.marketplace.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
}