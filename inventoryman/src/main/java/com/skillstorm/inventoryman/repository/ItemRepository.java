package com.skillstorm.inventoryman.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.skillstorm.inventoryman.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    
    Optional<Item> findById(long id);

    List<Item> findByItemName(String itemName);

    List<Item> findByWeightLbs(double weightLbs);

    List<Item> findBySizeCubicFt(double sizeCubicFt);

    List<Item> findByQuantity(int quantity);

    void delete(Optional<Item> item);
    
}
