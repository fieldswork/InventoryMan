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
    }
}
