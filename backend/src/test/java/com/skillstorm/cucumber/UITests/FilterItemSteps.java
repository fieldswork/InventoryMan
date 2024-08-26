package com.skillstorm.cucumber.UITests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.internal.verification.Only;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.selenium.ItemsPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FilterItemSteps {
    
    private WebDriver driver;
    private ItemsPage iPage;

    @Before("@filterItems")
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
        this.iPage = new ItemsPage(driver);
    }

    //Given I am on the Items page with several items contained in warehouses
    @Given("I am on the Items page with several items contained in warehouses")
    public void loadWebsite() {
        System.out.println("Step: I am on the Items page with several items contained in warehouses");
        this.iPage.get();
    }

    //When I select "<warehouse>" in the Filter by Warehouse dropdown to filter items
    @When("I select {string} in the Filter by Warehouse dropdown to filter items")
    public void filterByWarehouse(String warehouse) {
        System.out.println("Step: I select to filter items by " + warehouse);
        this.iPage.filterByWarehouse(warehouse);
    }

    //Then Only items from the "<warehouse>" warehouse should be displayed in the page
    @Then("Only items from the {string} warehouse should be displayed in the page")
    public void areItemsFiltered(String warehouse) {
        System.out.println("Step: Only items from the " + warehouse + " warehouse should be displayed in the page");
        assertTrue(iPage.areItemsFiltered(warehouse));
    }

    @After("@filterItems")
    public void tearDown() {
        if (driver != null) {
            this.driver.quit();
        }
    }
}
