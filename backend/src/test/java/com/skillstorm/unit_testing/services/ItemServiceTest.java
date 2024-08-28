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

    @Test
    public void saveItemTest() {     
        // Warehouse wh = new Warehouse();  
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

        System.out.println(inputItem.getId());
        System.out.println(savedItem.getId());
        System.out.println(inputItem.getWarehouse());

        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(inputItem));
        when(whRepo.findById(warehouseId)).thenReturn(Optional.ofNullable(wh));
        when(itRepo.save(inputItem)).thenReturn(savedItem);

        Item response = itService.saveItem(inputItem);

        Assert.assertEquals(response, savedItem);
    }

    @Test
    public void updateItemTest() {
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
    public void deleteItemTest() {
        long itemId = 1;
        Item deletedItem = new Item();
        
        when(itRepo.findById(itemId)).thenReturn(Optional.ofNullable(deletedItem));

        assertAll(() -> itService.deleteItem(deletedItem.getId()));
    }

    @AfterTest
    public void teardown() throws Exception{
        closeable.close();
    }
}
