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

    /**
     * Selects the sorting criteria 
     * @param sortingChoice
     */
    public void selectSortingOption(String sortingChoice) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        Select select = new Select(selectName);

        if (sortingChoice.equals("Name (Alphabetical)")) {

            select.selectByValue("name");

        } else if (sortingChoice.equals("Quantity")){

            select.selectByValue("quantity");

        } else if (sortingChoice.equals("Item Size")) {

            select.selectByValue("size");

        } else {

            select.selectByValue("totalSize");

        }
    }

    /**
     * Checks if items are sorted by sorting criteria
     * @param sortingOrder
     * @return true if items are sorted by the sorting criteria, otherwise false
     */
    public boolean iswarehousesOrdered(String sortingOrder) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if (sortingOrder.equals("alphabetical")) {
            List<String> values = allItems();

            List<String> orderedValues = new ArrayList<>(values);
            Collections.sort(orderedValues);

            return orderedValues.equals(values);

        } else {
            List<Integer> values;

            if (sortingOrder.equals("quantity")){
                values = allQts();
            } else if (sortingOrder.equals("size")) {
                values = allSizes();
            } else {
                values = itemsTotalSize();
            }
            
            List<Integer> orderedValues = new ArrayList<>(values);
            Collections.sort(orderedValues, Collections.reverseOrder());

            return orderedValues.equals(values);
        } 
    }

    /**
     * Gets the list of all items on the Items page
     * @return list of all items on the page
     */
    public List<String> allItems() {
        List<WebElement> iElements = new ArrayList<>();
        List<String> values = new ArrayList<>();

        iElements = driver.findElements(By.tagName("h5"));
        
        for (WebElement w: iElements) {
            values.add(w.getText());
        }
        return values;
    }

    /**
     * Gets the list of all items quantity
     * @return list of all items quantity on the page
     */
    public List<Integer> allQts() {
        List<WebElement> iElements = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        iElements = driver.findElements(By.cssSelector("p:nth-child(3)"));
        
        for (WebElement w: iElements) {
            int util = 0;
            if (!"".equals((w.getText()))) {
                String u = w.getText();
                util = Integer.parseInt(u.replaceAll("Quantity: ", ""));
            }
            values.add(util);
        }
        return values;
    }

    /**
     * Gets the list of all items size
     * @return list of all items size on the page
     */
    public List<Integer> allSizes() {
        List<WebElement> iElements = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        iElements = driver.findElements(By.cssSelector("p:nth-child(4)"));

        for (WebElement w: iElements) {
            int util = 0;
            if (!"".equals((w.getText()))) {
                String u = w.getText().replaceAll("Size: ", "");
                util = Integer.parseInt(u.replaceAll(" cubic feet", ""));
            }
            values.add(util);
        }
        return values;
    }

    /**
     * Gets the list of all items total size
     * @return list of all items total size on the page
     */
    public List<Integer> itemsTotalSize() {
        List<Integer> itemsTotalSizes = new ArrayList<>();

        List<String> items = allItems();
        List<Integer> qts = allQts();
        List<Integer> sizes = allSizes();

        for (int i = 0; i < items.size(); i++) {
            itemsTotalSizes.add(qts.get(i) * sizes.get(i));
        }
        return itemsTotalSizes;
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

    //clickDeleteItemButton
    public void clickDeleteItemButton() {
        if (itemCard == -1) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        try {
            WebElement deleteButton = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[3]/div[" + itemCard + "]/div/div/button[2]"));
            deleteButton.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Click the Ok dialog button from the browser alert
    public void clickOKButton() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.driver.switchTo().alert().accept();
    }
}
