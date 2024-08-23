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

public class AddItemSteps {
    private WebDriver driver;
    private AddItemPage aiPage;

    @Before("@addItem")
    public void before() {
        ChromeOptions options = new ChromeOptions();

        // Check if we are running in a headless environment
        if ("true".equals(System.getenv("HEADLESS"))) {
            options.setBinary("/usr/bin/google-chrome");  // Set the correct binary path for Google Chrome
            options.addArguments("--headless");           // Run Chrome in headless mode
            options.addArguments("--no-sandbox");         // Bypass OS security model
            options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
            options.addArguments("--window-size=1920,1080"); // Set the window size if needed
            options.addArguments("--disable-gpu");        // Disable GPU for headless mode (not necessary in some environments)
            options.addArguments("--remote-debugging-port=9222"); // Allows ChromeDriver to interact with the headless Chrome
        }

        // Setup WebDriverManager to manage ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Initialize the ChromeDriver with the specified options
        this.driver = new ChromeDriver(options);
        this.aiPage = new AddItemPage(driver);
    }

    @Given("I am on the Add an Item page")
    public void loadAddItemWebsite() {
        System.out.println("Step: I am on the Add Item page");
        this.aiPage.get(); // Loads add item page
    }

    @When("I enter {string} in the added item name field")
    public void enterItemName(String itemName) {
        System.out.println("Step: I enter " + itemName + " in the added item name field");
        this.aiPage.enterItemName(itemName);
    }

    @And("I enter {string} in the added item description field")
    public void enterAddedItemDescription(String itemDescription) {
        System.out.println("Step: I enter " + itemDescription + " in the added item description field");
        this.aiPage.enterItemDescription(itemDescription);
    }

    @And("I enter {string} in the added item quantity field")
    public void enterAddedItemQuantity(String itemQuantity) {
        System.out.println("Step: I enter " + itemQuantity + " in the item quantity field");
        this.aiPage.enterItemQuantity(itemQuantity);
    }

    @And("I enter {string} in the added item size field")
    public void enterAddedItemSize(String itemSize) {
        System.out.println("Step: I enter " + itemSize + " in the item size field");
        this.aiPage.enterItemSize(itemSize);
    }

    @And("I select {string} from the warehouse dropdown for the added item")
    public void selectAddedItemWarehouse(String warehouse) {
        System.out.println("Step: I select " + warehouse + " from the warehouse dropdown");
        this.aiPage.selectWarehouse(warehouse);
    }

    @And("I click the Create button to submit the added item")
    public void clickSubmitAddedItemButton() {
        System.out.println("Step: I click the Create button to submit the added item");
        this.aiPage.clickSubmitButton();
    }

    @Then("I should be redirected to the Warehouses page if the item is valid")
    public void amIOnWarehousesPage() {
        System.out.println("Step: I should be redirected to the Warehouses page");
        assertTrue(aiPage.amIOnWarehousesPage());
    }

    @After("@addItem")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
