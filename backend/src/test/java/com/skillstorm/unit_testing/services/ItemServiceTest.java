package com.skillstorm.unit_testing.services;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
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


public class ItemServiceTest {
    
    @Mock
    private ItemRepository itRepo;

    @Mock
    private WarehouseRepository whRepo;

    @InjectMocks
    private ItemService itService;
    private AutoCloseable closeable;

    @BeforeTest
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllItemsTest() {
        
        List<Item> expectedItems = Arrays.asList(new Item(), new Item());
        when(itRepo.findAll()).thenReturn(expectedItems);

        List<Item> response = itService.getAllItems();
        Assert.assertEquals(response, expectedItems);
    }

    @Test
    public void getItemByIdTest() {
        long itemId = 1;
        Item expectedItem = new Item();

        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(expectedItem));

        Item response = itService.getItemById(itemId);
        Assert.assertEquals(response, expectedItem);
    }

    /**
     * Testing if an item with all valid values for its properties can be saved without any problem
     */
    @Test
    public void saveItemTest1() {     
        long itemId = 1;
        long warehouseId = 1;

        Item inputItem = new Item();
        Item savedItem = new Item();
        Warehouse wh = new Warehouse();

        wh.setId(warehouseId);
        wh.setCapacity(2000);

        inputItem.setId(1L);
        inputItem.setName("shirt");
        inputItem.setDescription("Top clothes");
        inputItem.setQuantity(20);
        inputItem.setSizeInCubicFt(20);
        inputItem.setWarehouse(wh);

        savedItem.setId(1L);
        savedItem.setName("shirt");
        savedItem.setDescription("Top clothes");
        savedItem.setQuantity(20);
        savedItem.setSizeInCubicFt(20);
        savedItem.setWarehouse(wh);

        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(inputItem));
        when(whRepo.findById(warehouseId)).thenReturn(Optional.ofNullable(wh));
        when(itRepo.save(inputItem)).thenReturn(savedItem);

        Item response = itService.saveItem(inputItem);

        Assert.assertEquals(response, savedItem);
    }

    /**
     * Testing if an item to saved with a null Warehouse can throw an IllegalArgumentException
     */
    @Test
    public void saveItemTest2() {

        Item inputItem = new Item();
        Warehouse wh = new Warehouse();

        inputItem.setId(1L);
        inputItem.setName("shirt");
        inputItem.setDescription("Top clothes");
        inputItem.setQuantity(20);
        inputItem.setSizeInCubicFt(20);
        inputItem.setWarehouse(wh);

        when(itRepo.save(inputItem)).thenThrow(IllegalArgumentException.class);
        
        assertThrows(IllegalArgumentException.class, () -> itService.saveItem(inputItem));
    }

    /**
     * Testing if an item to be saved with total size would exceed the warehouse capacity and throw an IllegalArgumentException 
     */
    @Test
    public void saveItemTest3() {     
        long itemId = 1;
        long warehouseId = 1;

        Item inputItem = new Item();
        Item savedItem = new Item();
        Warehouse wh = new Warehouse();

        wh.setId(warehouseId);
        wh.setCapacity(200);

        inputItem.setId(1L);
        inputItem.setName("shirt");
        inputItem.setDescription("Top clothes");
        inputItem.setQuantity(20);
        inputItem.setSizeInCubicFt(20);
        inputItem.setWarehouse(wh);

        savedItem.setId(1L);
        savedItem.setName("shirt");
        savedItem.setDescription("Top clothes");
        savedItem.setQuantity(20);
        savedItem.setSizeInCubicFt(20);
        savedItem.setWarehouse(wh);

        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(inputItem));
        when(whRepo.findById(warehouseId)).thenReturn(Optional.ofNullable(wh));
        when(itRepo.save(inputItem)).thenThrow(IllegalArgumentException.class);
        
        assertThrows(IllegalArgumentException.class, () -> itService.saveItem(inputItem));
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
        Warehouse wh = new Warehouse();

        wh.setId(warehouseId);
        wh.setCapacity(2000);

        inputItem.setId(1L);
        inputItem.setName("shirt");
        inputItem.setDescription("Top clothes");
        inputItem.setQuantity(20);
        inputItem.setSizeInCubicFt(20);
        inputItem.setWarehouse(wh);

        savedItem.setId(1L);
        savedItem.setName("shirt");
        savedItem.setDescription("Top clothes");
        savedItem.setQuantity(20);
        savedItem.setSizeInCubicFt(20);
        savedItem.setWarehouse(wh);

        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(inputItem));
        when(whRepo.findById(warehouseId)).thenReturn(Optional.ofNullable(wh));
        when(itRepo.save(inputItem)).thenReturn(savedItem);

        Item response = itService.updateItem(itemId, inputItem);

        Assert.assertEquals(response, savedItem);
    }

    @Test
    public void updateItemTest2() {
        long itemId = 1;
        long warehouseId = 1;

        Item inputItem = new Item();
        Item savedItem = new Item();
        Warehouse wh = new Warehouse();

        wh.setId(warehouseId);
        wh.setCapacity(100);
        wh.setUsedCapacity(100);

        inputItem.setId(1L);
        inputItem.setName("shirt");
        inputItem.setDescription("Top clothes");
        inputItem.setQuantity(20);
        inputItem.setSizeInCubicFt(20);
        inputItem.setWarehouse(wh);

        savedItem.setId(1L);
        savedItem.setName("shirt");
        savedItem.setDescription("Top clothes");
        savedItem.setQuantity(20);
        savedItem.setSizeInCubicFt(20);
        savedItem.setWarehouse(wh);

        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(inputItem));
        when(whRepo.findById(warehouseId)).thenReturn(Optional.ofNullable(wh));
        when(itRepo.save(inputItem)).thenThrow(IllegalArgumentException.class);
        
        assertThrows(IllegalArgumentException.class, () -> itService.updateItem(itemId, inputItem));
    }

    /**
     * Testing the branch that checks if warehouse is null when updating an item
     */
    @Test
    public void updateItemTest3() {
        long itemId = 1;

        Item inputItem = new Item();
        Item savedItem = new Item();
        Warehouse wh = new Warehouse();

        inputItem.setId(1L);
        inputItem.setName("shirt");
        inputItem.setDescription("Top clothes");
        inputItem.setQuantity(20);
        inputItem.setSizeInCubicFt(20);
        inputItem.setWarehouse(wh);

        savedItem.setId(1L);
        savedItem.setName("shirt");
        savedItem.setDescription("Top clothes");
        savedItem.setQuantity(20);
        savedItem.setSizeInCubicFt(20);
        savedItem.setWarehouse(wh);

        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(inputItem));
        when(itRepo.save(inputItem)).thenThrow(IllegalArgumentException.class);
        
        assertThrows(IllegalArgumentException.class, () -> itService.updateItem(itemId, inputItem));
    }

    @Test
    public void updateItemTest4() {
        long itemId = 1;

        Item inputItem = null;
        Item savedItem = null;


        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(inputItem));
        when(itRepo.save(inputItem)).thenReturn(savedItem);
  
        assertThrows(NullPointerException.class, () -> itService.updateItem(itemId, inputItem));

    }
    

    /**
     * Testing if an item can be deleted
     */
    @Test
    public void deleteItemTest1() {
        long itemId = 1;
        long warehouseId = 1;

        Item deletedItem = new Item();
        Warehouse wh = new Warehouse();

        wh.setId(warehouseId);
        wh.setCapacity(2000);

        deletedItem.setId(itemId);
        deletedItem.setName("shirt");
        deletedItem.setDescription("Top clothes");
        deletedItem.setQuantity(20);
        deletedItem.setSizeInCubicFt(20);
        deletedItem.setWarehouse(wh);
        
        when(whRepo.findById(warehouseId)).thenReturn(Optional.ofNullable(wh));
        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(deletedItem));

        assertAll(() -> itService.deleteItem(deletedItem.getId()));
    }

    /**
     * Testing if an item can be deleted
     */    
    @Test
    public void deleteItemTest2() {

        Item deletedItem = new Item();
        Warehouse wh = new Warehouse();

        deletedItem.setId(1L);
        deletedItem.setName("shirt");
        deletedItem.setDescription("Top clothes");
        deletedItem.setQuantity(20);
        deletedItem.setSizeInCubicFt(20);
        deletedItem.setWarehouse(wh);

        when(itRepo.findById(0L)).thenReturn(Optional.ofNullable(deletedItem));

        assertAll(() -> itService.deleteItem(deletedItem.getId()));
    }

    @Test
    public void deleteItemTest3() {

        Item deletedItem = new Item();

        when(itRepo.findById(1L)).thenReturn(Optional.ofNullable(deletedItem));

        assertAll(() -> itService.deleteItem(deletedItem.getId()));
    }

    @AfterTest
    public void teardown() throws Exception{
        closeable.close();
    }
}
