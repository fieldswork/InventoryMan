package com.skillstorm.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.processing.Find;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.cs.A;

public class WarehousesPage {

    private WebDriver driver;
    private static final String url = "http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses";

    @FindBy(id = "sortCriteria")
    private WebElement selectName;

    // @FindBy(className = "progress-bar progress-bar-striped bg-primary")
    // private WebElement selectUtilization;



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

        } else {
            warehouses = driver.findElements(By.cssSelector("div.progress-bar.progress-bar-striped.bg-primary"));
            
            List<Integer> values = new ArrayList<>();
            for (WebElement w: warehouses) {
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

}
