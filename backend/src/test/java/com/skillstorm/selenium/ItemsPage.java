package com.skillstorm.selenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ItemsPage {
    
    private WebDriver driver;
    private static final String url = "http://inventoryman.s3-website-us-east-1.amazonaws.com/items";

    @FindBy(id = "sortCriteria")
    private WebElement selectName;

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
}
