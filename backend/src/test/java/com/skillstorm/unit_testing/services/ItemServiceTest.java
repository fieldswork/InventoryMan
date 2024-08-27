package com.skillstorm.unit_testing.services;

import java.util.List;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.base.Optional;
import com.skillstorm.inventoryman.controllers.ItemController;
import com.skillstorm.inventoryman.models.Item;
import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.repositories.ItemRepository;
import com.skillstorm.inventoryman.services.ItemService;


public class ItemServiceTest {
    
    @Mock
    private ItemRepository itRepo;

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

    // @Test
    // public void getItemByIdTest() {
    //     long itemId = 1;
    //     Item expectedItem = new Item();
    //     when(itRepo.findById(itemId)).thenReturn(Optional.of(expectedItem));

    //     Item response = itService.getItemById(itemId);

    //     Assert.assertEquals(response, expectedItem);
    // }

    @Test
    public void saveItemTest() {
        Item inputItem = new Item();
        Item savedItem = new Item();

        System.out.println(inputItem.getName());
        System.out.println(savedItem.getName());

        when(itRepo.save(inputItem)).thenReturn(savedItem);

        Item response = itService.saveItem(inputItem);

        Assert.assertEquals(response, savedItem);
    }

    @Test
    public void updateItemTest() {
        Item inputItem = new Item();
        Item updatedItem = new Item();

        when(itRepo.save(inputItem)).thenReturn(updatedItem);

        Item response = itService.updateItem(inputItem.getId(), inputItem);

        Assert.assertEquals(response, updatedItem);
    }

    @Test
    public void deleteItemTest() {
        long itemId = 1;

        doNothing().when(itRepo).deleteById(itemId);

        verify(itRepo, times(1)).deleteById(itemId);
    }

    @AfterTest
    public void teardown() throws Exception{
        closeable.close();
    }
}
