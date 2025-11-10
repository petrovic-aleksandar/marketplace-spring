package me.aco.marketplace.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import me.aco.marketplace.dto.ItemReq;
import me.aco.marketplace.dto.ItemResp;
import me.aco.marketplace.model.ItemType;
import me.aco.marketplace.repository.ItemTypesRepository;
import me.aco.marketplace.repository.ItemsRepository;
import me.aco.marketplace.repository.UsersRepository;
import me.aco.marketplace.service.ItemService;

@Async("asyncExecutor")
@RequestMapping("/Auth")
@RestController
@RequiredArgsConstructor
public class ItemsController {
    
    private final ItemsRepository itemsRepository;
    private final UsersRepository usersRepository;
    private final ItemTypesRepository itemTypesRepository;
    private final ItemService itemService;

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ItemResp>> getItemById(@PathVariable("id") long id) {

        // id is primitive, cannot be null; removed null check

        if (!itemsRepository.existsById(id))
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());

        return CompletableFuture.supplyAsync(() -> {
            var item = itemsRepository.findById(id).get();
            var resp = new ItemResp(item);
            return ResponseEntity.ok(resp);
        }); 
    }

    @GetMapping("/bySellerId/{sellerId}")
    public CompletableFuture<ResponseEntity<List<ItemResp>>> getItemsBySellerId(@PathVariable("sellerId") long sellerId) {
        return CompletableFuture.supplyAsync(() -> {
            var items = itemsRepository.getItemsBySellerId(sellerId);
            var resp = items.stream().map(ItemResp::new).toList();
            return ResponseEntity.ok(resp);
        });
    }

    @GetMapping("/byTypeId/{typeId}")
    public CompletableFuture<ResponseEntity<List<ItemResp>>> getItemsByTypeId(@PathVariable("typeId") long typeId) {
        return CompletableFuture.supplyAsync(() -> {
            var items = itemsRepository.getItemsByTypeId(typeId);
            var resp = items.stream().map(ItemResp::new).toList();
            return ResponseEntity.ok(resp);
        });
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<ItemResp>> addItem(@RequestBody ItemReq req) {
        var seller = usersRepository.findById(req.getSellerId());
        if (seller.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var itemType = itemTypesRepository.findById(req.getTypeId());
        if (itemType.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var addedItem = itemService.add(req, itemType.get(), seller.get());
        if (addedItem == null)
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        return CompletableFuture.completedFuture(ResponseEntity.ok(addedItem));
    }

    @PostMapping("/{itemId}")
    public CompletableFuture<ResponseEntity<ItemResp>> updateItem(@PathVariable("itemId") long itemId, ItemReq req) {
        var existingItemOpt = itemsRepository.findById(itemId);
        if (existingItemOpt.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var seller = usersRepository.findById(req.getSellerId());
        if (seller.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var itemType = itemTypesRepository.findById(req.getTypeId());
        if (itemType.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var updatedItem = itemService.update(req, itemType.get(), seller.get(), existingItemOpt.get());
        if (updatedItem == null)
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        return CompletableFuture.completedFuture(ResponseEntity.ok(updatedItem));
    }

    @PostMapping("/Deactivate/{id}")
    public CompletableFuture<ResponseEntity<ItemResp>> deactivateItem(@PathVariable("id") long id) {
        var existingItemOpt = itemsRepository.findById(id);
        if (existingItemOpt.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var existingItem = existingItemOpt.get();
        existingItem.setActive(false);
        var updatedItem = itemsRepository.save(existingItem);
        return CompletableFuture.completedFuture(ResponseEntity.ok(new ItemResp(updatedItem)));
    }

    @PostMapping("/Activate/{id}")
    public CompletableFuture<ResponseEntity<ItemResp>> activateItem(@PathVariable("id") long id) {
        var existingItemOpt = itemsRepository.findById(id);
        if (existingItemOpt.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var existingItem = existingItemOpt.get();
        existingItem.setActive(true);
        var updatedItem = itemsRepository.save(existingItem);
        return CompletableFuture.completedFuture(ResponseEntity.ok(new ItemResp(updatedItem)));
    }

    @PostMapping("/Delete/{id}")
    public CompletableFuture<ResponseEntity<ItemResp>> deleteItem(@PathVariable("id") long id) {
        var existingItemOpt = itemsRepository.findById(id);
        if (existingItemOpt.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var existingItem = existingItemOpt.get();
        existingItem.setDeleted(true);
        var updatedItem = itemsRepository.save(existingItem);
        return CompletableFuture.completedFuture(ResponseEntity.ok(new ItemResp(updatedItem)));
    }

    @GetMapping("/Types")
    public CompletableFuture<ResponseEntity<List<ItemType>>> getItemTypes() {
        return CompletableFuture.supplyAsync(() -> {
            var types = itemTypesRepository.findAll().stream().toList();
            return ResponseEntity.ok(types);
        });
    }

}