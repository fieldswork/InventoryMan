package com.skillstorm.selenium;

import java.time.Duration;

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

    //selectWarehousesPage
    public void selectWarehousesPage() {
        this.warehousesButton.click();
    }

    // selectItemsPage
    public void selectItemsPage() {
        this.itemsButton.click();
    }

    // amIOnWarehousesPage
    public boolean amIOnWarehousesPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses");
    }

    // amIOnItemsPage
    public boolean amIOnItemsPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/items");
    }

    // selectInventoryMan
    public void selectInventoryMan() {
        this.inventoryManButton.click();
    }

    public boolean noOtherButtonsHighlighted() {
        return (!this.warehousesButton.getAttribute("class").contains("active") &&
                !this.itemsButton.getAttribute("class").contains("active") &&
                !this.addWarehouseButton.getAttribute("class").contains("active") &&
                !this.addItemButton.getAttribute("class").contains("active"));
    }

    // onInventoryManLandingPage
    public boolean onInventoryManLandingPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/");
    }

    // selectWarehouses
    public void selectWarehouses() {
        this.warehousesButton.click();
    }

    // warehousesButtonHighlighted
    public boolean warehousesButtonHighlighted() {
        return this.warehousesButton.getAttribute("class").contains("active");
    }

    // onWarehousesPage
    public boolean onWarehousesPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses");
    }

    // selectItems
    public void selectItems() {
        this.itemsButton.click();
    }

    // itemsButtonHighlighted
    public boolean itemsButtonHighlighted() {
        return this.itemsButton.getAttribute("class").contains("active");
    }

    // onItemsPage
    public boolean onItemsPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/items");
    }

    // selectAddWarehouse   
    public void selectAddWarehouse() {
        this.addWarehouseButton.click();
    }

    // addWarehouseButtonHighlighted
    public boolean addWarehouseButtonHighlighted() {
        return this.addWarehouseButton.getAttribute("class").contains("active");
    }

    // onAddWarehousePage
    public boolean onAddWarehousePage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/add-warehouse");
    }

    // selectAddItem
    public void selectAddItem() {
        this.addItemButton.click();
    }

    // addItemButtonHighlighted
    public boolean addItemButtonHighlighted() {
        return this.addItemButton.getAttribute("class").contains("active");
    }

    // onAddItemPage
    public boolean onAddItemPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/add-item");
    }
}
