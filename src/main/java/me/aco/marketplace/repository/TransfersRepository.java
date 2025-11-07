package me.aco.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.aco.marketplace.model.Transfer;
import me.aco.marketplace.model.User;

@Repository
public interface TransfersRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findBySeller(User user);
    List<Transfer> findByBuyer(User user);
}
