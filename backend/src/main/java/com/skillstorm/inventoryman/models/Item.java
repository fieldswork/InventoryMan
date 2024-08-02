package com.skillstorm.inventoryman.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "items")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int quantity;
    private double sizeInCubicFt;

    @ManyToOne(fetch = FetchType.EAGER) // Many items can belong to one warehouse
    @JoinColumn(name = "warehouse_id", nullable = false) // Foreign key
    private Warehouse warehouse;

    // Getters and setters
    public Long getId() { // Returns the id of the item
        return id;
    }

    public void setId(Long id) { // Sets the id of the item
        this.id = id;
    }

    public String getName() { // Returns the name of the item
        return name;
    }

    public void setName(String name) { // Sets the name of the item
        this.name = name;
    }

    public String getDescription() { // Returns the description of the item
        return description;
    }

    public void setDescription(String description) { // Sets the description of the item
        this.description = description;
    }

    public int getQuantity() {  // Returns the quantity of the item
        return quantity;
    }

    public void setQuantity(int quantity) { // Sets the quantity of the item
        this.quantity = quantity;
    }

    public double getSizeInCubicFt() { // Returns the size in cubic feet of the item
        return sizeInCubicFt;
    }

    public void setSizeInCubicFt(double sizeInCubicFt) { // Sets the size in cubic feet of the item
        this.sizeInCubicFt = sizeInCubicFt;
    }

    public Warehouse getWarehouse() { // Returns the warehouse the item belongs to
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) { // Sets the warehouse the item belongs to
        this.warehouse = warehouse;
    }
}
