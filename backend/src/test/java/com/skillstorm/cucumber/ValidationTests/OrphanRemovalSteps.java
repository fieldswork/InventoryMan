package com.skillstorm.cucumber.ValidationTests;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.ItemsPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Orphan removal step definitions
 */
public class OrphanRemovalSteps {
    
    private WebDriver driver;       // driver
    private ItemsPage itPage;       // page object

    @Before("@orphanRemoval")
    public void before() {
        ChromeOptions options = new ChromeOptions();

        /**
        * Checks if we are running these tests on Jenkins
        * If so, these tests will be run in a HEADLESS mode
        * If not, these tests will run as usual with browser popping, etc
        */
        if ("true".equals(System.getenv("HEADLESS"))) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-debugging-port=9222");
        }

        WebDriverManager.chromedriver().setup();        //setting up the chrome driver

        this.driver = new ChromeDriver(options);        // assigning our driver to be of a type Chrome
        this.itPage = new ItemsPage(driver);            // initializing our page object
    }

    /**
     * Load the items page after the orphantestwh warehouse has been deleted
     */
    @Given("I am on the items page after the orphantestwh warehouse has been deleted")
    public void loadItemsAfterDeletion() {
        System.out.println("Step: I am on the items page after orphans have been removed");
        this.itPage.get();
    }

    /**
     * Should not see the orphaned item in the items list
     * @param item
     */
    //Then I s
    @Then("I should not see the orphaned item {string} in the items list")
    public void verifyOrphanedItem(String item) {
        System.out.println("Step: I should not see the orphaned item " + item + " in the items list");
        assertFalse(itPage.isItemPresent(item));
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@orphanRemoval")
    public void tearDown() {
        this.driver.quit();
    }
}
