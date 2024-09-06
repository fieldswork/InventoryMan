package com.skillstorm.cucumber.CRUDTests;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.WarehousesPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Delete an warehouse step definitions
 */
public class DeleteWarehouseSteps {
    
    private WebDriver driver;           // driver
    private WarehousesPage whPage;      // page object

    @Before("@deleteWarehouse")
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
    @Given("I am viewing the Warehouses")
    public void loadWarehouses() {
        System.out.println("Step: I am on the Warehouses page");
        this.whPage.get();
    }

    /**
     * Finds the warehouse to be deleted
     * @param warehouseName
     */
    @When("I find the Warehouse {string} on the page")
    public void findWarehouseToDelete(String warehouseName) {
        System.out.println("Step: I find the " + warehouseName + " warehouse in the Warehouses page");
        this.whPage.findWarehouse(warehouseName);
    }

    /**
     * Click the Delete button
     */
    @And("I click the Delete button to delete the Warehouse")
    public void clickDeleteWarehouseButton() {
        System.out.println("Step: I click the Delete button to delete the Warehouse");
        this.whPage.clickDeleteButton();
    }

    /**
     * Click the OK button to confirm the deletion
     */
    @And("I click the OK button to confirm deletion")
    public void clickOKButtonToConfirm() {
        System.out.println("Step: I click the OK button to confirm deletion");
        this.whPage.clickOKButton();
    }

    /**
     * Checks if the deleted warehouse is not listed on the Warehouses page anymore
     * @param warehouseName
     */
    @Then("The warehouse {string} should be deleted from the Warehouse page")
    public void warehouseDeleted(String warehouseName) {
        System.out.println("Step: The " + warehouseName + " warehouse should be deleted from the Warehouses page");
        assertFalse(whPage.findWarehouse(warehouseName));
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@deleteWarehouse")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
