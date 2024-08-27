package com.skillstorm.cucumber.UITests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.WarehousesPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ViewUtilizationSteps {
 
    private WebDriver driver;
    private WarehousesPage whPage;

    @Before("@utilBarRendering")
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
        this.whPage = new WarehousesPage(driver);
    }

    // Given I am on the page with the Warehouses cards
    @Given("I am on the page with the Warehouses cards")
    public void onWarehousesPageString() {
        this.whPage.get();
    }

    // When the warehouse "<warehouse>" is displaying on the page
    @When("the warehouse {string} is displaying on the page")
    public void warehouseIsDisplaying(String warehouse) {
        this.whPage.findWarehouse(warehouse);
    }

    //Then I should see the utilization bar for the "<warehouse>" warehouse displaying "<utilization>" utilization
    @Then("I should see the utilization bar for the {string} warehouse displaying {string} utilization")
    public void utilizationBarIsDisplaying(String warehouse, String utilization) {
        this.whPage.findUtilization(warehouse, utilization);
    }

    @After("@utilBarRendering")
    public void after() {
        this.driver.quit();
    }
}