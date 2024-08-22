package com.skillstorm.selenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddWarehousePage {
    
    private WebDriver driver;
    private static final String url = "http://inventoryman.s3-website-us-east-1.amazonaws.com/add-warehouse";

    @FindBy(xpath = "/html/body/div/div/div/form/div[1]/input")
    private WebElement warehouseName;

    @FindBy(xpath = "/html/body/div/div/div/form/div[2]/input")
    private WebElement warehouseCapacity;

    @FindBy(xpath = "/html/body/div/div/div/form/button")
    private WebElement submitButton;

    private int warehouseCard = 0;

    public AddWarehousePage(WebDriver driver) {
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

    // Enters a string into the warehouse name field
    public void enterWarehouseName(String warehouseName) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.warehouseName.sendKeys(warehouseName);
    }

    // Enters a string into the warehouse capacity field
    public void enterWarehouseCapacity(String warehouseCapacity) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.warehouseCapacity.sendKeys(warehouseCapacity);
    }

    // Clicks the submit button
    public void clickSubmitWarehouseButton() {
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

    // Using xpath /html/body/div/div/div/div/div[2]/div[<variable>]/div/h5 to find the warehouse name
    public boolean isWarehouseInWarehousesPage(String warehouseName) {
        warehouseCard = -1; // setting warehouseCard for error checking
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> warehouses = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            try {
                WebElement warehouse = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[" + i + "]/div/h5"));
                warehouses.add(warehouse);
                String warehouseText = warehouse.getText();
                values.add(warehouseText);
                if (warehouseText.equals(warehouseName)) {
                    warehouseCard = i; // save the index to warehouseCard
                    break; // breaks when found
                }
            } catch (Exception e) {
                break;
            }
        }
        System.out.println("Warehouse card number: " + warehouseCard); // Print the warehouseCard for debugging
        return values.contains(warehouseName);
    }

    // /html/body/div/div/div/div/div[2]/div[<variable>]/div/p is the xpath for the capacity, but the capacity is formatted as "0 / <capacity> cubic feet utilized"
    // use the warehouseCard to find the correct warehouse card
    public boolean isCapacityInWarehousesPage(String warehouseCapacity) {
        if (warehouseCard == -1) {
            return false; // warehouse not found
        }
    
        try {
            WebElement capacityElement = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[" + warehouseCard + "]/div/p"));
            String capacityText = capacityElement.getText();
            
            String[] parts = capacityText.split(" / ");
            if (parts.length > 1) {
                String[] capacityParts = parts[1].split(" ");
                if (capacityParts.length > 0) {
                    String extractedCapacity = capacityParts[0];
                    return extractedCapacity.equals(warehouseCapacity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return false;
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