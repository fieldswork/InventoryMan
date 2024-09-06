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
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Sort warehouses step definitions
 */
public class SortWarehousesSteps {
    
    private WebDriver driver;           // driver
    private WarehousesPage whPage;      // page object

    @Before("@sortWarehouses")
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
     * Load the Warehouses page
     */
    @Given("I am in the Warehouses page")
    public void loadWebsite() {
        System.out.println("Step: I am on the Warehouses page");
        this.whPage.get();
    }

    /**
     * Select to sort by sortingOrder in the Sort By dropdown
     * @param sortingOrder
     */
    @When("I select {string} in the Sort By dropdown to sort Warehouses")
    public void selectSortingOption(String sortingOrder) {
        System.out.println("Step: I select to sort warehouse by " + sortingOrder);
        this.whPage.selectSortingOption(sortingOrder);
    }

    /**
     * Warehouses should be displayed in the Warehouses page by sortingOrder
     * @param sortingOrder
     */
     @Then("Warehouses should be displayed in the page by {string} order")
    public void isWarehousesOrdered(String sortingOrder) {
        System.out.println("Step: Warehouse should be order by " + sortingOrder);
        assertTrue(whPage.iswarehousesOrdered(sortingOrder));
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@sortWarehouses")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
