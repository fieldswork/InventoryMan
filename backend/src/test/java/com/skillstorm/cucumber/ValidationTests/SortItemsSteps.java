package com.skillstorm.cucumber.ValidationTests;

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
 * Sort items step definitions
 */
public class SortItemsSteps {

    private WebDriver driver;       // driver
    private ItemsPage whPage;       // page object


    @Before("@sortItems")
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
        this.whPage = new ItemsPage(driver);            // initializing our page object
    }

    /**
     * Load Items page
     */
    @Given("I am in the Items page")
    public void loadWebsite() {
        System.out.println("Step: I am on the Items page");
        this.whPage.get();
    }

    /**
     * Select to sort by sortingOrder in the Sort By dropdown
     * @param sortingOrder
     */
    @When("I select {string} in the Sort By dropdown to sort Items")
    public void selectSortingOption(String sortingOrder) {
        System.out.println("Step: I select to sort items by " + sortingOrder);
        this.whPage.selectSortingOption(sortingOrder);
    }

    /**
     * Items should be displayed in the Items page by sortingOrder
     * @param sortingOrder
     */
     @Then("Items should be displayed in the page by {string} order")
    public void isWarehousesOrdered(String sortingOrder) {
        System.out.println("Step: Items should be order by " + sortingOrder);
        assertTrue(whPage.isItemsOrdered(sortingOrder));
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@sortItems")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
