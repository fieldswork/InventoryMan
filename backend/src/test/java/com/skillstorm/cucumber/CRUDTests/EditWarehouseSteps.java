package com.skillstorm.cucumber.CRUDTests;

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
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Updating a warehouse step definitions
 */
public class EditWarehouseSteps {

    private WebDriver driver;           // driver
    private WarehousesPage whPage;      // page object

    @Before("@editWarehouse")
    public void before() {
        ChromeOptions options = new ChromeOptions();

        /**
        * Checks if we are running these tests on Jenkins
        * If so, these tests will be run in a HEADLESS mode
        * If not, these tests will run as usual with browser popping, etc
        */
        if ("true".equals(System.getenv("HEADLESS"))) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-debugging-port=9222");
        }

        WebDriverManager.chromedriver().setup();        //setting up the chrome driver

        this.driver = new ChromeDriver(options);        // assigning our driver to be of a type Chrome
        this.whPage = new WarehousesPage(driver);       // initializing our page object
    }

    /**
     * Loads add Warehouses page
     */
    @Given("I am on the Warehouses page")
    public void loadWebsite() {
        System.out.println("Step: I am on the Warehouses page");
        this.whPage.get();
    }

    /**
     * Finds the warehouse to be updated
     * @param warehouseName
     */
    @When("I find the {string} warehouse in the Warehouses page")
    public void findWarehouse(String warehouseName) {
        System.out.println("Step: I find the " + warehouseName + " warehouse in the Warehouses page");
        this.whPage.findWarehouse(warehouseName);
    }

    /**
     * Click the Edit button
     */
    @And("I click the Edit button to edit the warehouse")
    public void clickEditButton() {
        System.out.println("Step: I click the Edit button to edit the warehouse");
        this.whPage.clickEditButton();
    }

    /**
     * Load the Edit Warehouse page
     */
    @Then("I should be redirected to the Edit Warehouse page")
    public void isOnEditWarehousePage() {
        System.out.println("Step: I should be redirected to the Edit Warehouse page");
        assertTrue(whPage.amIOnEditWarehousePage());
    }

    /**
     * Enter new warehouse name
     * @param warehouseName
     */
    @When("I enter {string} in the warehouse name field")
    public void enterWarehouseName(String warehouseName) {
        System.out.println("Step: I enter " + warehouseName + " in the warehouse name field");
        this.whPage.enterWarehouseName(warehouseName);
    }

    /**
     * Enter new warehouse capacity
     * @param warehouseCapacity
     */
    @And("I enter {string} in the warehouse capacity field")
    public void enterWarehouseCapacity(String warehouseCapacity) {
        System.out.println("Step: I enter " + warehouseCapacity + " in the warehouse capacity field");
        this.whPage.enterWarehouseCapacity(warehouseCapacity);
    }

    /**
     * Click the Update button
     */
    @And("I click the Update button to submit the warehouse")
    public void clickSubmitWarehouseButton() {
        System.out.println("Step: I click the Update button to submit the warehouse");
        this.whPage.clickSubmitWarehouseButton();
    }

    /**
     * If all the warehouse's information are valid, redirect me to the Warehouses page
     */
    @Then("I should be redirected to the Warehouses page if the edited warehouse is valid")
    public void isOnWarehousesPageAfterEdit() {
        System.out.println("Step: I should be redirected to the Warehouses page");
        assertTrue(whPage.amIOnWarehousesPage());
    }

    /**
     * Check if warehouse's name got changed
     * @param warehouseName
     */
    @And("I should see the edited warehouse {string} in the Warehouses page")
    public void checkEditedWarehouse(String warehouseName) {
        System.out.println("Step: I should see the warehouse " + warehouseName + " in the Warehouses page");
        assertTrue(whPage.findWarehouse(warehouseName));
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@editWarehouse")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
