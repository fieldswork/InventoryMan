package com.skillstorm.cucumber.FunctionalityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.LandingPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Browser navigation step definitions
 */
public class BrowserNavigationSteps {
    
    private WebDriver driver;       // driver
    private LandingPage lPage;      // page object

    @Before("@browserNavigation")
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
        this.lPage = new LandingPage(driver);           // initializing our page object
    }

    /**
     * Load the landing page of InventoryMan
     */
    @Given("I am on the landing page of InventoryMan")
    public void loadWebsite() {
        System.out.println("Step: I am on the landing page of InventoryMan");
        this.lPage.get();
    }

    /**
     * Select the Warehouses page from the navigation bar
     */
    @When("I select the Warehouses page from the navigation bar")
    public void selectWarehousesPage() {
        System.out.println("Step: I select the Warehouses page from the navigation bar");
        this.lPage.selectWarehousesPage();
    }

    /**
     * Select the Items page from the navigation bar
     */
    @When("I select the Items page from the navigation bar")
    public void selectItemsPage() {
        System.out.println("Step: I select the Items page from the navigation bar");
        this.lPage.selectItemsPage();
    }

    /**
     * Navigate back using the browser back button
     */
    @When("I navigate back using the browser back button")
    public void navigateBack() {
        System.out.println("Step: I navigate back using the browser back button");
        this.driver.navigate().back();
    }

    /**
     * Should be navigated to the Warehouses page
     */
    @Then("I should see the Warehouses page")
    public void isOnWarehousesPage() {
        System.out.println("Step: I should see the Warehouses page");
        assertTrue(lPage.amIOnWarehousesPage());
    }

    /**
     * Navigate forward using the browser forward button
     */
    @When("I navigate forward using the browser forward button")
    public void navigateForward() {
        System.out.println("Step: I navigate forward using the browser forward button");
        this.driver.navigate().forward();
    }

    /**
     * Should be navigated to the Items page 
     */
    @Then("I should see the Items page")
    public void isOnItemsPage() {
        System.out.println("Step: I should see the Items page");
        assertTrue(lPage.amIOnItemsPage());
    }
    
    /**
     * Quit the driver - will close all browsers
     */
    @After("@browserNavigation")
    public void tearDown() {
        if (driver != null) {
            this.driver.quit();
        }
    }
}
