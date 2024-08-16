package com.skillstorm.cucumber;

import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.skillstorm.selenium.InventoryManPage;
import com.skillstorm.selenium.AddWarehousePage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateWarehouse {

    private WebDriver driver;
    private InventoryManPage imPage;
    private AddWarehousePage awPage;

    @Before("@createwarehouse")
    public void before() {
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.imPage = new InventoryManPage(driver);
        this.awPage = new AddWarehousePage(driver); // need to implement
    }

    @Given("I am on the main page")
    public void loadWebsite() {
        System.out.println("Step: I am on the main page");
        this.imPage.get();
    }

    @When("I click on the Add Warehouse button")
    public void clickAddWarehouse() {
        System.out.println("Step: I click on the Add Warehouse button");
        this.imPage.clickAddWarehouseButton();
    }

    @When("the page loads")
    public void pageLoads() {
        System.out.println("Step: the page loads");
        //awPage.waitForFormToLoad();
    }

    @When("I enter the warehouse name {string}")
    public void enterWarehouseName(String name) {
        System.out.println("Step: I enter the warehouse name " + name);
        //awPage.enterWarehouseName(name); 
    }

    @When("I enter the warehouse capacity {string}")
    public void enterWarehouseCapacity(String capacity) {
        System.out.println("Step: I enter the warehouse capacity " + capacity);
        //awPage.enterWarehouseCapacity(capacity); 
    }

    @When("I click the Create button")
    public void clickCreateButton() {
        System.out.println("Step: I click the Create button");
        //awPage.clickCreateButton(); 
    }

    @Then("I should be redirected to {string}")
    public void checkRedirection(String expectedUrl) {
        System.out.println("Step: I should be redirected to " + expectedUrl);
        //String currentUrl = driver.getCurrentUrl();
        //assertEquals(expectedUrl, currentUrl, "The current URL should match the expected URL.");
    }

    @Then("I should see the warehouse {string} in the list")
    public void checkWarehouseInList(String warehouseName) {
        System.out.println("Step: I should see the warehouse " + warehouseName + " in the list");
        //boolean isWarehouseInList = awPage.isWarehouseInList(warehouseName);
        //assertEquals(true, isWarehouseInList, "The warehouse should be in the list.");
    }

    @Then("I should see the warehouse text {string}")
    public void checkWarehouseText(String expectedText) {
        System.out.println("Step: I should see the warehouse text " + expectedText);
        //String actualText = awPage.getWarehouseText(); 
        //assertEquals(expectedText, actualText, "The warehouse text should match the expected text.");
    }

    //@After("@createwarehouse")
    //public void tearDown() {
    //    if (driver != null) {
    //        this.driver.quit();
    //    }
    //}
}
