package me.aco.marketplace.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.aco.marketplace.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findSingleByUsername(String username);
}