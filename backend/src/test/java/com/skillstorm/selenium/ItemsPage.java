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

    public boolean iswarehousesOrdered(String sortingOrder) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> iElements = new ArrayList<>();

        if (sortingOrder.equals("alphabetical")) {
            List<String> values = allItems();

            List<String> orderedValues = new ArrayList<>(values);
            Collections.sort(orderedValues);

            System.out.println(values);
            System.out.println(orderedValues);

            return orderedValues.equals(values);

        } else if (sortingOrder.equals("quantity")) {
            
            List<Integer> values = allQts();

            List<Integer> orderedValues = new ArrayList<>(values);
            Collections.sort(orderedValues, Collections.reverseOrder());

            System.out.println(values);
            System.out.println(orderedValues);

            return orderedValues.equals(values);
        } else {
        //   else if (sortingOrder.equals("size")) {
  
            List<Integer> values = allSizes();

            List<Integer> orderedValues = new ArrayList<>(values);
            Collections.sort(orderedValues, Collections.reverseOrder());

            System.out.println(values);
            System.out.println(orderedValues);

            return orderedValues.equals(values);
        } 
        // else {
        //     Map<String, Integer> itemsTotalSizes = itemsTotalSizes();

        //     // List<Integer> orderedTotalSizes = new ArrayList<>(itemsTotalSizes.values());

        //     // Collections.sort(orderedValues, Collections.reverseOrder());

        //     System.out.println(itemsTotalSizes);
        //     // System.out.println(orderedValues);

        //     return true;
        //     // orderedValues.equals(values);
        // }
    }

    public List<String> allItems () {
        List<WebElement> iElements = new ArrayList<>();
        List<String> values = new ArrayList<>();

        iElements = driver.findElements(By.tagName("h5"));
        
        for (WebElement w: iElements) {
            values.add(w.getText());
        }
        return values;
    }

    public List<Integer> allQts () {
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

    public List<Integer> allSizes () {
        List<WebElement> iElements = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        iElements = driver.findElements(By.cssSelector("p:nth-child(4)"));
        System.out.println(iElements);

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

    // public Map<String, Integer> itemsTotalSizes() {
    //     Map<String, Integer> itemsTotalSizes = new HashMap<>();

    //     Iterator<String> items = allItems().iterator();
    //     Iterator<Integer> qts = allQts().iterator();
    //     Iterator<Integer> sizes = allSizes().iterator();

    //     System.out.println(allItems());
    //     System.out.println(allQts());
    //     System.out.println(allSizes());

    //     while (items.hasNext()) {
    //         int totalSize = qts.next() * sizes.next();
    //         itemsTotalSizes.put(items.next(), totalSize);
    //     }
    //     return itemsTotalSizes;
    // }
}
