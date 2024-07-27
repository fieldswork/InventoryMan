package com.skillstorm.inventoryman.services;

import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }
    
    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    public Warehouse saveWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    public double getCurrentSpaceUsed(Long warehouseId) {
        Optional<Warehouse> warehouseOpt = warehouseRepository.findById(warehouseId);
        if (warehouseOpt.isPresent()) {
            Warehouse warehouse = warehouseOpt.get();
            return warehouse.getItems().stream()
                            .mapToDouble(item -> item.getQuantity() * item.getSizeInCubicFt())
                            .sum();
        } else {
            throw new IllegalArgumentException("Warehouse not found");
        }
    }
}
