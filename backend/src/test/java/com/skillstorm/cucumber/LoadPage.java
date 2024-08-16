package com.skillstorm.cucumber;

import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.InventoryManPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadPage {

    private WebDriver driver;
    private InventoryManPage imPage;

    @Before("@loading")
    public void before() {
        System.out.println("Before hook: initializing WebDriver and InventoryManPage");
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.imPage = new InventoryManPage(driver);
        System.out.println("Before hook: InventoryManPage initialized: " + (this.imPage != null));
    }

    @Given("I attempt to load the website")
    public void loadWebsite() {
        System.out.println("Step: I attempt to load the website");
        if (this.imPage == null) {
            System.out.println("Error: InventoryManPage is null!");
        } else {
            this.imPage.get();
        }
    }

    @When("the website loads")
    public void websiteLoads() {
        System.out.println("Step: the website loads");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("I should see the title {string}")
    public void checkTitle(String expectedTitle) {
        System.out.println("Step: I should see the title " + expectedTitle);
        if (this.imPage == null) {
            System.out.println("Error: InventoryManPage is null in checkTitle!");
        } else {
            String actualTitle = this.imPage.getTabTitle();
            System.out.println("Actual title: " + actualTitle);
            assertEquals(expectedTitle, actualTitle, "The page title should match the expected title");
        }
    }

    @After("@loading")
    public void tearDown() {
        if (driver != null) {
            this.driver.quit();
        }
    }
}