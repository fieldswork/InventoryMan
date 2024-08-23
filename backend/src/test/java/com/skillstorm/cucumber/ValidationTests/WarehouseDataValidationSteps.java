package com.skillstorm.cucumber.ValidationTests;

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

public class WarehouseDataValidationSteps {
    private WebDriver driver;
    private AddWarehousePage awhPage;

    @Before("@addingWarehouseDataValidation")
    public void before() {
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.awhPage = new AddWarehousePage(driver);
    }

    @Given("I am on the Add Warehouse page")
    public void loadWebsite() {
        System.out.println("Step: I am on the Add Warehouses page");
        this.awhPage.get(); // Loads add warehouse page
    }

    @When("I enter a {string} in the warehouse name field")
    public void selectSortingOption(String warehouseName) {
        System.out.println("Step: I enter" + warehouseName + " in the warehouse name field");
        this.awhPage.enterWarehouseName(warehouseName);
    }

    @And("I enter a {string} in the warehouse capacity field")
    public void enterWarehouseCapacity(String warehouseCapacity) {
        System.out.println("Step: I enter " + warehouseCapacity + " in the warehouse capacity field");
        this.awhPage.enterWarehouseCapacity(warehouseCapacity);
    }

    @And("I click the Create button to submit the warehouse")
    public void clickSubmitWarehouseButton() {
        System.out.println("Step: I click the Create button to submit the warehouse");
        this.awhPage.clickSubmitWarehouseButton();
    }

    @Then("The {string} message should be displayed when entering invalid capacity")
    public void isInvalidDataAlertDisplayed(String message) {
        System.out.println("Step: I should see the alert message - " + message);
        assertTrue(awhPage.isInvalidDataAlertDisplayed(message));
    }

    @After("@addingWarehouseDataValidation")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
