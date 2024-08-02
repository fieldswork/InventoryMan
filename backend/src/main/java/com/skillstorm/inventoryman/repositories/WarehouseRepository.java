package com.skillstorm.inventoryman.repositories;

import com.skillstorm.inventoryman.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> { // WarehouseRepository interface that extends JpaRepository
}
