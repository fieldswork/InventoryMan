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

public class DeleteItemSteps {
    private WebDriver driver;
    private ItemsPage itPage;

    @Before("@deleteItem")
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

    // Given I am viewing items
    @Given("I am viewing items")
    public void loadItem() {
        System.out.println("Step: I am on the Items page");
        this.itPage.get();
    }
    
    // When I find the item "<item>" on the Items page
    @When("I find the item {string} on the Items page")
    public void findItemToDelete(String itemName) {
        System.out.println("Step: I find the " + itemName + " item in the Items page");
        this.itPage.findItem(itemName);
    }

    // And I click the Delete button
    @And("I click the Delete button")
    public void clickDeleteButton() {
        System.out.println("Step: I click the Delete button");
        this.itPage.clickDeleteItemButton();
    }

    // And I click the OK button to confirm the deletion
    @And("I click the OK button to confirm the deletion")
    public void clickOKButton() {
        System.out.println("Step: I click the OK button to confirm the deletion");
        this.itPage.clickOKButton();
    }

    // Then The item "<item>" should be deleted from the Items page
    @Then("The item {string} should be deleted from the Items page")
    public void itemDeleted(String itemName) {
        System.out.println("Step: The " + itemName + " item should be deleted from the Items page");
        assertFalse(itPage.findItem(itemName));
    }

    @After("@deleteItem")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
