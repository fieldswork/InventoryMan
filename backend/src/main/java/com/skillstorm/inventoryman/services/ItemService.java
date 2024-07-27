package com.skillstorm.inventoryman.services;

import com.skillstorm.inventoryman.models.Item;
import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.repositories.ItemRepository;
import com.skillstorm.inventoryman.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item saveItem(Item item) {
        Warehouse warehouse = warehouseRepository.findById(item.getWarehouse().getId()).orElse(null);
        if (warehouse == null) {
            throw new IllegalArgumentException("Warehouse not found");
        }

        double currentSpaceUsed = warehouse.getItems().stream()
                                            .mapToDouble(existingItem -> existingItem.getQuantity() * existingItem.getSizeInCubicFt())
                                            .sum();
        double newItemSpace = item.getQuantity() * item.getSizeInCubicFt();

        if (currentSpaceUsed + newItemSpace > warehouse.getCapacity()) {
            throw new IllegalArgumentException("Warehouse capacity exceeded");
        }

        item.setWarehouse(warehouse);
        warehouse.getItems().add(item);
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item itemDetails) {
        Item existingItem = itemRepository.findById(id).orElse(null);
        if (existingItem == null) {
            throw new IllegalArgumentException("Item not found");
        }

        Warehouse warehouse = existingItem.getWarehouse();

        double currentSpaceUsed = warehouse.getItems().stream()
                                            .filter(item -> !item.getId().equals(id))
                                            .mapToDouble(item -> item.getQuantity() * item.getSizeInCubicFt())
                                            .sum();
        double updatedItemSpace = itemDetails.getQuantity() * itemDetails.getSizeInCubicFt();

        if (currentSpaceUsed + updatedItemSpace > warehouse.getCapacity()) {
            throw new IllegalArgumentException("Warehouse capacity exceeded");
        }

        existingItem.setName(itemDetails.getName());
        existingItem.setDescription(itemDetails.getDescription());
        existingItem.setQuantity(itemDetails.getQuantity());
        existingItem.setSizeInCubicFt(itemDetails.getSizeInCubicFt());
        return itemRepository.save(existingItem);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
