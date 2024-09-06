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
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.en.And;

/**
 * Warehouse data validation step definitions
 */
public class WarehouseDataValidationSteps {

    private WebDriver driver;               // driver
    private AddWarehousePage awhPage;       // page object

    @Before("@addingWarehouseDataValidation")
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

        WebDriverManager.chromedriver().setup();            //setting up the chrome driver

        this.driver = new ChromeDriver(options);            // assigning our driver to be of a type Chrome
        this.awhPage = new AddWarehousePage(driver);        // initializing our page object
    }

    /**
     * Load the Add Warehouse page
     */
    @Given("I am on the Add Warehouse page")
    public void loadWebsite() {
        System.out.println("Step: I am on the Add Warehouses page");
        this.awhPage.get(); // Loads add warehouse page
    }

    /**
     * Enter the warehouse name
     * @param warehouseName
     */
    @When("I enter a {string} in the warehouse name field")
    public void selectSortingOption(String warehouseName) {
        System.out.println("Step: I enter" + warehouseName + " in the warehouse name field");
        this.awhPage.enterWarehouseName(warehouseName);
    }

    /**
     * Enter the warehouse capacity
     * @param warehouseCapacity
     */
    @And("I enter a {string} in the warehouse capacity field")
    public void enterWarehouseCapacity(String warehouseCapacity) {
        System.out.println("Step: I enter " + warehouseCapacity + " in the warehouse capacity field");
        this.awhPage.enterWarehouseCapacity(warehouseCapacity);
    }

    /**
     * Click the Create button
     */
    @And("I click the Create button to submit the warehouse")
    public void clickSubmitWarehouseButton() {
        System.out.println("Step: I click the Create button to submit the warehouse");
        this.awhPage.clickSubmitWarehouseButton();
    }

    /**
     * A alert message should be displayed if capacity entered is invalid
     * @param message
     */
    @Then("The {string} message should be displayed when entering invalid capacity")
    public void isInvalidDataAlertDisplayed(String message) {
        System.out.println("Step: I should see the alert message - " + message);
        assertTrue(awhPage.isInvalidDataAlertDisplayed(message));
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@addingWarehouseDataValidation")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
