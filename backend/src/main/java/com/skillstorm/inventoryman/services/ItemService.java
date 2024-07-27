package com.skillstorm.inventoryman.services;

import com.skillstorm.inventoryman.models.Item;
import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    // ItemService.java
    public Item saveItem(Item item) {
        Warehouse warehouse = item.getWarehouse();
        double totalSize = warehouse.getItems().stream().mapToDouble(Item::getSizeInCubicFt).sum() + item.getSizeInCubicFt();
        if (totalSize > warehouse.getCapacity()) {
            throw new IllegalArgumentException("Warehouse capacity exceeded");
        }
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }    
}
