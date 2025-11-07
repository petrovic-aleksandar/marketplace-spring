package me.aco.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.aco.marketplace.model.Image;
import me.aco.marketplace.model.Item;

@Repository
public interface ImagesRepository extends JpaRepository<Image, Long> {
    List<Image> findByItemId(Long itemId);
    Image findFrontImageByItem(Item item);
}
