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

import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.repositories.WarehouseRepository;
import com.skillstorm.inventoryman.services.WarehouseService;

/**
 * Mockito tests for WarehouseService
 */
public class WarehouseServiceTest {
    
    @Mock
    private WarehouseRepository whRepository;   // the mock object

    @InjectMocks
    private WarehouseService whService;     // the tested class which the mock object will be injected to
    private AutoCloseable closeable;        // used to manage mock objects (open and close them)

    /**
     * Opening all mock objects
     */
    @BeforeTest
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for getAllWarehouses method
     */
    @Test
    public void getAllWarehousesTest() {
        
        List<Warehouse> expectedWH = Arrays.asList(new Warehouse(), new Warehouse());

        when(whRepository.findAll()).thenReturn(expectedWH);

        List<Warehouse> response = whService.getAllWarehouses();

        Assert.assertEquals(response, expectedWH);
    }

    /**
     * Test for getWarehouseById method
     */
    @Test
    public void getWarehouseByIdTest() {
        long warehouseId = 1;
        Warehouse expectedWH = new Warehouse();
        when(whRepository.findById(warehouseId)).thenReturn(Optional.ofNullable(expectedWH));

        Warehouse response = whService.getWarehouseById(warehouseId);

        Assert.assertEquals(response, expectedWH);
    }

    /**
     * Test for saveWarehouse method
     */
    @Test
    public void saveWarehouseTest() {
        Warehouse inputWH = new Warehouse();
        Warehouse savedWH = new Warehouse();

        when(whRepository.save(inputWH)).thenReturn(savedWH);

        Warehouse response = whService.saveWarehouse(inputWH);

        Assert.assertEquals(response, savedWH);
    }

    /**
     * Test for deleteWarehouse method
     */
    @Test
    public void deleteWarehouseTest() {
        long warehouseId = 1;
        Warehouse expectedWH = new Warehouse();
        
        when(whRepository.findById(warehouseId)).thenReturn(Optional.ofNullable(expectedWH));

        assertAll(() -> whService.deleteWarehouse(warehouseId));
    }

    /**
     * Test for getCurrentSpaceUsed method
     */
    @Test
    public void getCurrentSpaceUsedTest() {
        long warehouseId = 1;
        Warehouse expectedWH = new Warehouse();
        
        when(whRepository.findById(warehouseId)).thenReturn(Optional.ofNullable(expectedWH));

        double response1 = whService.getCurrentSpaceUsed(warehouseId);
        double response2 = whService.getCurrentSpaceUsed(0L);

        Assert.assertEquals(response1, expectedWH.getUsedCapacity());   // Tests if the response and expected values for the current space used of an existing warhouse are the same
        Assert.assertEquals(response2, 0.0);                   // Tests if the response and expected values for the current space used of a warhouse that does not exist (null) are the same

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
