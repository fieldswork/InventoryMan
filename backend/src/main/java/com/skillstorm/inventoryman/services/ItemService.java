package com.skillstorm.inventoryman.services;

import com.skillstorm.inventoryman.models.Item;
import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.repositories.ItemRepository;
import com.skillstorm.inventoryman.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        double additionalCapacity = item.getQuantity() * item.getSizeInCubicFt();
        double newUsedCapacity = warehouse.getUsedCapacity() + additionalCapacity;

        if (newUsedCapacity > warehouse.getCapacity()) {
            throw new IllegalArgumentException("Warehouse capacity exceeded");
        }

        warehouse.setUsedCapacity(newUsedCapacity);
        warehouseRepository.save(warehouse);
        
        item.setWarehouse(warehouse);
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElse(null);

        if (item != null) {
            Warehouse warehouse = warehouseRepository.findById(item.getWarehouse().getId()).orElse(null);
            if (warehouse != null) {
                double newUsedCapacity = warehouse.getUsedCapacity() - (item.getQuantity() * item.getSizeInCubicFt());
                warehouse.setUsedCapacity(newUsedCapacity);
                warehouseRepository.save(warehouse);
            }
            itemRepository.deleteById(id);
        }
    }

    public Item updateItem(Long id, Item itemDetails) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            Warehouse oldWarehouse = warehouseRepository.findById(item.getWarehouse().getId()).orElse(null);
            Warehouse newWarehouse = warehouseRepository.findById(itemDetails.getWarehouse().getId()).orElse(null);
    
            if (newWarehouse == null) {
                throw new IllegalArgumentException("New Warehouse not found");
            }
    
            if (oldWarehouse != null) {
                double oldItemSize = item.getQuantity() * item.getSizeInCubicFt();
                oldWarehouse.setUsedCapacity(oldWarehouse.getUsedCapacity() - oldItemSize);
                warehouseRepository.save(oldWarehouse);
            }

            double newItemSize = itemDetails.getQuantity() * itemDetails.getSizeInCubicFt();
            double newUsedCapacity = newWarehouse.getUsedCapacity() + newItemSize;
    
            if (newUsedCapacity > newWarehouse.getCapacity()) {
                throw new IllegalArgumentException("New Warehouse capacity exceeded");
            }
    
            newWarehouse.setUsedCapacity(newUsedCapacity);
            warehouseRepository.save(newWarehouse);
    
            item.setName(itemDetails.getName());
            item.setDescription(itemDetails.getDescription());
            item.setQuantity(itemDetails.getQuantity());
            item.setSizeInCubicFt(itemDetails.getSizeInCubicFt());
            item.setWarehouse(newWarehouse);
            return itemRepository.save(item);
        }
        return null;
    }
    
}

