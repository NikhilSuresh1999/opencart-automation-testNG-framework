package com.opencart.pages;

import com.opencart.utils.ConfigReader;
import com.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected WebDriver driver;
    protected ElementUtil ele;
    protected String baseUrl;

    private final By breadcrumb = By.cssSelector(".breadcrumb");
    private final By footer = By.tagName("footer");
    private final By cartTotalBtn = By.id("cart-total");
    private final By myAccountDropdown = By.xpath("//a[@title='My Account']");
    private final By registerLink = By.xpath("//a[normalize-space()='Register']");
    private final By loginLink = By.xpath("//a[normalize-space()='Login']");
    private final By logoutLink = By.xpath("//a[normalize-space()='Logout']");
    private final By wishlistHeaderLink = By.id("wishlist-total");
    private final By cartHeaderLink = By.xpath("//a[@title='Shopping Cart']");
    private final By checkoutHeaderLink = By.xpath("//a[@title='Checkout']");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.ele = new ElementUtil(driver);
        this.baseUrl = ConfigReader.getProperty("storefront_url", "https://tutorialsninja.com/demo/");
    }

    public void navigateTo(String pathOrUrl) {
        if (pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://")) {
            driver.get(pathOrUrl);
        } else {
            String fullUrl = baseUrl.endsWith("/") ? baseUrl + pathOrUrl : baseUrl + "/" + pathOrUrl;
            driver.get(fullUrl);
        }
        ele.waitForAjaxToComplete();
    }

    public void navigateToHome() {
        driver.get(baseUrl);
        ele.waitForAjaxToComplete();
    }

    public boolean isBreadcrumbDisplayed() {
        return ele.isElementDisplayed(breadcrumb);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isFooterDisplayed() {
        return ele.isElementDisplayed(footer);
    }

    public String getCartBadge() {
        return ele.getTextSafely(cartTotalBtn);
    }

    public void openMyAccountDropdown() {
        ele.click(myAccountDropdown);
    }

    public void clickRegisterFromHeader() {
        openMyAccountDropdown();
        ele.click(registerLink);
        ele.waitForAjaxToComplete();
    }

    public void clickLoginFromHeader() {
        openMyAccountDropdown();
        ele.click(loginLink);
        ele.waitForAjaxToComplete();
    }

    public void clickLogoutFromHeader() {
        openMyAccountDropdown();
        ele.click(logoutLink);
        ele.waitForAjaxToComplete();
    }

    public void clickWishlistHeader() {
        ele.click(wishlistHeaderLink);
        ele.waitForAjaxToComplete();
    }

    public void clickCartHeader() {
        ele.click(cartHeaderLink);
        ele.waitForAjaxToComplete();
    }

    public void clickCheckoutHeader() {
        ele.click(checkoutHeaderLink);
        ele.waitForAjaxToComplete();
    }
}

