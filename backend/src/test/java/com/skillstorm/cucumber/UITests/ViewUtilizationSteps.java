package com.skillstorm.cucumber.UITests;

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

public class ViewUtilizationSteps {
 
    private WebDriver driver;               // driver
    private WarehousesPage whPage;          // page object

    @Before("@utilBarRendering")
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
    @Given("I am on the page with the Warehouses cards")
    public void onWarehousesPageString() {
        this.whPage.get();
    }

    /**
     * Checks if the warehouse of interest is being displayed
     * @param warehouse
     */
    @When("the warehouse {string} is displaying on the page")
    public void warehouseIsDisplaying(String warehouse) {
        this.whPage.findWarehouse(warehouse);
    }

    /**
     * Should see the utilization bar for the warehouse of interest displaying its utilization
     * @param warehouse
     * @param utilization
     */
    //Then I s
    @Then("I should see the utilization bar for the {string} warehouse displaying {string} utilization")
    public void utilizationBarIsDisplaying(String warehouse, String utilization) {
        this.whPage.findUtilization(warehouse, utilization);
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@utilBarRendering")
    public void after() {
        this.driver.quit();
    }
}