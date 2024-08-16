package com.skillstorm.cucumber;

import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.skillstorm.selenium.InventoryManPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadPage {

    private WebDriver driver;
    private InventoryManPage imPage;

    @Before
    public void before() {
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.imPage = new InventoryManPage(driver);
    }

    @Given("I attempt to load the website")
    public void loadWebsite() {
        System.out.println("Step: I attempt to load the website");
        imPage.get();
    }

    @When("the website loads")
    public void websiteLoads() {
        System.out.println("Step: the website loads");
        // Wait for the page to load
    }

    @Then("I should see the title {string}")
    public void checkTitle(String expectedTitle) {
        System.out.println("Step: I should see the title " + expectedTitle);
        assertEquals(expectedTitle, driver.getTitle());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
