package com.skillstorm.cucumber.CRUDTests;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.ItemsPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Delete an item step definitions
 */
public class DeleteItemSteps {
    
    private WebDriver driver;   // driver
    private ItemsPage itPage;   // page object

    @Before("@deleteItem")
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
        this.itPage = new ItemsPage(driver);            // initializing our page object
    }

    /**
     * Loads add Items page
     */
    @Given("I am viewing items")
    public void loadItem() {
        System.out.println("Step: I am on the Items page");
        this.itPage.get();
    }
    
    /**
     * Finds the item to be deleted
     * @param itemName
     */
    @When("I find the item {string} on the Items page")
    public void findItemToDelete(String itemName) {
        System.out.println("Step: I find the " + itemName + " item in the Items page");
        this.itPage.findItem(itemName);
    }

    /**
     * Click the Delete button
     */
    @And("I click the Delete button")
    public void clickDeleteButton() {
        System.out.println("Step: I click the Delete button");
        this.itPage.clickDeleteItemButton();
    }

    /**
     * Click the OK button to confirm the deletion
     */
    @And("I click the OK button to confirm the deletion")
    public void clickOKButton() {
        System.out.println("Step: I click the OK button to confirm the deletion");
        this.itPage.clickOKButton();
    }

    /**
     * Checks if the deleted item is not listed on the Items page anymore
     * @param itemName
     */
    @Then("The item {string} should be deleted from the Items page")
    public void itemDeleted(String itemName) {
        System.out.println("Step: The " + itemName + " item should be deleted from the Items page");
        assertFalse(itPage.findItem(itemName));
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@deleteItem")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
