package com.skillstorm.unit_testing.services;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.skillstorm.inventoryman.models.Item;
import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.repositories.ItemRepository;
import com.skillstorm.inventoryman.repositories.WarehouseRepository;
import com.skillstorm.inventoryman.services.ItemService;


/**
 * Mockito tests for ItemService
 */
public class ItemServiceTest {
    
    @Mock
    private ItemRepository itRepo;      // the mock object

    @Mock
    private WarehouseRepository whRepo; // the mock object

    @InjectMocks
    private ItemService itService;      // the tested class which the mock object will be injected to
    private AutoCloseable closeable;    // used to manage mock objects (open and close them)

    /**
     * Opening all mock objects
     */
    @BeforeTest
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for getAllItems method
     */
    @Test
    public void getAllItemsTest() {
        
        List<Item> expectedItems = Arrays.asList(new Item(), new Item());
        when(itRepo.findAll()).thenReturn(expectedItems);   //use the mock object to get the expected list of all items

        List<Item> response = itService.getAllItems();      // get all items using the tested class
        Assert.assertEquals(response, expectedItems);       // checks if the response and the expected list of items are the same
    }

    /**
     * Test for getItemById method
     */
    @Test
    public void getItemByIdTest() {
        long itemId = 1;
        Item expectedItem = new Item();

        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(expectedItem));

        Item response = itService.getItemById(itemId);
        Assert.assertEquals(response, expectedItem);
    }

    /**
     * Test for saveItem
     */
    @Test
    public void saveItemTest() {     
        long itemId = 1;
        long warehouseId = 1;

        Item inputItem1 = new Item();
        Item inputItem2 = new Item();
        Item inputItem3 = new Item();

        Item savedItem = new Item();

        Warehouse wh1 = new Warehouse();
        Warehouse wh2 = new Warehouse();

        wh1.setId(warehouseId);
        wh1.setCapacity(2000);

        inputItem1.setId(1L);
        inputItem1.setName("shirt");
        inputItem1.setDescription("Top clothes");
        inputItem1.setQuantity(20);
        inputItem1.setSizeInCubicFt(20);
        inputItem1.setWarehouse(wh1);

        inputItem2.setWarehouse(wh2);

        inputItem3.setId(3L);
        inputItem3.setName("shirt");
        inputItem3.setDescription("Top clothes");
        inputItem3.setQuantity(100);
        inputItem3.setSizeInCubicFt(20);
        inputItem3.setWarehouse(wh1);

        savedItem.setId(1L);
        savedItem.setName("shirt");
        savedItem.setDescription("Top clothes");
        savedItem.setQuantity(20);
        savedItem.setSizeInCubicFt(20);
        savedItem.setWarehouse(wh1);

        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(inputItem1));
        when(whRepo.findById(warehouseId)).thenReturn(Optional.ofNullable(wh1));
        when(itRepo.save(inputItem1)).thenReturn(savedItem);
        when(itRepo.save(inputItem2)).thenThrow(IllegalArgumentException.class);
        when(itRepo.save(inputItem3)).thenThrow(IllegalArgumentException.class);

        Item response = itService.saveItem(inputItem1);

        // Testing if an item with all valid values for its properties can be saved without any problem
        Assert.assertEquals(response, savedItem);

        // Testing if an item to be saved with null value for the warehouse can throw an IllegalArgumentException 
        Assert.assertThrows(IllegalArgumentException.class, () -> itService.saveItem(inputItem2));

        //Testing if an item to be saved with total size would exceed the warehouse capacity and throw an IllegalArgumentException 
        Assert.assertThrows(IllegalArgumentException.class, () -> itService.saveItem(inputItem3));    
    }

    /**
     * Testing if an item with all valid new values for its properties can be updated without any problem
     */
    @Test
    public void updateItemTest1() {
        long itemId = 1;
        long warehouseId = 1;

        Item inputItem = new Item();
        Item savedItem = new Item();

        Item inputItem2 = null;
        Item savedItem2 = null;

        Item savedItem3 = new Item();

        Warehouse wh1 = new Warehouse();
        Warehouse wh2 = new Warehouse();

        wh1.setId(warehouseId);
        wh1.setCapacity(2000);
        wh1.setUsedCapacity(100);

        inputItem.setId(1L);
        inputItem.setName("shirt");
        inputItem.setDescription("Top clothes");
        inputItem.setQuantity(20);
        inputItem.setSizeInCubicFt(20);
        inputItem.setWarehouse(wh1);

        savedItem.setId(1L);
        savedItem.setName("shirt");
        savedItem.setDescription("Top clothes");
        savedItem.setQuantity(20);
        savedItem.setSizeInCubicFt(20);
        savedItem.setWarehouse(wh1);

        savedItem3.setId(1L);
        savedItem3.setName("short");
        savedItem3.setDescription("Top clothes");
        savedItem3.setQuantity(20);
        savedItem3.setSizeInCubicFt(20);
        savedItem3.setWarehouse(wh2);

        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(inputItem));
        when(whRepo.findById(1L)).thenReturn(Optional.ofNullable(wh1));
        when(whRepo.findById(2L)).thenReturn(Optional.ofNullable(wh2));
        when(itRepo.save(inputItem)).thenReturn(savedItem);

        // Testing if an item with all valid values for its properties can be updated without any problem
        Item response = itService.updateItem(itemId, inputItem);
        Assert.assertEquals(response, savedItem);

        // Testing if an item to be updated with null value for the warehouse can throw an IllegalArgumentException
        savedItem.setWarehouse(wh2);
 
        when(itRepo.save(inputItem)).thenThrow(IllegalArgumentException.class);
        Assert.assertThrows(IllegalArgumentException.class, () -> itService.updateItem(itemId, inputItem));

        // Testing the branch when Item is null
        when(itRepo.save(inputItem2)).thenReturn(savedItem2);
        Assert.assertThrows(NullPointerException.class, () -> itService.updateItem(2L, inputItem2));

        // Testing if new item size exceeds warehouse capacity
        Assert.assertThrows(IllegalArgumentException.class, () -> itService.updateItem(savedItem3.getId(), savedItem3));
    }

    /**
     * Testing if an item can be deleted
     */
    @Test
    public void deleteItemTest() {
        long itemId = 1;
        long warehouseId = 1;

        Item deletedItem = new Item();
        Warehouse wh1 = new Warehouse();
        Warehouse wh2 = new Warehouse();

        Item deletedItem3 = new Item();

        wh1.setId(warehouseId);
        wh1.setCapacity(2000);

        deletedItem.setId(itemId);
        deletedItem.setName("shirt");
        deletedItem.setDescription("Top clothes");
        deletedItem.setQuantity(20);
        deletedItem.setSizeInCubicFt(20);
        deletedItem.setWarehouse(wh1);
        
        when(whRepo.findById(1L)).thenReturn(Optional.ofNullable(wh1));
        when(whRepo.findById(2L)).thenReturn(Optional.ofNullable(wh2));
        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(deletedItem));

        assertAll(() -> itService.deleteItem(deletedItem.getId()));

        // Tests when item is null
        when(itRepo.findById(3L)).thenReturn(Optional.ofNullable(deletedItem3));

        assertAll(() -> itService.deleteItem(deletedItem3.getId()));

        // Testing if an item with warehouse null can be deleted
        deletedItem.setWarehouse(wh2);        
        assertAll(() -> itService.deleteItem(deletedItem.getId()));
    }


    /**
     * Closes all mock objects
     * @throws Exception
     */
    @AfterTest
    public void teardown() throws Exception{
        closeable.close();
    }
}
