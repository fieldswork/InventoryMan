package com.skillstorm.cucumber.CRUDTests;

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
import io.cucumber.java.en.And;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Updating an item step definitions
 */
public class EditItemSteps {
    
    private WebDriver driver;       // driver
    private ItemsPage itPage;       // page 

    /**
    * Checks if we are running these tests on Jenkins
    * If so, these tests will be run in a HEADLESS mode
    * If not, these tests will run as usual with browser popping, etc
    */
    @Before("@editItem")
    public void before() {
        ChromeOptions options = new ChromeOptions();

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
    @Given("I am on the Items page")
    public void loadWebsite() {
        System.out.println("Step: I am on the Items page");
        this.itPage.get();
    }

    /**
     * Finds the item to be updated
     * @param itemName
     */
    @When("I find the item {string} in the Items page")
    public void findItem(String itemName) {
        System.out.println("Step: I find the " + itemName + " item in the Items page");
        this.itPage.findItem(itemName);
    }

    /**
     * Click the Edit button
     */
    @And("I click the Edit button to edit the item")
    public void clickEditButton() {
        System.out.println("Step: I click the Edit button to edit the item");
        this.itPage.clickEditItemButton();
    }

    /**
     * Load the Edit Item page
     */
    @Then("I should be redirected to the Edit Item page")
    public void isOnEditItemPage() {
        System.out.println("Step: I should be redirected to the Edit Item page");
        assertTrue(itPage.amIOnEditItemPage());
    }

    /**
     * Enter new item name
     * @param itemName
     */
    @When("I enter the new name {string} in the item name field")
    public void enterItemName(String itemName) {
        System.out.println("Step: I enter " + itemName + " in the item name field");
        this.itPage.enterNewItemName(itemName);
    }

    /**
     * Enter new item description
     * @param itemDescription
     */
    @And("I enter the new description {string} in the item description field")
    public void enterItemDescription(String itemDescription) {
        System.out.println("Step: I enter " + itemDescription + " in the item description field");
        this.itPage.enterNewItemDescription(itemDescription);
    }

    /**
     * Enter new item quantity
     * @param itemQuantity
     */
    @And("I enter the new quantity {string} in the item quantity field")
    public void enterItemQuantity(String itemQuantity) {
        System.out.println("Step: I enter " + itemQuantity + " in the item quantity field");
        this.itPage.enterNewItemQuantity(itemQuantity);
    }

    /**
     * Enter new item size
     * @param itemSize
     */
    @And("I enter the new size {string} in the item size field")
    public void enterItemSize(String itemSize) {
        System.out.println("Step: I enter " + itemSize + " in the item size field");
        this.itPage.enterNewItemSize(itemSize);
    }

    /**
     * Enter new item warehouse that it belongs to
     * @param warehouse
     */
    @And("I select the new item {string} in the item warehouse dropdown")
    public void selectWarehouse(String warehouse) {
        System.out.println("Step: I select " + warehouse + " from the warehouse dropdown");
        this.itPage.selectNewWarehouse(warehouse);
    }

    /**
     * Click the Update button
     */
    @And("I click the Update button to submit the edited item")
    public void clickSubmitItemButton() {
        System.out.println("Step: I click the Update button to submit the edited item");
        this.itPage.clickSubmitItemButton();
    }

    /**
     * If all the item's information are valid, redirect me to the Warehouses page
     */
    @Then("I should be redirected to the Warehouses page if the edited item is valid")
    public void isOnItemsPageAfterEdit() {
        System.out.println("Step: I should be redirected to the Warehouses page");
        assertTrue(itPage.amIOnWarehousesPage());
    }

    /**
     * Navigate to the Items page
     */
    @And("If I navigate to the Items page")
    public void navigateToItemsPage() {
        System.out.println("Step: If I navigate to the Items page");
        this.itPage.get();
    }

    /**
     * Check if item's name got changed
     * @param itemName
     */
    @And("I should see the edited item {string} in the Items page")
    public void checkEditedItem(String itemName) {
        System.out.println("Step: I should see the edited item " + itemName + " in the Items page");
        assertTrue(itPage.findItem(itemName));
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@editItem")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
