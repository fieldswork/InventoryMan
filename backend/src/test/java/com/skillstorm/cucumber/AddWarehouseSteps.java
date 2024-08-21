package com.skillstorm.cucumber;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.AddWarehousePage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

public class AddWarehouseSteps {
    private WebDriver driver;
    private AddWarehousePage awhPage;

    @Before("@addWarehouse")
    public void before() {
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.awhPage = new AddWarehousePage(driver);
    }

    @Given("I am on the Add Warehouse page")
    public void loadWebsite() {
        System.out.println("Step: I am on the Warehouses page");
        this.awhPage.get(); // Loads add warehouse page
    }

    @When("I enter a {string} in the warehouse name field")
    public void selectSortingOption(String warehouseName) {
        System.out.println("Step: I enter" + warehouseName + " in the warehouse name field");
        //this.awhPage.enterWarehouseName(warehouseName);
    }

    @And("I enter a {string} in the warehouse capacity field")
    public void enterWarehouseCapacity(String warehouseCapacity) {
        System.out.println("Step: I enter" + warehouseCapacity + " in the warehouse capacity field");
        //this.awhPage.enterWarehouseCapacity(warehouseCapacity);
    }

    @Then("I should be redirected to the Warehouses page")
    public void isOnWarehousesPage() {
        System.out.println("Step: I should be redirected to the Warehouses page");
        //assertTrue(whPage.amIOnWarehousesPage());
    }

    @And("I should see the warehouse {string} in the Warehouses page")
    public void isWarehouseInWarehousesPage(String warehouseName) {
        System.out.println("Step: I should see the warehouse" + warehouseName + " in the Warehouses page");
        //assertTrue(whPage.isWarehouseInWarehousesPage(warehouseName));
    }

    @And("I should see the capacity {string} in the Warehouses page")
    public void isCapacityInWarehousesPage(String warehouseCapacity) {
        System.out.println("Step: I should see the capacity" + warehouseCapacity + " in the Warehouses page");
        //assertTrue(whPage.isCapacityInWarehousesPage(warehouseCapacity));
    }

    @After("@addWarehouse")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
