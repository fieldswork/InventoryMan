package com.skillstorm.selenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ItemsPage {
    
    private WebDriver driver;
    private static final String url = "http://inventoryman.s3-website-us-east-1.amazonaws.com/items";

    int itemCard = 0;

    @FindBy(id = "sortCriteria")
    private WebElement selectName;

    // /html/body/div/div/div/form/div[5]/select for the warehouse dropdown
    @FindBy(xpath = "/html/body/div/div/div/form/div[5]/select")
    private WebElement warehouseDropdown;

    public ItemsPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigate to the Items page
     */
    public void get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.driver.get(url);
    }

    public void selectSortingOption(String sortingChoice) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        Select select = new Select(selectName);

        if (sortingChoice.equals("Name (Alphabetical)")) {
            select.selectByValue("name");
        } else {
            select.selectByValue("quantity");
        }
        
    }

    public boolean iswarehousesOrdered(String sortingOrder) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> warehouses = new ArrayList<>();

        if (sortingOrder.equals("alphabetical")) {
            warehouses = driver.findElements(By.tagName("h5"));
            List<String> values = new ArrayList<>();
            
            for (WebElement w: warehouses) {
                values.add(w.getText());
            }

            List<String> orderedValues = new ArrayList<>(values);
            Collections.sort(orderedValues);

            System.out.println(values);
            System.out.println(orderedValues);

            return orderedValues.equals(values);

        } else if (sortingOrder.equals("quantity")) {
            warehouses = driver.findElements(By.cssSelector("p:nth-child(3)"));
            
            List<Integer> values = new ArrayList<>();
            for (WebElement w: warehouses) {
                int util = 0;
                if (!"".equals((w.getText()))) {
                    String u = w.getText();
                    util = Integer.parseInt(u.replaceAll("Quantity: ", ""));
                }
                values.add(util);
            }

            List<Integer> orderedValues = new ArrayList<>(values);
            Collections.sort(orderedValues, Collections.reverseOrder());

            System.out.println(values);
            System.out.println(orderedValues);

            return orderedValues.equals(values);
        } 
        // else if (sortingOrder.equals("size")) {
        else {
            warehouses = driver.findElements(By.cssSelector("p:nth-child(4)"));

            List<Integer> values = new ArrayList<>();
            for (WebElement w: warehouses) {
                int util = 0;
                if (!"".equals((w.getText()))) {
                    String u = w.getText().replaceAll("Size: ", "");
                    util = Integer.parseInt(u.replaceAll(" cubic feet", ""));
                }
                values.add(util);
            }

            List<Integer> orderedValues = new ArrayList<>(values);
            Collections.sort(orderedValues);

            System.out.println(values);
            System.out.println(orderedValues);

            return orderedValues.equals(values);
        } 
        // else {
        //     warehouses = driver.findElements(By.cssSelector("p:nth-child(3)"));
            
        //     List<Integer> values = new ArrayList<>();
        //     for (WebElement w: warehouses) {
        //         int util = 0;
        //         if (!"".equals((w.getText()))) {
        //             String u = w.getText();
        //             util = Integer.parseInt(u.replaceAll("Quantity: ", ""));
        //         }
        //         values.add(util);
        //     }

        //     List<Integer> orderedValues = new ArrayList<>(values);
        //     Collections.sort(orderedValues, Collections.reverseOrder());

        //     System.out.println(values);
        //     System.out.println(orderedValues);

        //     return orderedValues.equals(values);
        // }

        // findItem 
    }

    // /html/body/div/div/div/div/div[3]/div[<variable>]/div/h5
    public boolean findItem(String itemName) {
        itemCard = -1; // setting itemCard for error checking
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> items = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            try {
                WebElement item = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[3]/div[" + i + "]/div/h5"));
                items.add(item);
                values.add(item.getText());
                if (item.getText().equals(itemName)) {
                    itemCard = i;
                    return true;
                }
            } catch (Exception e) {
                break;
            }
        }
        return false;
    }

    public void clickEditItemButton() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        WebElement editButton = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[3]/div[" + itemCard + "]/div/div/button[1]"));
        editButton.click();
    }

    // http://inventoryman.s3-website-us-east-1.amazonaws.com/edit-item/*
    public boolean amIOnEditItemPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().contains("http://inventoryman.s3-website-us-east-1.amazonaws.com/edit-item/");
    }

    // /html/body/div/div/div/form/div[1]/input for the item name
    public void enterNewItemName(String itemName) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        WebElement itemNameField = this.driver.findElement(By.xpath("/html/body/div/div/div/form/div[1]/input"));
        itemNameField.clear();
        itemNameField.sendKeys(itemName);
    }

    // /html/body/div/div/div/form/div[2]/input for the item description
    public void enterNewItemDescription(String itemDescription) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        WebElement itemDescriptionField = this.driver.findElement(By.xpath("/html/body/div/div/div/form/div[2]/input"));
        itemDescriptionField.clear();
        itemDescriptionField.sendKeys(itemDescription);
    }

    // /html/body/div/div/div/form/div[3]/input for the item quantity
    public void enterNewItemQuantity(String itemQuantity) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        WebElement itemQuantityField = this.driver.findElement(By.xpath("/html/body/div/div/div/form/div[3]/input"));
        itemQuantityField.clear();
        itemQuantityField.sendKeys(itemQuantity);
    }

    // /html/body/div/div/div/form/div[4]/input for the item size
    public void enterNewItemSize(String itemSize) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        WebElement itemSizeField = this.driver.findElement(By.xpath("/html/body/div/div/div/form/div[4]/input"));
        itemSizeField.clear();
        itemSizeField.sendKeys(itemSize);
    }

    // Click the dropdown and select a warehouse from the dropdown
    public void selectNewWarehouse(String warehouse) {
        try {
            warehouseDropdown.click();
    
            Select select = new Select(warehouseDropdown);
            select.selectByVisibleText(warehouse);
    
            System.out.println("Selected warehouse: " + warehouse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // /html/body/div/div/div/form/button for the submit button
    public void clickSubmitItemButton() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        WebElement submitButton = this.driver.findElement(By.xpath("/html/body/div/div/div/form/button"));
        submitButton.click();
    }

    // amIOnWarehousesPage
    public boolean amIOnWarehousesPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().contains("http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses");
    }
}
