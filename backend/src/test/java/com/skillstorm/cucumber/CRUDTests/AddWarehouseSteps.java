package com.skillstorm.cucumber.CRUDTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.AddWarehousePage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AddWarehouseSteps {
    private WebDriver driver;
    private AddWarehousePage awhPage;

    @Before("@addWarehouse")
    public void before() {
        ChromeOptions options = new ChromeOptions();

        // Check if we are running in a headless environment
        if ("true".equals(System.getenv("HEADLESS"))) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-debugging-port=9222"); 

            // Set the binary path for headless Chrome
            options.setBinary("/home/ec2-user/chrome-headless-shell-linux64/chrome-headless-shell");
        }

        // Setup WebDriverManager to manage ChromeDriver
        WebDriverManager.chromedriver().setup();

        this.driver = new ChromeDriver(options);
        this.awhPage = new AddWarehousePage(driver);
    }

    @Given("I have navigated to the Add Warehouse page")
    public void navigateToAddWarehousePage() {
        System.out.println("Step: I have navigated to the Add Warehouse page");
        this.awhPage.get(); // Loads add warehouse page
    }

    @When("I input the warehouse name {string}")
    public void inputWarehouseName(String warehouseName) {
        System.out.println("Step: I input the warehouse name " + warehouseName);
        this.awhPage.enterWarehouseName(warehouseName);
    }

    @And("I provide the warehouse capacity {string}")
    public void provideWarehouseCapacity(String warehouseCapacity) {
        System.out.println("Step: I provide the warehouse capacity " + warehouseCapacity);
        this.awhPage.enterWarehouseCapacity(warehouseCapacity);
    }

    @And("I press the Create button to submit the warehouse form")
    public void pressSubmitWarehouseButton() {
        System.out.println("Step: I press the Create button to submit the warehouse form");
        this.awhPage.clickSubmitWarehouseButton();
    }

    @Then("I should be taken to the Warehouses overview page if the warehouse details are valid")
    public void takenToWarehousesOverviewPage() {
        System.out.println("Step: I should be taken to the Warehouses overview page");
        assertTrue(awhPage.amIOnWarehousesPage());
    }

    @And("I should see the new warehouse {string} listed on the Warehouses page")
    public void verifyWarehouseInWarehousesPage(String warehouseName) {
        System.out.println("Step: I should see the new warehouse " + warehouseName + " listed on the Warehouses page");
        assertTrue(awhPage.isWarehouseInWarehousesPage(warehouseName));
    }

    @And("I should find the capacity {string} shown on the Warehouses page")
    public void verifyCapacityInWarehousesPage(String warehouseCapacity) {
        System.out.println("Step: I should find the capacity " + warehouseCapacity + " shown on the Warehouses page");
        assertTrue(awhPage.isCapacityInWarehousesPage(warehouseCapacity));
    }

    @After("@addWarehouse")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
