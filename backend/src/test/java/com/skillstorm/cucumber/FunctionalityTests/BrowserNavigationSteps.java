package com.skillstorm.cucumber.FunctionalityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.LandingPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserNavigationSteps {
    
    private WebDriver driver;
    private LandingPage lPage;

    @Before("@browserNavigation")
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
        this.lPage = new LandingPage(driver);
    }

    //Given I am on the landing page of InventoryMan
    @Given("I am on the landing page of InventoryMan")
    public void loadWebsite() {
        System.out.println("Step: I am on the landing page of InventoryMan");
        this.lPage.get();
    }

    //When I select the Warehouses page from the navigation bar
    @When("I select the Warehouses page from the navigation bar")
    public void selectWarehousesPage() {
        System.out.println("Step: I select the Warehouses page from the navigation bar");
        this.lPage.selectWarehousesPage();
    }

    //And I select the Items page from the navigation bar
    @When("I select the Items page from the navigation bar")
    public void selectItemsPage() {
        System.out.println("Step: I select the Items page from the navigation bar");
        this.lPage.selectItemsPage();
    }

    //And I navigate back using the browser back button
    @When("I navigate back using the browser back button")
    public void navigateBack() {
        System.out.println("Step: I navigate back using the browser back button");
        this.driver.navigate().back();
    }

    //Then I should see the Warehouses page
    @Then("I should see the Warehouses page")
    public void isOnWarehousesPage() {
        System.out.println("Step: I should see the Warehouses page");
        assertTrue(lPage.amIOnWarehousesPage());
    }

    //When I navigate forward using the browser forward button
    @When("I navigate forward using the browser forward button")
    public void navigateForward() {
        System.out.println("Step: I navigate forward using the browser forward button");
        this.driver.navigate().forward();
    }

    //Then I should see the Items page
    @Then("I should see the Items page")
    public void isOnItemsPage() {
        System.out.println("Step: I should see the Items page");
        assertTrue(lPage.amIOnItemsPage());
    }

    @After("@browserNavigation")
    public void tearDown() {
        if (driver != null) {
            this.driver.quit();
        }
    }
}
