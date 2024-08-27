package com.skillstorm.unit_testing.models;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.skillstorm.inventoryman.models.Item;
import com.skillstorm.inventoryman.models.Warehouse;

/**
 * Unit testing for the inventoryman Item model with TestNG
 */
public class ItemTest {

    private Item item;
    private Warehouse warehouse;

    @BeforeTest
    public void init() {
        System.out.println("Before Testing: Initializing Item");

        item = new Item();
        warehouse = new Warehouse();
        item.setId(1L);
        item.setName("shirt");
        item.setDescription("Top clothes");
        item.setQuantity(20);
        item.setSizeInCubicFt(20);
        item.setWarehouse(warehouse);
    }

    @Test
    public void testItemGetters() {
        Assert.assertEquals(1L, item.getId());
        Assert.assertEquals("shirt", item.getName());
        Assert.assertEquals("Top clothes", item.getDescription());
        Assert.assertEquals(20, item.getQuantity());
        Assert.assertEquals(20, item.getSizeInCubicFt());
        Assert.assertEquals(warehouse, item.getWarehouse());
    }

    @Test
    public void testItemSetters() {
        Warehouse wh = new Warehouse();
        item.setId(2L);
        item.setName("dress");
        item.setDescription("Long clothes");
        item.setQuantity(10);
        item.setSizeInCubicFt(10);
        item.setWarehouse(wh);

        Assert.assertEquals(2L, item.getId());
        Assert.assertEquals("dress", item.getName());
        Assert.assertEquals("Long clothes", item.getDescription());
        Assert.assertEquals(10, item.getQuantity());
        Assert.assertEquals(10, item.getSizeInCubicFt());
        Assert.assertEquals(wh, item.getWarehouse());
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Testing: Finished testing!");
    }
}
