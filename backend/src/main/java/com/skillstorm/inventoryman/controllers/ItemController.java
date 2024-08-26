package com.skillstorm.inventoryman.controllers;

import com.skillstorm.inventoryman.models.Item;
import com.skillstorm.inventoryman.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping // Gets all items
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}") // Gets an item by id
    public Item getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @PostMapping // Creates a new item
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        try {
            return ResponseEntity.ok(itemService.saveItem(item));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}") // Updates an item by id
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        try {
            return ResponseEntity.ok(itemService.updateItem(id, itemDetails));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}") // Deletes an item by id
    public ResponseEntity<Item> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
