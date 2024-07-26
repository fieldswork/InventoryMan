package com.skillstorm.inventoryman.model;

import jakarta.persistence.*;

@Entity // This is a persistent class 
@Table(name = "inventory") // This class is mapped to the items table
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto-increments the id
    private long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "weight_lbs")
    private double weightLbs;

    @Column(name = "size_cubic_ft")
    private double sizeCubicFt;

    @Column(name = "quantity")
    private int quantity;

    public Object getItemName() {
        return itemName;
    }

    public void setItemName(Object newItemName) {
        this.itemName = (String) newItemName;
    }

    public Object getWeightLbs() {
        return weightLbs;
    }

    public void setWeightLbs(Object newWeightLbs) {
        this.weightLbs = (double) newWeightLbs;
    }

    public Object getSizeCubicFt() {
        return sizeCubicFt;
    }

    public void setSizeCubicFt(Object newSizeCubicFt) {
        this.sizeCubicFt = (double) newSizeCubicFt;
    }

    public Object getQuantity() {
        return quantity;
    }

    public void setQuantity(Object newQuantity) {
        this.quantity = (int) newQuantity;
    }
}
