package com.skillstorm.cucumber;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.WarehousesPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SortWarehousesByNameSteps {
    
    private WebDriver driver;
    private WarehousesPage whPage;

    @Before("@sortWarehouses")
    public void before() {
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.whPage = new WarehousesPage(driver);
    }

    @Given("I am in the Warehouses page")
    public void loadWebsite() {
        System.out.println("Step: I am on the Warehouses page");
        this.whPage.get();
    }

    @When("I select {string} in the Sort By dropdown")
    public void selectSortByName(String whOrder) {
        System.out.println("Step: I select to sort warehouse by " + whOrder);
        this.whPage.selectSortByName();
    }

     @Then("Warehouses should be displayed in the page by {string} order")
    public void isWarehousesOrdered(String whOrder) {
        System.out.println("Step: Warehouse should be order by" + whOrder);
        assertTrue(whPage.iswarehousesOrderedByName());
    }

    @After("@sortWarehouses")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
