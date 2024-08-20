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

    // @Find({FindBy(tagName = "h5")})
    // private List<WebElement> warehouses;

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

    public void selectSortByName() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        Select select = new Select(selectName);
        select.selectByValue("name");
    }

    public boolean iswarehousesOrderedByName() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> warehouses = driver.findElements(By.tagName("h5"));
        List<String> values = new ArrayList<>();
        
        for(WebElement w: warehouses){
            values.add(w.getText());
        }
        List<String> orderedValues = new ArrayList<>(values);
        Collections.sort(orderedValues);

        System.out.println(values);
        return orderedValues.equals(values);
    }

}
