package me.aco.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.aco.marketplace.model.Item;

public interface ItemsRepository extends JpaRepository<Item, Long> {
    List<Item> getItemsBySellerId(Long sellerId);
    List<Item> getItemsByTypeId(Long typeId);
}
