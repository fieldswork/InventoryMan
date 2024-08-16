package com.skillstorm.cucumber;

import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.skillstorm.selenium.InventoryManPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateWarehouse {

    private WebDriver driver;
    private InventoryManPage imPage;

    @Before("@loading")
    public void before() {
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.imPage = new InventoryManPage(driver);
    }



    @After("@loading")
    public void tearDown() {
        if (driver != null) {
            this.driver.quit();
        }
    }
}