package com.skillstorm.selenium;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {
    
    private WebDriver driver;
    private static final String url = "http://inventoryman.s3-website-us-east-1.amazonaws.com/";

    // /html/body/div/div/nav/a - InventoryMan button
    @FindBy(xpath = "/html/body/div/div/nav/a")
    private WebElement inventoryManButton;

    // Warehouses button: //*[@id="view-warehouse"]
    @FindBy(xpath = "//*[@id=\"view-warehouse\"]")
    private WebElement warehousesButton;

    // Items button: //*[@id="view-items"] 
    @FindBy(xpath = "//*[@id=\"view-items\"]")
    private WebElement itemsButton;

    // //*[@id="add-warehouse"] - add warehouse button
    @FindBy(xpath = "//*[@id=\"add-warehouse\"]")
    private WebElement addWarehouseButton;

    // //*[@id="add-item"] - add item button
    @FindBy(xpath = "//*[@id=\"add-item\"]")
    private WebElement addItemButton;

    public LandingPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigate to the Landing page
     */
    public void get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.driver.get(url);
    }

    // Check if the user is on the landing page
    public boolean amIOnLandingPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com");
    }

    // Select Warehouses page
    public void selectWarehousesPage() {
        //this.warehousesButton.click();
        this.warehousesButton.sendKeys(Keys.ENTER);
    }

    // Select Items page
    public void selectItemsPage() {
        //this.itemsButton.click();
        this.itemsButton.sendKeys(Keys.ENTER);
    }

    // Checks if I am on Warehouses page
    public boolean amIOnWarehousesPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses");
    }

    // Checks if I am on the Items page
    public boolean amIOnItemsPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/items");
    }

    // Select InventoryMan
    public void selectInventoryMan() {
        //this.inventoryManButton.click();
        this.inventoryManButton.sendKeys(Keys.ENTER);
    }

    // Check if no other buttons are highlighted
    public boolean noOtherButtonsHighlighted() {
        return (!this.warehousesButton.getAttribute("class").contains("active") &&
                !this.itemsButton.getAttribute("class").contains("active") &&
                !this.addWarehouseButton.getAttribute("class").contains("active") &&
                !this.addItemButton.getAttribute("class").contains("active"));
    }

    // On InventoryMan landing page
    public boolean onInventoryManLandingPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/");
    }

    // Select Warehouses
    public void selectWarehouses() {
        //this.warehousesButton.click();
        this.warehousesButton.sendKeys(Keys.ENTER);
    }

    // Warehouses button should be highlighted
    public boolean warehousesButtonHighlighted() {
        return this.warehousesButton.getAttribute("class").contains("active");
    }

    // On Warehouses Page
    public boolean onWarehousesPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses");
    }

    // Select Items
    public void selectItems() {
        //this.itemsButton.click();
        this.itemsButton.sendKeys(Keys.ENTER);
    }

    // Items button should be highlighted
    public boolean itemsButtonHighlighted() {
        return this.itemsButton.getAttribute("class").contains("active");
    }

    // On Items page
    public boolean onItemsPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/items");
    }

    // Select Add Warehouse   
    public void selectAddWarehouse() {
        //this.addWarehouseButton.click();
        this.addWarehouseButton.sendKeys(Keys.ENTER);
    }

    // Add Warehouse button should be highlighted
    public boolean addWarehouseButtonHighlighted() {
        return this.addWarehouseButton.getAttribute("class").contains("active");
    }

    // On Add Warehouse page
    public boolean onAddWarehousePage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/add-warehouse");
    }

    // Select Add Item
    public void selectAddItem() {
        //this.addItemButton.click();
        this.addItemButton.sendKeys(Keys.ENTER);
    }

    // Add Item button should be highlighted
    public boolean addItemButtonHighlighted() {
        return this.addItemButton.getAttribute("class").contains("active");
    }

    // On Add Item page
    public boolean onAddItemPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/add-item");
    }
}
