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

public class EditItemSteps {
    private WebDriver driver;
    private ItemsPage itPage;

    @Before("@editItem")
    public void before() {
        ChromeOptions options = new ChromeOptions();

        // Check if we are running in a headless environment
        if ("true".equals(System.getenv("HEADLESS"))) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080"); 

            // Set the binary path for headless Chrome
            options.setBinary("/usr/bin/google-chrome");
        }

        // Setup WebDriverManager to manage ChromeDriver
        WebDriverManager.chromedriver().setup();

        this.driver = new ChromeDriver(options);
        this.itPage = new ItemsPage(driver);
    }

    @Given("I am on the Items page")
    public void loadWebsite() {
        System.out.println("Step: I am on the Items page");
        this.itPage.get();
    }

    @When("I find the item {string} in the Items page")
    public void findItem(String itemName) {
        System.out.println("Step: I find the " + itemName + " item in the Items page");
        this.itPage.findItem(itemName);
    }

    @And("I click the Edit button to edit the item")
    public void clickEditButton() {
        System.out.println("Step: I click the Edit button to edit the item");
        this.itPage.clickEditItemButton();
    }

    @Then("I should be redirected to the Edit Item page")
    public void isOnEditItemPage() {
        System.out.println("Step: I should be redirected to the Edit Item page");
        assertTrue(itPage.amIOnEditItemPage());
    }

    @When("I enter the new name {string} in the item name field")
    public void enterItemName(String itemName) {
        System.out.println("Step: I enter " + itemName + " in the item name field");
        this.itPage.enterNewItemName(itemName);
    }

    @And("I enter the new description {string} in the item description field")
    public void enterItemDescription(String itemDescription) {
        System.out.println("Step: I enter " + itemDescription + " in the item description field");
        this.itPage.enterNewItemDescription(itemDescription);
    }

    @And("I enter the new quantity {string} in the item quantity field")
    public void enterItemQuantity(String itemQuantity) {
        System.out.println("Step: I enter " + itemQuantity + " in the item quantity field");
        this.itPage.enterNewItemQuantity(itemQuantity);
    }

    @And("I enter the new size {string} in the item size field")
    public void enterItemSize(String itemSize) {
        System.out.println("Step: I enter " + itemSize + " in the item size field");
        this.itPage.enterNewItemSize(itemSize);
    }

    @And("I select the new item {string} in the item warehouse dropdown")
    public void selectWarehouse(String warehouse) {
        System.out.println("Step: I select " + warehouse + " from the warehouse dropdown");
        this.itPage.selectNewWarehouse(warehouse);
    }

    @And("I click the Update button to submit the edited item")
    public void clickSubmitItemButton() {
        System.out.println("Step: I click the Update button to submit the edited item");
        this.itPage.clickSubmitItemButton();
    }

    @Then("I should be redirected to the Warehouses page if the edited item is valid")
    public void isOnItemsPageAfterEdit() {
        System.out.println("Step: I should be redirected to the Warehouses page");
        assertTrue(itPage.amIOnWarehousesPage());
    }

    @And("If I navigate to the Items page")
    public void navigateToItemsPage() {
        System.out.println("Step: If I navigate to the Items page");
        this.itPage.get();
    }

    @And("I should see the edited item {string} in the Items page")
    public void checkEditedItem(String itemName) {
        System.out.println("Step: I should see the edited item " + itemName + " in the Items page");
        assertTrue(itPage.findItem(itemName));
    }

    @After("@editItem")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
