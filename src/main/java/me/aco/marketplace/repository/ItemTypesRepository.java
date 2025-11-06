package me.aco.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.aco.marketplace.model.ItemType;

public interface ItemTypesRepository extends JpaRepository<ItemType, Long> {
    
}
