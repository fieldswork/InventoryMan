package com.skillstorm.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class InventoryManPage {
    private WebDriver driver;

    public InventoryManPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void navigateTo() {
        driver.get("http://localhost:3000"); // will prob need to change later?
    }
}
