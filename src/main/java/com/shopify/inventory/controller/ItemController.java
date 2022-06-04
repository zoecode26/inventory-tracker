package com.shopify.inventory.controller;

import com.shopify.inventory.dao.ItemDAO;
import com.shopify.inventory.model.Item;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/items")
public class ItemController {
    private final ItemDAO itemDAO;

    public ItemController(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @GetMapping("")
    public Iterable<Item> getItems() {
        return itemDAO.findAll();
    }

    @GetMapping("/{item_id}")
    public Optional<Item> getVegetable(@PathVariable(value = "item_id") Long item_id) {
        return itemDAO.findById(item_id);
    }

    @PostMapping("")
    public ResponseEntity<?> writeItem(@RequestBody Item item) {
        itemDAO.save(item);
        return ResponseEntity.ok().build();
    }
}
