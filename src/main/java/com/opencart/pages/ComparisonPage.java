package com.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ComparisonPage extends BasePage {

    private final By comparisonTable = By.xpath("//table[contains(@class,'table-bordered')]");
    private final By productNames = By.xpath("//table[contains(@class,'table-bordered')]//strong/a");
    private final By removeBtn = By.xpath("//a[contains(@href, 'remove=')]");
    private final By emptyComparisonMsg = By.xpath("//div[@id='content']//p[contains(text(),'You have not chosen any products to compare.')]");
    private final By alertSuccess = By.cssSelector(".alert-success");
    private final By emptyComparisonContinueBtn = By.xpath("//div[@id='content']//a[normalize-space()='Continue']");

    public ComparisonPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToComparison() {
        navigateTo("index.php?route=product/compare");
        ele.waitForAjaxToComplete();
    }

    public boolean isComparisonTableDisplayed() {
        return ele.isElementDisplayed(comparisonTable);
    }

    public String getFirstProductName() {
        return ele.getTextSafely(productNames);
    }

    public void removeProduct() {
        ele.click(removeBtn);
        ele.waitForAjaxToComplete();
    }

    public boolean isEmptyComparisonMessageDisplayed() {
        return ele.isElementDisplayed(emptyComparisonMsg) || !ele.isElementPresent(comparisonTable);
    }

    public void clickEmptyContinue() {
        ele.click(emptyComparisonContinueBtn);
        ele.waitForAjaxToComplete();
    }

    public String getAlertText() {
        return ele.getTextSafely(alertSuccess);
    }
}

