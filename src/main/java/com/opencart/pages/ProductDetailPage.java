package com.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailPage extends BasePage {

    private final By productTitle = By.xpath("//div[@id='content']//h1");
    private final By descriptionTab = By.xpath("//a[normalize-space()='Description']");
    private final By descriptionContent = By.id("tab-description");
    private final By specificationTab = By.xpath("//a[contains(normalize-space(),'Specification')]");
    private final By specificationContent = By.id("tab-specification");
    private final By reviewsTab = By.xpath("//a[contains(normalize-space(),'Reviews')]");
    private final By reviewsContent = By.id("tab-review");
    
    // Reviews Form
    private final By reviewAuthorInput = By.id("input-name");
    private final By reviewTextInput = By.id("input-review");
    private final By submitReviewBtn = By.id("button-review");
    private final By reviewAlert = By.cssSelector("#tab-review .alert, #form-review .alert, .alert-dismissible");

    // Product actions
    private final By quantityInput = By.id("input-quantity");
    private final By addToCartBtn = By.id("button-cart");
    private final By addToWishListBtn = By.xpath("//button[@data-original-title='Add to Wish List']");
    private final By compareProductBtn = By.xpath("//button[@data-original-title='Compare this Product']");
    private final By alertSuccess = By.cssSelector(".alert-success");
    private final By priceHeader = By.xpath("//div[@id='content']//ul[contains(@class,'list-unstyled') and contains(.,'$')] | //div[@id='content']//h2");
    private final By stockStatus = By.xpath("//div[@id='content']//li[contains(text(), 'Availability:')]");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToProduct(int productId) {
        navigateTo("index.php?route=product/product&product_id=" + productId);
        ele.waitForAjaxToComplete();
    }

    public String getProductTitle() {
        return ele.getTextSafely(productTitle);
    }

    public void clickDescriptionTab() {
        ele.click(descriptionTab);
    }

    public boolean isDescriptionDisplayed() {
        return ele.isElementDisplayed(descriptionContent);
    }

    public void clickSpecificationTab() {
        ele.click(specificationTab);
    }

    public boolean isSpecificationDisplayed() {
        return ele.isElementDisplayed(specificationContent);
    }

    public void clickReviewsTab() {
        ele.click(reviewsTab);
    }

    public boolean isReviewsTabDisplayed() {
        return ele.isElementDisplayed(reviewsContent);
    }

    public void submitReview(String author, String reviewText, int rating) {
        clickReviewsTab();
        ele.type(reviewAuthorInput, author);
        ele.type(reviewTextInput, reviewText);
        if (rating >= 1 && rating <= 5) {
            ele.click(By.xpath("//input[@name='rating' and @value='" + rating + "']"));
        }
        ele.click(submitReviewBtn);
        ele.waitForAjaxToComplete();
        ele.isElementDisplayed(reviewAlert);
    }

    public String getReviewAlertText() {
        try {
            WebDriverWait dynamicWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement alert = dynamicWait.until(ExpectedConditions.visibilityOfElementLocated(reviewAlert));
            String text = alert.getText();
            if (text == null || text.trim().isEmpty()) {
                text = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText || arguments[0].textContent;", alert);
            }
            return text != null ? text.trim() : "";
        } catch (Exception e) {
            return ele.getTextSafely(reviewAlert);
        }
    }

    public String getQuantityValue() {
        return ele.getAttributeSafely(quantityInput, "value");
    }

    public void setQuantity(String qty) {
        try {
            WebElement el = driver.findElement(quantityInput);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; $(arguments[0]).trigger('change');", el, qty);
        } catch (Exception e) {
            ele.type(quantityInput, qty);
        }
    }

    public void addToCart() {
        ele.scrollToElement(addToCartBtn);
        ele.click(addToCartBtn);
        ele.isElementDisplayed(alertSuccess);
    }

    public void addToWishList() {
        ele.click(addToWishListBtn);
        ele.isElementDisplayed(alertSuccess);
    }

    public void addToCompare() {
        ele.click(compareProductBtn);
        ele.isElementDisplayed(alertSuccess);
    }

    public String getSuccessAlertText() {
        try {
            WebDriverWait dynamicWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement alert = dynamicWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert-success, .alert-info, .alert-dismissible, .alert")));
            String text = alert.getText();
            if (text == null || text.trim().isEmpty()) {
                text = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText || arguments[0].textContent;", alert);
            }
            return text != null ? text.trim() : "";
        } catch (Exception e) {
            return ele.getTextSafely(By.cssSelector(".alert, .alert-dismissible"));
        }
    }

    public boolean isPriceDisplayed() {
        return ele.isElementDisplayed(priceHeader);
    }

    public boolean isStockDisplayed() {
        return ele.isElementDisplayed(stockStatus);
    }
}

