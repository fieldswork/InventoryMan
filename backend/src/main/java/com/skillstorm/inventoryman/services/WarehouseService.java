package com.skillstorm.inventoryman.services;

import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<Warehouse> getAllWarehouses() { // Method that returns all warehouses
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(Long id) { // Method that returns a warehouse by id
        return warehouseRepository.findById(id).orElse(null);
    }

    public Warehouse saveWarehouse(Warehouse warehouse) { // Method that creates a new warehouse
        return warehouseRepository.save(warehouse);
    }

    public Warehouse deleteWarehouse(Long id) { // Method that deletes a warehouse by id
        Warehouse warehouse = warehouseRepository.findById(id).orElse(null);
        warehouseRepository.deleteById(id);
        return warehouse;
    }

    public double getCurrentSpaceUsed(Long id) { // Method that returns the current space used in a warehouse by id
        Warehouse warehouse = warehouseRepository.findById(id).orElse(null);
        if (warehouse != null) {
            return warehouse.getUsedCapacity();
        }
        return 0;
    }
}
