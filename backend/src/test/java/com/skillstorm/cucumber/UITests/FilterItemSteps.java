package com.skillstorm.cucumber.UITests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.ItemsPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Filter items step definitions
 */
public class FilterItemSteps {
    
    private WebDriver driver;       // driver
    private ItemsPage iPage;        // page object

    @Before("@filterItems")
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
        this.iPage = new ItemsPage(driver);             // initializing our page object
    }

    /**
     * Load the Items page
     */
    @Given("I am on the Items page with several items contained in warehouses")
    public void loadWebsite() {
        System.out.println("Step: I am on the Items page with several items contained in warehouses");
        this.iPage.get();
    }

    /**
     * Select a warehouse in the Filter by Warehouse dropdown
     * @param warehouse
     */
    @When("I select {string} in the Filter by Warehouse dropdown to filter items")
    public void filterByWarehouse(String warehouse) {
        System.out.println("Step: I select to filter items by " + warehouse);
        this.iPage.filterByWarehouse(warehouse);
    }

    /**
     * Only items from the selected warehouse should be displayed in the page
     * @param warehouse
     */
    //Then 
    @Then("Only items from the {string} warehouse should be displayed in the page")
    public void areItemsFiltered(String warehouse) {
        System.out.println("Step: Only items from the " + warehouse + " warehouse should be displayed in the page");
        assertTrue(iPage.areItemsFiltered(warehouse));
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@filterItems")
    public void tearDown() {
        if (driver != null) {
            this.driver.quit();
        }
    }
}
