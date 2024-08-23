package com.skillstorm.cucumber.ValidationTests;

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


public class SortWarehousesSteps {
    
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

    @When("I select {string} in the Sort By dropdown to sort Warehouses")
    public void selectSortingOption(String sortingOrder) {
        System.out.println("Step: I select to sort warehouse by " + sortingOrder);
        this.whPage.selectSortingOption(sortingOrder);
    }

     @Then("Warehouses should be displayed in the page by {string} order")
    public void isWarehousesOrdered(String sortingOrder) {
        System.out.println("Step: Warehouse should be order by " + sortingOrder);
        assertTrue(whPage.iswarehousesOrdered(sortingOrder));
    }

    @After("@sortWarehouses")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
