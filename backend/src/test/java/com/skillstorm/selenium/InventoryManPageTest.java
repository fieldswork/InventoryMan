package com.skillstorm.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryManPageTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Set up ChromeDriver
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
    }

    @Test
    public void testPageTitle() {
        // Navigate to the web page
        driver.get("http://localhost:3000");

        // Fetch the title
        String title = driver.getTitle();

        // Check if the title is "InventoryMan"
        assertEquals("InventoryMan", title, "Title should be InventoryMan");
    }

    @AfterEach
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
