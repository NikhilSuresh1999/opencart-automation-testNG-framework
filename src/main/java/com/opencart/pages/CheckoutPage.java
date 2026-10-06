package com.opencart.pages;

import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage extends BasePage {
    private final Faker faker = new Faker();

    // Checkout Steps
    private final By guestCheckoutRadio = By.xpath("//input[@value='guest']");
    private final By step1ContinueBtn = By.id("button-account");
    private final By fNameInput = By.id("input-payment-firstname");
    private final By lNameInput = By.id("input-payment-lastname");
    private final By emailInput = By.id("input-payment-email");
    private final By telephoneInput = By.id("input-payment-telephone");
    private final By addressInput = By.id("input-payment-address-1");
    private final By cityInput = By.id("input-payment-city");
    private final By postcodeInput = By.id("input-payment-postcode");
    private final By countryDropdown = By.id("input-payment-country");
    private final By zoneDropdown = By.id("input-payment-zone");
    private final By step2ContinueBtn = By.id("button-guest");
    private final By flatRateRadio = By.xpath("//input[contains(@value, 'flat')]");
    private final By step4ContinueBtn = By.id("button-shipping-method");
    private final By termsCheckbox = By.name("agree");
    private final By step5ContinueBtn = By.id("button-payment-method");
    private final By confirmOrderBtn = By.id("button-confirm");
    private final By successMessage = By.xpath("//h1[text()='Your order has been placed!']");
    private final By orderSuccessContinueBtn = By.xpath("//div[@id='content']//a[normalize-space()='Continue']");
    private final By cartTotalBtn = By.id("cart-total");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void setupCheckoutWithItem() {
        navigateTo("index.php?route=product/product&product_id=47");
        ele.scrollToElement(By.id("button-cart"));
        ele.click(By.id("button-cart"));
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(d -> {
                String txt = d.findElement(cartTotalBtn).getText();
                return txt != null && txt.contains("item(s)") && !txt.startsWith("0 item(s)");
            });
        } catch (Exception ignored) {
        }
        navigateTo("index.php?route=checkout/checkout");
        ele.waitForAjaxToComplete();
    }

    public void selectCheckoutType(String type) {
        ele.waitForAjaxToComplete();
        if ("Guest".equalsIgnoreCase(type)) {
            ele.click(guestCheckoutRadio);
        }
        ele.click(step1ContinueBtn);
    }

    public void enterBillingDetails(String country, String zone) {
        ele.waitForAjaxToComplete();
        ele.type(fNameInput, faker.name().firstName());
        ele.type(lNameInput, faker.name().lastName());
        ele.type(emailInput, faker.internet().emailAddress());
        ele.type(telephoneInput, faker.phoneNumber().subscriberNumber(10));
        ele.type(addressInput, faker.address().streetAddress());
        ele.type(cityInput, faker.address().city());
        ele.type(postcodeInput, faker.address().zipCode());

        if (country != null && !country.isEmpty()) {
            ele.selectByVisibleText(countryDropdown, country);
            ele.waitForOptionPresent(zoneDropdown, zone);
            ele.selectByVisibleText(zoneDropdown, zone);
        }

        ele.click(step2ContinueBtn);
    }

    public void submitEmptyBillingDetails() {
        ele.waitForAjaxToComplete();
        ele.click(step2ContinueBtn);
    }

    public boolean isAddressErrorDisplayed() {
        ele.waitForAjaxToComplete();
        return ele.isElementDisplayed(By.cssSelector("div.text-danger, .alert-danger"));
    }

    public void selectShippingMethod(String method) {
        ele.waitForAjaxToComplete();
        if (method != null && method.contains("Flat")) {
            ele.click(flatRateRadio);
        }
        ele.click(step4ContinueBtn);
    }

    public void confirmOrderAndPayment() {
        ele.waitForAjaxToComplete();
        ele.clickWithJS(termsCheckbox);
        ele.click(step5ContinueBtn);
        
        ele.waitForAjaxToComplete();
        ele.click(confirmOrderBtn);
    }

    public String getOrderStatus() {
        ele.waitForAjaxToComplete();
        return ele.getTextSafely(successMessage).equals("Your order has been placed!") ? "Order Placed" : "Address Error";
    }

    public boolean isOrderSuccessDisplayed() {
        ele.waitForAjaxToComplete();
        return ele.isElementDisplayed(successMessage);
    }

    public void clickOrderSuccessContinue() {
        ele.click(orderSuccessContinueBtn);
        ele.waitForAjaxToComplete();
    }
}

