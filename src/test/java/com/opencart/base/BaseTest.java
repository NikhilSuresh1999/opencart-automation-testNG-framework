package com.opencart.base;

import com.opencart.driver.DriverFactory;
import com.opencart.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({com.opencart.listeners.TestListener.class})
public class BaseTest {
    protected WebDriver driver;
    protected StoreFrontPage storePage;
    protected AccountPage accountPage;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;
    protected ProductDetailPage productPage;
    protected ComparisonPage comparisonPage;
    protected CustomerServicePage customerServicePage;
    protected AdminPage adminPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.initDriver();
        storePage = new StoreFrontPage(driver);
        accountPage = new AccountPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        productPage = new ProductDetailPage(driver);
        comparisonPage = new ComparisonPage(driver);
        customerServicePage = new CustomerServicePage(driver);
        adminPage = new AdminPage(driver);
        storePage.navigateToHome();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
}
