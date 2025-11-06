package me.aco.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.aco.marketplace.model.Transfer;

public interface TransfersRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findByUserId(Long userId);
}
