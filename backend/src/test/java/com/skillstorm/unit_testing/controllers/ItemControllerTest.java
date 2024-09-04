package com.skillstorm.unit_testing.controllers;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
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

import com.skillstorm.inventoryman.controllers.ItemController;
import com.skillstorm.inventoryman.models.Item;
import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.services.ItemService;

/**
 * Mockito tests for ItemController
 */
public class ItemControllerTest {
    
    @Mock
    private ItemService itService; // the mock object

    @InjectMocks
    private ItemController itController; // the tested class which the mock object will be injected to
    private AutoCloseable closeable;     // used to manage mock objects (open and close them)

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
        when(itService.getAllItems()).thenReturn(expectedItems);    //use the mock object to get the expected list of all items

        List<Item> response = itController.getAllItems();   // get all items using the tested class
        Assert.assertEquals(response, expectedItems);       // checks if the response and the expected list of items are the same
    }

    /**
     * Test for getItemById method
     */
    @Test
    public void getItemByIdTest() {
        long itemId = 1;
        Item expectedItem = new Item();
        
        when(itService.getItemById(itemId)).thenReturn(expectedItem);   //use the mock object to get the expected item by its ID

        Item response = itController.getItemById(itemId);   // get item by its ID using the tested class
        Assert.assertEquals(response, expectedItem);        // checks if the response and the expected items retrieved by itemId are the same
    }

    /**
     * Test for createItem method
     */
    @Test
    public void createItemTest() {
        long inputId = 1;

        Item inputItem1 = new Item();
        Item inputItem2 = new Item();

        Item savedItem = new Item();
        Warehouse wh = new Warehouse(); //wh is initialized with null value

        inputItem2.setId(inputId);
        inputItem2.setName("shirt");
        inputItem2.setDescription("Top clothes");
        inputItem2.setQuantity(20);
        inputItem2.setSizeInCubicFt(20);
        inputItem2.setWarehouse(wh);

        when(itService.saveItem(inputItem1)).thenReturn(savedItem);     //use the mock object to create an item
        when(itService.saveItem(inputItem2)).thenThrow(IllegalArgumentException.class);     //use the mock object to create an item without a defined warehouse

        ResponseEntity<Item> response = itController.createItem(inputItem1);    // create the item using the tested class

        Assert.assertEquals(response.getBody(), savedItem);             //checks if the response and expected Item are the same
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);   //if the response and expected Item are the same the OK HTTP status code should be return for both of them
        Assert.assertEquals(itController.createItem(inputItem2).getStatusCode(), HttpStatus.BAD_REQUEST);   //when an Item with null value for Warehouse is created, a BAD REQUEST HTTP status code should be returned

    }

    /**
     * Test for updateItem method
     */
    @Test
    public void updateItemTes() {
        long inputId = 1;
       
        Item inputItem1 = new Item();
        Item inputItem2 = new Item();

        Item updatedItem = new Item();
        Warehouse wh = new Warehouse(); //wh is initialized with null value

        inputItem2.setId(inputId);
        inputItem2.setName("shirt");
        inputItem2.setDescription("Top clothes");
        inputItem2.setQuantity(10);
        inputItem2.setSizeInCubicFt(10);
        inputItem2.setWarehouse(wh);

        when(itService.updateItem(inputId, inputItem1)).thenReturn(updatedItem);    //use the mock object to update an item
        when(itService.updateItem(inputId, inputItem2)).thenThrow(IllegalArgumentException.class);  //use the mock object to update an item without a defined warehouse

        ResponseEntity<Item> response = itController.updateItem(inputId, inputItem1);   // update the item using the tested class

        Assert.assertEquals(response.getBody(), updatedItem);           //checks if the response and expected Item are the same
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);   //if the response and expected Item are the same the OK HTTP status code should be return for both of them
        Assert.assertEquals(itController.updateItem(inputId, inputItem2).getStatusCode(), HttpStatus.BAD_REQUEST);  //when an Item with null value for Warehouse is updated, a BAD REQUEST HTTP status code should be returned
    }

    /**
     * Test for deleteItem
     */
    @Test
    public void deleteItemTest() {
        long itemId = 1;

        Item deletedItem = new Item();
        Warehouse wh = new Warehouse(); //wh is initialized with null value

        deletedItem.setId(itemId);
        deletedItem.setName("shirt");
        deletedItem.setDescription("Top clothes");
        deletedItem.setQuantity(20);
        deletedItem.setSizeInCubicFt(20);
        deletedItem.setWarehouse(wh);

        when(itService.getItemById(itemId)).thenReturn(deletedItem);    // use the mock object to find the item by its ID

        assertAll(() -> itController.deleteItem(itemId));               // delete the item using the tested class
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
