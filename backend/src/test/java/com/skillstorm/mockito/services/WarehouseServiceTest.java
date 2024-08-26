package com.skillstorm.mockito.services;

import java.util.List;
import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
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

import com.skillstorm.inventoryman.controllers.WarehouseController;
import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.repositories.WarehouseRepository;
import com.skillstorm.inventoryman.services.WarehouseService;


public class WarehouseServiceTest {
    
    @Mock
    private WarehouseRepository whRepository;

    @InjectMocks
    private WarehouseService whService;
    private AutoCloseable closeable;

    @BeforeTest
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllWarehousesTest() {
        
        List<Warehouse> expectedWH = Arrays.asList(new Warehouse(), new Warehouse());

        when(whRepository.findAll()).thenReturn(expectedWH);

        List<Warehouse> response = whService.getAllWarehouses();

        Assert.assertEquals(response, expectedWH);
    }

    // @Test
    // public void getWarehouseByIdTest() {
    //     long warehouseId = 1;
    //     Warehouse expectedWH = new Warehouse();
    //     when(whRepository.findById(warehouseId)).thenReturn(expectedWH);

    //     Warehouse response = whService.getWarehouseById(warehouseId);

    //     Assert.assertEquals(response, expectedWH);
    // }

    @Test
    public void saveWarehouseTest() {
        Warehouse inputWH = new Warehouse();
        Warehouse savedWH = new Warehouse();

        when(whRepository.save(inputWH)).thenReturn(savedWH);

        Warehouse response = whService.saveWarehouse(inputWH);

        Assert.assertEquals(response, savedWH);
    }

    @Test
    public void deleteWarehouseTest() {
        Warehouse inputWarehouse = new Warehouse();

        doNothing().when(whRepository).deleteById(inputWarehouse.getId());

        verify(whRepository, times(1)).deleteById(inputWarehouse.getId());
    }

    // @Test
    // public void getCurrentSpaceUsedTest() {
    //     Warehouse inputWarehouse = new Warehouse();
    //     Warehouse expectedWH = new Warehouse();

    //     when(inputWarehouse.getUsedCapacity()).thenReturn(expectedWH.getUsedCapacity());

    //     double response = whService.getCurrentSpaceUsed(inputWarehouse.getId());

    //     Assert.assertEquals(response, expectedWH.getUsedCapacity());
    // }

    @AfterTest
    public void teardown() throws Exception{
        closeable.close();
    }
}
