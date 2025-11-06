package me.aco.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.aco.marketplace.dto.ItemReq;
import me.aco.marketplace.dto.ItemResp;
import me.aco.marketplace.model.Item;
import me.aco.marketplace.model.ItemType;
import me.aco.marketplace.model.User;
import me.aco.marketplace.repository.ItemsRepository;

@Service
public class ItemService {

    @Autowired
    private ItemsRepository itemsRepository;

    public ItemResp add(ItemReq req, ItemType type, User seller) {
        Item item = new Item();
        item.setName(req.getName());
        item.setDescription(req.getDescription());
        item.setPrice(req.getPrice());
        item.setSeller(seller);
        item.setType(type);
        Item savedItem = itemsRepository.save(item);
        return new ItemResp(savedItem);
    }

    public ItemResp update(ItemReq req, ItemType type, User seller, Item existingItem) {
        existingItem.setName(req.getName());
        existingItem.setDescription(req.getDescription());
        existingItem.setPrice(req.getPrice());
        existingItem.setSeller(seller);
        existingItem.setType(type);
        Item updatedItem = itemsRepository.save(existingItem);
        return new ItemResp(updatedItem);
    }
    
}
