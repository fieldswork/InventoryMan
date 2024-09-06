package com.skillstorm.cucumber.CRUDTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.AddItemPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Adding an item step definitions
 */
public class AddItemSteps {

    private WebDriver driver;       // driver
    private AddItemPage aiPage;     // page object

    /**
     * Step definitions for adding an item
     */
    @Before("@addItem")
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

        WebDriverManager.chromedriver().setup();    //setting up the chrome driver

        this.driver = new ChromeDriver(options);    // assigning our driver to be of a type Chrome
        this.aiPage = new AddItemPage(driver);      // initializing our page object
    }

    /**
     * Loads add Items page
     */
    @Given("I am on the Add an Item page")
    public void loadAddItemWebsite() {
        System.out.println("Step: I am on the Add Item page");
        System.out.println("HEADLESS value: '" + System.getenv("HEADLESS") + "'");

        this.aiPage.get();
    }

    /**
     * Enters the item name
     * @param itemName
     */
    @When("I enter {string} in the added item name field")
    public void enterItemName(String itemName) {
        System.out.println("Step: I enter " + itemName + " in the added item name field");
        this.aiPage.enterItemName(itemName);
    }

    /**
     * Enters the description of the item
     * @param itemDescription
     */
    @And("I enter {string} in the added item description field")
    public void enterAddedItemDescription(String itemDescription) {
        System.out.println("Step: I enter " + itemDescription + " in the added item description field");
        this.aiPage.enterItemDescription(itemDescription);
    }

    /**
     * Enters the quantity of the item 
     * @param itemQuantity
     */
    @And("I enter {string} in the added item quantity field")
    public void enterAddedItemQuantity(String itemQuantity) {
        System.out.println("Step: I enter " + itemQuantity + " in the item quantity field");
        this.aiPage.enterItemQuantity(itemQuantity);
    }

    /**
     * Enters the item size
     * @param itemSize
     */
    @And("I enter {string} in the added item size field")
    public void enterAddedItemSize(String itemSize) {
        System.out.println("Step: I enter " + itemSize + " in the item size field");
        this.aiPage.enterItemSize(itemSize);
    }

    /**
     * Selects the warehouse that the item belongs to
     * @param warehouse
     */
    @And("I select {string} from the warehouse dropdown for the added item")
    public void selectAddedItemWarehouse(String warehouse) {
        System.out.println("Step: I select " + warehouse + " from the warehouse dropdown");
        this.aiPage.selectWarehouse(warehouse);
    }

    /**
     * Click the Create button after entering all the information of the item
     */
    @And("I click the Create button to submit the added item")
    public void clickSubmitAddedItemButton() {
        System.out.println("Step: I click the Create button to submit the added item");
        this.aiPage.clickSubmitButton();
    }

    /**
     * Checks if after creating a valid item, we are redirected to the Warehouses page
     */
    @Then("I should be redirected to the Warehouses page if the item is valid")
    public void amIOnWarehousesPage() {
        System.out.println("Step: I should be redirected to the Warehouses page");
        assertTrue(aiPage.amIOnWarehousesPage());
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@addItem")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
