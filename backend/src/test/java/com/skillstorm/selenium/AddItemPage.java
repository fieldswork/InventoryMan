package com.skillstorm.selenium;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddItemPage {

    private WebDriver driver;
    private static final String url = "http://inventoryman.s3-website-us-east-1.amazonaws.com/add-item";

    // /html/body/div/div/div/form/div[1]/input for the item name 
    @FindBy(xpath = "/html/body/div/div/div/form/div[1]/input")
    private WebElement itemName;

    // /html/body/div/div/div/form/div[2]/input for the item description
    @FindBy(xpath = "/html/body/div/div/div/form/div[2]/input")
    private WebElement itemDescription;

    // /html/body/div/div/div/form/div[3]/input for the item quantity
    @FindBy(xpath = "/html/body/div/div/div/form/div[3]/input")
    private WebElement itemQuantity;

    // /html/body/div/div/div/form/div[4]/input for the item size 
    @FindBy(xpath = "/html/body/div/div/div/form/div[4]/input")
    private WebElement itemSize;

    // /html/body/div/div/div/form/div[5]/select for the warehouse dropdown
    @FindBy(xpath = "/html/body/div/div/div/form/div[5]/select")
    private WebElement warehouseDropdown;

    // /html/body/div/div/div/form/button for the submit button
    @FindBy(xpath = "/html/body/div/div/div/form/button")
    private WebElement submitButton;
    
    // //div[contains(text(),'Quantity and size must be at least 1.')] for alert message
    @FindBy(xpath = "//div[contains(text(),'Quantity and size must be at least 1.')]")
    private WebElement alertMessage;

    public AddItemPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        PageFactory.initElements(driver, this);
    }

    public void get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.driver.get(url);
    }

    // Enters a string into the item name field
    public void enterItemName(String itemName) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.itemName.sendKeys(itemName);
    }

    // Enters a string into the item description field
    public void enterItemDescription(String itemDescription) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.itemDescription.sendKeys(itemDescription);
    }

    // Enters a string into the item quantity field
    public void enterItemQuantity(String itemQuantity) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.itemQuantity.sendKeys(itemQuantity);
    }

    // Enters a string into the item size field
    public void enterItemSize(String itemSize) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.itemSize.sendKeys(itemSize);
    }

    // Click the dropdown and select a warehouse from the dropdown
    public void selectWarehouse(String warehouse) {
        try {
            warehouseDropdown.click();
    
            Select select = new Select(warehouseDropdown);
            select.selectByVisibleText(warehouse);
    
            System.out.println("Selected warehouse: " + warehouse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Clicks the submit button
    public void clickSubmitButton() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.submitButton.click();
    }

    // Checks if the user is redirected to the Warehouses page
    public boolean amIOnWarehousesPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses");
    }

    public boolean isInvalidDataAlertDisplayed(String alertMessage) {
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.alertMessage.getText().equals(alertMessage);
    }

}