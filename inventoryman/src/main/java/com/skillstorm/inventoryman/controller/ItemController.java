package com.skillstorm.inventoryman.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.skillstorm.inventoryman.model.Item;
import com.skillstorm.inventoryman.repository.ItemRepository;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:8080") // Check this
@RestController
@RequestMapping("/api")
public class ItemController {
 
    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/items")
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id));
        return ResponseEntity.ok(item);
    }

    @PostMapping("/items")
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable long id, @RequestBody Item itemDetails) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id));
        
        item.setItemName(itemDetails.getItemName());
        item.setWeightLbs(itemDetails.getWeightLbs());
        item.setSizeCubicFt(itemDetails.getSizeCubicFt());
        item.setQuantity(itemDetails.getQuantity());
        
        Item updatedItem = itemRepository.save(item);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteItem(@PathVariable long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id));
        itemRepository.delete(item);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
