package com.skillstorm.inventoryman.controllers;

import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/warehouses")
public class WarehouseController {
    
    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouseById(@PathVariable Long id) {
        return warehouseService.getWarehouseById(id);
    }

    @PostMapping
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.saveWarehouse(warehouse);
    }

    @PutMapping("/{id}")
    public Warehouse updateWarehouse(@PathVariable Long id, @RequestBody Warehouse warehouseDetails) {
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        warehouse.setName(warehouseDetails.getName());
        warehouse.setCapacity(warehouseDetails.getCapacity());
        return warehouseService.saveWarehouse(warehouse);
    }

    @DeleteMapping("/{id}")
    public void deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
    }
}
