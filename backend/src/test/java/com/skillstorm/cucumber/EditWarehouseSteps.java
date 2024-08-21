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
import io.cucumber.java.en.And;

public class EditWarehouseSteps {
    private WebDriver driver;
    private WarehousesPage whPage;

    @Before("@editWarehouse")
    public void before() {
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.whPage = new WarehousesPage(driver);
    }

    @Given("I am on the Warehouses page")
    public void loadWebsite() {
        System.out.println("Step: I am on the Warehouses page");
        this.whPage.get();
    }

    @When("I find the {string} warehouse in the Warehouses page")
    public void findWarehouse(String warehouseName) {
        System.out.println("Step: I find the " + warehouseName + " warehouse in the Warehouses page");
        this.whPage.findWarehouse(warehouseName);
    }

    @And("I click the Edit button to edit the warehouse")
    public void clickEditButton() {
        System.out.println("Step: I click the Edit button to edit the warehouse");
        this.whPage.clickEditButton();
    }

    @Then("I should be redirected to the Edit Warehouse page")
    public void isOnEditWarehousePage() {
        System.out.println("Step: I should be redirected to the Edit Warehouse page");
        assertTrue(whPage.amIOnEditWarehousePage());
    }

    @When("I enter {string} in the warehouse name field")
    public void enterWarehouseName(String warehouseName) {
        System.out.println("Step: I enter " + warehouseName + " in the warehouse name field");
        this.whPage.enterWarehouseName(warehouseName);
    }

    @And("I enter {string} in the warehouse capacity field")
    public void enterWarehouseCapacity(String warehouseCapacity) {
        System.out.println("Step: I enter " + warehouseCapacity + " in the warehouse capacity field");
        this.whPage.enterWarehouseCapacity(warehouseCapacity);
    }

    @And("I click the Update button to submit the warehouse")
    public void clickSubmitWarehouseButton() {
        System.out.println("Step: I click the Update button to submit the warehouse");
        this.whPage.clickSubmitWarehouseButton();
    }

    @Then("I should be redirected to the Warehouses page if the edited warehouse is valid")
    public void isOnWarehousesPageAfterEdit() {
        System.out.println("Step: I should be redirected to the Warehouses page");
        assertTrue(whPage.amIOnWarehousesPage());
    }

    @And("I should see the edited warehouse {string} in the Warehouses page")
    public void checkEditedWarehouse(String warehouseName) {
        System.out.println("Step: I should see the warehouse " + warehouseName + " in the Warehouses page");
        assertTrue(whPage.findWarehouse(warehouseName));
    }

    @After("@editWarehouse")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
