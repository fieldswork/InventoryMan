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

    int warehouseCard = 0;

    // /html/body/div/div/div/form/div[1]/input for the warehouse name
    @FindBy(xpath = "/html/body/div/div/div/form/div[1]/input")
    private WebElement editWarehouseName;

    // /html/body/div/div/div/form/div[2]/input for the warehouse capacity
    @FindBy(xpath = "/html/body/div/div/div/form/div[2]/input")
    private WebElement editWarehouseCapacity;

    // /html/body/div/div/div/form/button for the Update button
    @FindBy(xpath = "/html/body/div/div/div/form/button")
    private WebElement updateButton;

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

    // findWarehouse - Find a warehouse by name
    // /html/body/div/div/div/div/div[2]/div[<variable>]/div/h5 for the warehouse name
    // Save the variable of the matching warehouse to warehouseCard for later use
    public boolean findWarehouse(String warehouseName) {
        warehouseCard = -1; // setting warehouseCard for error checking
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> warehouses = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            try {
                WebElement warehouse = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[" + i + "]/div/h5"));
                warehouses.add(warehouse);
                String warehouseText = warehouse.getText();
                values.add(warehouseText);
                if (warehouseText.equals(warehouseName)) {
                    warehouseCard = i; // save the index to warehouseCard
                    break; // breaks when found
                }
            } catch (Exception e) {
                break;
            }
        }
        System.out.println("Warehouse card number: " + warehouseCard);
        return values.contains(warehouseName);
    }

    // Using warehouseCard to select the edit button for clickEditButton
    // /html/body/div/div/div/div/div[2]/div[<variable>]/div/div[2]/button[1] for the edit button
    public void clickEditButton() {
        if (warehouseCard == -1) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        try {
            WebElement editButton = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[" + warehouseCard + "]/div/div[2]/button[1]"));
            editButton.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Check if the user is on the Edit Warehouse page
    public boolean amIOnEditWarehousePage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().contains("http://inventoryman.s3-website-us-east-1.amazonaws.com/edit-warehouse/");
    }

    // Clear and enter a string into the warehouse name field
    public void enterWarehouseName(String warehouseName) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.editWarehouseName.clear();
        this.editWarehouseName.sendKeys(warehouseName);
    }

    // Clear and enter a string into the warehouse capacity field
    public void enterWarehouseCapacity(String warehouseCapacity) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.editWarehouseCapacity.clear();
        this.editWarehouseCapacity.sendKeys(warehouseCapacity);
    }

    // Click the Update button to submit the warehouse
    public void clickSubmitWarehouseButton() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.updateButton.click();
    }

    // Check if the user is on the Warehouses page
    public boolean amIOnWarehousesPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses");
    }

    // clickDeleteButton - /html/body/div/div/div/div/div[2]/div[<warehouseCard>]/div/div[2]/button[2]
    public void clickDeleteButton() {
        if (warehouseCard == -1) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        try {
            WebElement deleteButton = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[" + warehouseCard + "]/div/div[2]/button[2]"));
            deleteButton.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // clickOKButton - Click the Ok dialog button from the browser alert
    public void clickOKButton() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.driver.switchTo().alert().accept();
    }
}
