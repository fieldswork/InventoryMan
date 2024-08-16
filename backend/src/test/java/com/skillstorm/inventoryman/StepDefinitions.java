package com.skillstorm.inventoryman;

import com.skillstorm.selenium.InventoryManPage;
import io.cucumber.java.en.*;
import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {

    private WebDriver driver;
    private InventoryManPage inventoryManPage;

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.inventoryManPage = new InventoryManPage(driver);
    }

    @Given("I am on the website")
    public void iAmOnTheWebsite() {
        inventoryManPage.navigateTo();
    }

    @Then("I should see the title {string}")
    public void iShouldSeeTheTitle(String expectedTitle) {
        String actualTitle = inventoryManPage.getPageTitle();
        assertEquals(expectedTitle, actualTitle, "Expected title: " + expectedTitle + " but found: " + actualTitle);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
