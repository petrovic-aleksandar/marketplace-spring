package me.aco.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.aco.marketplace.model.ItemType;

@Repository
public interface ItemTypesRepository extends JpaRepository<ItemType, Long> {
    
}
