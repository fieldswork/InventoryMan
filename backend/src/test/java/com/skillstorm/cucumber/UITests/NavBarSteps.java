package com.skillstorm.cucumber.UITests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.LandingPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Navigation bar step definitions
 */
public class NavBarSteps {
    
    private WebDriver driver;       // driver
    private LandingPage lPage;      // page object

    @Before("@navBarFunctionality")
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
        this.lPage = new LandingPage(driver);           // initializing our page object
    }

    /**
     * Load the InventoryMan landing page
     */
    @Given("I am on the InventoryMan landing page")
    public void loadWebsite() {
        System.out.println("Step: I am on the InventoryMan landing page");
        this.lPage.get();
    }

    /**
     * Select the InventoryMan button 
     */
    @When("I select the InventoryMan button")
    public void selectInventoryMan() {
        System.out.println("Step: I select the InventoryMan button");
        this.lPage.selectInventoryMan();
    }

    /**
     * Should see that no other buttons are highlighted
     */
    @Then("I should see that no other buttons are highlighted")
    public void noOtherButtonsHighlighted() {
        System.out.println("Step: I should see that no other buttons are highlighted");
        assertTrue(lPage.noOtherButtonsHighlighted());
    }

    /**
     * And that I am on the landing page for InventoryMan
     */
    @Then("that I am on the landing page for InventoryMan")
    public void onInventoryManLandingPage() {
        System.out.println("Step: I am on the landing page for InventoryMan");
        assertTrue(lPage.onInventoryManLandingPage());
    }

    /**
     * Select the Warehouses button
     */
    @When("I select the Warehouses button")
    public void selectWarehouses() {
        System.out.println("Step: I select the Warehouses button");
        this.lPage.selectWarehouses();
    }

    /**
     * Should see that the Warehouses button is highlighted
     */
    @Then("I should see that the Warehouses button is highlighted")
    public void warehousesButtonHighlighted() {
        System.out.println("Step: I should see that the Warehouses button is highlighted");
        assertTrue(lPage.warehousesButtonHighlighted());
    }

    /**
     * And I am on the page with the Warehouses
     */
    @And("I am on the page with the Warehouses")
    public void onWarehousesPage() {
        System.out.println("Step: I am on the page with the Warehouses");
        assertTrue(lPage.onWarehousesPage());
    }

    /**
     * Select the Items button
     */
    @When("I select the Items button")
    public void selectItems() {
        System.out.println("Step: I select the Items button");
        this.lPage.selectItems();
    }

    /**
     * Should see that the Items button is highlighted
     */
    @Then("I should see that the Items button is highlighted")
    public void itemsButtonHighlighted() {
        System.out.println("Step: I should see that the Items button is highlighted");
        assertTrue(lPage.itemsButtonHighlighted());
    }

    /**
     * And that I am on the page with the Items
     */
    @And("that I am on the page with the Items")
    public void onThePageItemsPage() {
        System.out.println("Step: I am on the Items page");
        assertTrue(lPage.onItemsPage());
    }

    /**
     * Select the Add Warehouse button
     */
    @When("I am on the page where I can add Warehouses")
    public void selectAddWarehouse() {
        System.out.println("Step: I select the Add Warehouse button");
        this.lPage.selectAddWarehouse();
    }

    /**
     * Sould see that the Add Warehouse button is highlighted
     */
    @Then("I should see that the Add Warehouse button is highlighted")
    public void addWarehouseButtonHighlighted() {
        System.out.println("Step: I should see that the Add Warehouse button is highlighted");
        assertTrue(lPage.addWarehouseButtonHighlighted());
    }

    /**
     * And that I am on the page where I can add Warehouses
     */
    @And("that I am on the page where I can add Warehouses")
    public void onAddWarehousePage() {
        System.out.println("Step: I am on the page where I can add Warehouses");
        assertTrue(lPage.onAddWarehousePage());
    }

    /**
     * Select the Add Item button
     */
    @When("I select the Add Item button")
    public void selectAddItem() {
        System.out.println("Step: I select the Add Item button");
        this.lPage.selectAddItem();
    }

    /**
     * Should see that the Add Item button is highlighted
     */
    @Then("I should see that the Add Item button is highlighted")
    public void addItemButtonHighlighted() {
        System.out.println("Step: I should see that the Add Item button is highlighted");
        assertTrue(lPage.addItemButtonHighlighted());
    }
    
    /**
     * And I am on the page where I can add items
     */
    @And("I am on the page where I can add items")
    public void onTheAddItemPage() {
        System.out.println("Step: I am on the page where I can add items");
        assertTrue(lPage.onAddItemPage());
    }

    /**
     * Quit the driver - will close all browsers
     */
    @After("@navBarFunctionality") 
    public void after() {
        this.driver.quit();
    }
}
