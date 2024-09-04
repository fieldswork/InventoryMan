package com.skillstorm.unit_testing.models;

import java.util.List;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.skillstorm.inventoryman.models.Item;
import com.skillstorm.inventoryman.models.Warehouse;

/**
 * Unit testing for the inventoryman Warehouse model with TestNG
 */
public class WarehouseTest {

    private Warehouse warehouse;
    private List<Item> items;

    /**
     * Declaring and initializing a Warehouse
     */
    @BeforeTest
    public void init() {
        System.out.println("Before Testing: Initializing Warehouse");

        warehouse = new Warehouse();
        items = new ArrayList<>();

        Item item1 = new Item();
        Item item2 = new Item();
        items.add(item1);
        items.add(item2);

        warehouse.setId(1L);
        warehouse.setName("testedWH");
        warehouse.setCapacity(2000);
        warehouse.setUsedCapacity(200);
        warehouse.setItems(items);
    }

    /**
     * Testing all the Warehouse getter methods
     */
    @Test
    public void testItemGetters() {
        Assert.assertEquals(1L, warehouse.getId());
        Assert.assertEquals("testedWH", warehouse.getName());
        Assert.assertEquals(2000, warehouse.getCapacity());
        Assert.assertEquals(200, warehouse.getUsedCapacity());
        Assert.assertEquals(items, warehouse.getItems());
    }

    /**
     * Testing all the Warehouse setter methods
     */
    @Test
    public void testItemSetters() {
        List<Item> items2 = new ArrayList<>();
        Item item3 = new Item();
        Item item4 = new Item();
        items2.add(item3);
        items2.add(item4);

        warehouse.setId(2L);
        warehouse.setName("testedWH2");
        warehouse.setCapacity(3000);
        warehouse.setUsedCapacity(300);
        warehouse.setItems(items2);

        Assert.assertEquals(2L, warehouse.getId());
        Assert.assertEquals("testedWH2", warehouse.getName());
        Assert.assertEquals(3000, warehouse.getCapacity());
        Assert.assertEquals(300, warehouse.getUsedCapacity());
        Assert.assertEquals(items2, warehouse.getItems());
    }

    /**
     * Prints out a msg when all tests for the Warehouse model has been done running
     */
    @AfterTest
    public void afterTest() {
        System.out.println("After Testing: Finished testing!");
    }
}
