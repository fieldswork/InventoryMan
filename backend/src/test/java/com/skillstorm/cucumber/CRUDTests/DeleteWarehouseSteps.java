package com.skillstorm.cucumber.CRUDTests;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.WarehousesPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteWarehouseSteps {
    
    private WebDriver driver;
    private WarehousesPage whPage;

    @Before("@deleteWarehouse")
    public void before() {
        ChromeOptions options = new ChromeOptions();

        // Check if we are running in a headless environment
        if ("true".equals(System.getenv("HEADLESS"))) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-debugging-port=9222"); 

            // Set the binary path for headless Chrome and the ChromeDriver location
            options.setBinary("/home/ec2-user/chrome-headless-shell-linux64/chrome-headless-shell");
            System.setProperty("webdriver.chrome.driver", "/home/ec2-user/chromedriver-linux64/chromedriver");
        }

        this.driver = new ChromeDriver(options);
        this.whPage = new WarehousesPage(driver);
    }

    // Given I am viewing the Warehouses
    @Given("I am viewing the Warehouses")
    public void loadWarehouses() {
        System.out.println("Step: I am on the Warehouses page");
        this.whPage.get();
    }

    // When I find the Warehouse "<item>" on the page
    @When("I find the Warehouse {string} on the page")
    public void findWarehouseToDelete(String warehouseName) {
        System.out.println("Step: I find the " + warehouseName + " warehouse in the Warehouses page");
        this.whPage.findWarehouse(warehouseName);
    }

    // And I click the Delete button to delete the Warehouse
    @And("I click the Delete button to delete the Warehouse")
    public void clickDeleteWarehouseButton() {
        System.out.println("Step: I click the Delete button to delete the Warehouse");
        this.whPage.clickDeleteButton();
    }

    // And I click the OK button to confirm deletion
    @And("I click the OK button to confirm deletion")
    public void clickOKButtonToConfirm() {
        System.out.println("Step: I click the OK button to confirm deletion");
        this.whPage.clickOKButton();
    }

    // Then The warehouse "<item>" should be deleted from the Warehouse page
    @Then("The warehouse {string} should be deleted from the Warehouse page")
    public void warehouseDeleted(String warehouseName) {
        System.out.println("Step: The " + warehouseName + " warehouse should be deleted from the Warehouses page");
        assertFalse(whPage.findWarehouse(warehouseName));
    }

    @After("@deleteWarehouse")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
