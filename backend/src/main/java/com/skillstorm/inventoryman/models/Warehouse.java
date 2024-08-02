package com.skillstorm.inventoryman.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int capacity;
    private double usedCapacity;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // Note that cascade is set to ALL, orphanRemoval is set to true,
                                                                                                                // If the warehouse is deleted, all items in the warehouse are deleted as well
    @JsonBackReference
    private List<Item> items;

    // Getters and setters
    public Long getId() { // Returns the id of the warehouse
        return id;
    }

    public void setId(Long id) { // Sets the id of the warehouse
        this.id = id;
    }

    public String getName() { // Returns the name of the warehouse
        return name;
    }

    public void setName(String name) { // Sets the name of the warehouse
        this.name = name;
    }

    public int getCapacity() { // returns the capacity of the warehouse
        return capacity;
    }

    public void setCapacity(int capacity) { // Sets the capacity of the warehouse
        this.capacity = capacity;
    }

    public double getUsedCapacity() { // Returns the used capacity of the warehouse
        return usedCapacity;
    }

    public void setUsedCapacity(double usedCapacity) { // Sets the used capacity of the warehouse
        this.usedCapacity = usedCapacity;
    }

    public List<Item> getItems() { // Returns the items in the warehouse
        return items;
    }

    public void setItems(List<Item> items) { // Sets the items in the warehouse
        this.items = items;
    }
}
