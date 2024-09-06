package com.skillstorm.cucumber.CRUDTests;

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
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Adding a warehouse step definitions
 */
public class AddWarehouseSteps {

    private WebDriver driver;               // driver
    private AddWarehousePage awhPage;       // page object

    @Before("@addWarehouse")
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
        this.awhPage = new AddWarehousePage(driver);    // initializing our page object
    }

    /**
     * Loads add Warehouses page
     */
    @Given("I have navigated to the Add Warehouse page")
    public void navigateToAddWarehousePage() {
        System.out.println("Step: I have navigated to the Add Warehouse page");
        this.awhPage.get(); 
    }

    /**
     * Enters the warehouse name
     * @param warehouseName
     */
    @When("I input the warehouse name {string}")
    public void inputWarehouseName(String warehouseName) {
        System.out.println("Step: I input the warehouse name " + warehouseName);
        this.awhPage.enterWarehouseName(warehouseName);
    }

    /**
     * Enter the warehouse capacity
     * @param warehouseCapacity
     */
    @And("I provide the warehouse capacity {string}")
    public void provideWarehouseCapacity(String warehouseCapacity) {
        System.out.println("Step: I provide the warehouse capacity " + warehouseCapacity);
        this.awhPage.enterWarehouseCapacity(warehouseCapacity);
    }

    /**
     * Click the Create button after entering all the information of the warehouse
     */
    @And("I press the Create button to submit the warehouse form")
    public void pressSubmitWarehouseButton() {
        System.out.println("Step: I press the Create button to submit the warehouse form");
        this.awhPage.clickSubmitWarehouseButton();
    }

    /**
     * Checks if after creating a valid warehouse, we are redirected to the Warehouses page
     */
    @Then("I should be taken to the Warehouses overview page if the warehouse details are valid")
    public void takenToWarehousesOverviewPage() {
        System.out.println("Step: I should be taken to the Warehouses overview page");
        assertTrue(awhPage.amIOnWarehousesPage());
    }

    /**
     * Checks if the new warehouse is displayed on the Warehouses page
     * @param warehouseName
     */
    @And("I should see the new warehouse {string} listed on the Warehouses page")
    public void verifyWarehouseInWarehousesPage(String warehouseName) {
        System.out.println("Step: I should see the new warehouse " + warehouseName + " listed on the Warehouses page");
        assertTrue(awhPage.isWarehouseInWarehousesPage(warehouseName));
    }

    /**
     * Checks if the capacity for the new warehouse is also displayed on the Warehouse page
     * @param warehouseCapacity
     */
    @And("I should find the capacity {string} shown on the Warehouses page")
    public void verifyCapacityInWarehousesPage(String warehouseCapacity) {
        System.out.println("Step: I should find the capacity " + warehouseCapacity + " shown on the Warehouses page");
        assertTrue(awhPage.isCapacityInWarehousesPage(warehouseCapacity));
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@addWarehouse")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
