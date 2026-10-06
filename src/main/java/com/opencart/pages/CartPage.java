package com.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage extends BasePage {

    private final By firstRowQtyInput = By.xpath("//div[@id='content']//form//tbody/tr[1]//input[contains(@name,'quantity')]");
    private final By firstRowUpdateBtn = By.xpath("//div[@id='content']//form//tbody/tr[1]//button[@data-original-title='Update' or @type='submit']");
    private final By firstRowRemoveBtn = By.xpath("//div[@id='content']//form//tbody/tr[1]//button[@data-original-title='Remove' or contains(@onclick,'cart.remove')]");
    
    // Accordions
    private final By couponAccordion = By.xpath("//a[contains(text(),'Use Coupon Code')]");
    private final By couponInput = By.id("input-coupon");
    private final By couponApplyBtn = By.id("button-coupon");

    private final By voucherAccordion = By.xpath("//a[contains(text(),'Use Gift Certificate')]");
    private final By voucherInput = By.id("input-voucher");
    private final By voucherApplyBtn = By.id("button-voucher");

    private final By shippingAccordion = By.xpath("//a[contains(text(),'Estimate Shipping & Taxes')]");
    private final By shippingCountryDropdown = By.id("input-country");
    private final By shippingZoneDropdown = By.id("input-zone");
    private final By shippingPostcodeInput = By.id("input-postcode");
    private final By getQuotesBtn = By.id("button-quote");

    private final By alertSuccess = By.cssSelector(".alert-success");
    private final By alertDanger = By.cssSelector(".alert-danger");
    private final By emptyCartMsg = By.xpath("//div[@id='content']//p[contains(text(),'Your shopping cart is empty!')]");
    private final By emptyCartContinueBtn = By.xpath("//div[@id='content']//a[normalize-space()='Continue']");
    private final By checkoutBtn = By.xpath("//a[normalize-space()='Checkout']");
    private final By totalsTable = By.xpath("//div[@id='content']//table[contains(@class,'table-bordered')] | //div[@id='content']//div[contains(@class,'col-sm-offset-8')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToCart() {
        navigateTo("index.php?route=checkout/cart");
        ele.waitForAjaxToComplete();
    }

    public void updateFirstItemQuantity(String qty) {
        ele.type(firstRowQtyInput, qty);
        ele.click(firstRowUpdateBtn);
        ele.waitForAjaxToComplete();
        ele.isElementDisplayed(alertSuccess);
    }

    public void removeFirstItem() {
        ele.click(firstRowRemoveBtn);
        ele.waitForAjaxToComplete();
    }

    public void applyCoupon(String couponCode) {
        ele.click(couponAccordion);
        ele.type(couponInput, couponCode);
        ele.click(couponApplyBtn);
        ele.waitForAjaxToComplete();
        ele.isElementDisplayed(alertDanger);
    }

    public void applyVoucher(String voucherCode) {
        ele.click(voucherAccordion);
        ele.type(voucherInput, voucherCode);
        ele.click(voucherApplyBtn);
        ele.waitForAjaxToComplete();
        ele.isElementDisplayed(alertDanger);
    }

    public void estimateShipping(String country, String zone, String postcode) {
        ele.click(shippingAccordion);
        ele.selectByVisibleText(shippingCountryDropdown, country);
        ele.waitForOptionPresent(shippingZoneDropdown, zone);
        ele.selectByVisibleText(shippingZoneDropdown, zone);
        ele.type(shippingPostcodeInput, postcode);
        ele.click(getQuotesBtn);
        ele.waitForAjaxToComplete();
    }

    public String getAlertText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert-danger, .alert-warning, .alert-success, .alert-dismissible, .alert")));
            String text = alert.getText();
            if (text == null || text.trim().isEmpty()) {
                text = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText || arguments[0].textContent;", alert);
            }
            return text != null ? text.trim() : "";
        } catch (Exception e) {
            return ele.getTextSafely(By.cssSelector(".alert"));
        }
    }

    public boolean isCartEmpty() {
        return ele.isElementDisplayed(emptyCartMsg);
    }

    public void clickEmptyCartContinue() {
        ele.click(emptyCartContinueBtn);
        ele.waitForAjaxToComplete();
    }

    public void proceedToCheckout() {
        ele.click(checkoutBtn);
        ele.waitForAjaxToComplete();
    }

    public boolean isTotalsTableDisplayed() {
        return ele.isElementDisplayed(totalsTable);
    }

    public String getCouponInputValue() {
        return ele.getAttributeSafely(couponInput, "value");
    }

    public String getVoucherInputValue() {
        return ele.getAttributeSafely(voucherInput, "value");
    }
}

