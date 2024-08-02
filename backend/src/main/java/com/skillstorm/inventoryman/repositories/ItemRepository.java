package com.skillstorm.inventoryman.repositories;

import com.skillstorm.inventoryman.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> { // ItemRepository interface that extends JpaRepository
}
