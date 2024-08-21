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


public class WarehousesPage {

    private WebDriver driver;
    private static final String url = "http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses";

    @FindBy(id = "sortCriteria")
    private WebElement selectName;

    public WarehousesPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigate to the Warehouses page
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
            select.selectByValue("utilization");
        }
        
    }

    public boolean iswarehousesOrdered(String sortingOrder) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if (sortingOrder.equals("alphabetical")) {
        
            List<String> values = allWarehouses();

            List<String> orderedValues = new ArrayList<>(values);
            Collections.sort(orderedValues);

            System.out.println(values);
            System.out.println(orderedValues);

            return orderedValues.equals(values);

        } else {
            List<WebElement> whElements = new ArrayList<>();
            whElements = driver.findElements(By.cssSelector("div.progress-bar.progress-bar-striped.bg-primary"));
            
            List<Integer> values = new ArrayList<>();
            for (WebElement w: whElements) {
                int util = 0;
                if (!"".equals((w.getText()))) {
                    String u = w.getText();
                    util = Integer.parseInt(u.replaceAll("%", ""));
                }
                values.add(util);
            }

            List<Integer> orderedValues = new ArrayList<>(values);
            Collections.sort(orderedValues, Collections.reverseOrder());

            System.out.println(values);
            System.out.println(orderedValues);

            return orderedValues.equals(values);
        }
    }

    public List<String> allWarehouses () {
        List<WebElement> whElements = new ArrayList<>();
        List<String> values = new ArrayList<>();

        whElements = driver.findElements(By.tagName("h5"));
        
        for (WebElement w: whElements) {
            values.add(w.getText());
        }
        return values;
    }

}
