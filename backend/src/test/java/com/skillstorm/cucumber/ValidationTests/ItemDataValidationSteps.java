package com.skillstorm.cucumber.ValidationTests;

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

public class ItemDataValidationSteps {
    private WebDriver driver;
    private AddItemPage aiPage;

    @Before("@itemDataValidation")
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

        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver(options);
        this.aiPage = new AddItemPage(driver);
    }

    @Given("I am on the Add Item page")
    public void loadWebsite() {
        System.out.println("Step: I am on the Add Item page");
        this.aiPage.get(); // Loads add item page
    }

    @When("I enter {string} in the item name field") // I enter "<name>" in the item name field
    public void enterItemName(String itemName) {
        System.out.println("Step: I enter " + itemName + " in the item name field");
        this.aiPage.enterItemName(itemName);
    }

    @And("I enter {string} in the item description field") // I enter "<description>" in the item description field
    public void enterItemDescription(String itemDescription) {
        System.out.println("Step: I enter " + itemDescription + " in the item description field");
        this.aiPage.enterItemDescription(itemDescription);
    }

    @And("I enter {string} in the item quantity field") // I enter "<quantity>" in the item quantity field
    public void enterItemQuantity(String itemQuantity) {
        System.out.println("Step: I enter " + itemQuantity + " in the item quantity field");
        this.aiPage.enterItemQuantity(itemQuantity);
    }

    @And("I enter {string} in the item size field") // I enter "<size>" in the item size field
    public void enterItemSize(String itemSize) {
        System.out.println("Step: I enter " + itemSize + " in the item size field");
        this.aiPage.enterItemSize(itemSize);
    }

    @And("I select {string} from the warehouse dropdown") // I select "<warehouse>" from the warehouse dropdown
    public void selectWarehouse(String warehouse) {
        System.out.println("Step: I select " + warehouse + " from the warehouse dropdown");
        this.aiPage.selectWarehouse(warehouse);
    }

    @And("I click the Create button to submit the item")
    public void clickSubmitItemButton() {
        System.out.println("Step: I click the Create button to submit the item");
        this.aiPage.clickSubmitButton();
    }

    @Then("The {string} message should be displayed")
    public void isInvalidDataAlertDisplayed(String message) {
        System.out.println("Step: I should see the alert message - " + message);
        assertTrue(aiPage.isInvalidDataAlertDisplayed(message));
    }

    @After("@addingItemDataValidation")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}