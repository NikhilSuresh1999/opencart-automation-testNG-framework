package com.opencart.pages;

import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AccountPage extends BasePage {
    private final Faker faker = new Faker();

    // Registration Locators
    private final By fNameInput = By.id("input-firstname");
    private final By lNameInput = By.id("input-lastname");
    private final By emailInput = By.id("input-email");
    private final By telephoneInput = By.id("input-telephone");
    private final By passwordInput = By.id("input-password");
    private final By confirmPasswordInput = By.id("input-confirm");
    private final By newsletterYesRadio = By.xpath("//input[@name='newsletter' and @value='1']");
    private final By privacyPolicyCheckbox = By.name("agree");
    private final By registerContinueBtn = By.xpath("//input[@value='Continue']");
    private final By registerSuccessHeading = By.xpath("//h1[text()='Your Account Has Been Created!']");
    private final By loginLinkFromRegister = By.xpath("//div[@id='content']//a[normalize-space()='login page']");
    
    // Login Locators
    private final By loginEmailInput = By.id("input-email");
    private final By loginPasswordInput = By.id("input-password");
    private final By loginSubmitBtn = By.xpath("//input[@value='Login']");
    private final By forgottenPasswordLink = By.xpath("//div[@class='form-group']//a[contains(text(), 'Forgotten Password')]");
    private final By forgottenEmailInput = By.id("input-email");
    private final By forgottenContinueBtn = By.xpath("//input[@value='Continue']");
    private final By alertSuccess = By.cssSelector(".alert-success");
    private final By alertDanger = By.cssSelector(".alert-danger");
    private final By textDanger = By.cssSelector(".text-danger");
    private final By accountDashboardHeading = By.xpath("//h2[text()='My Account']");
    private final By logoutSuccessHeading = By.xpath("//h1[text()='Account Logout']");
    private final By rightColumnListGroup = By.cssSelector("#column-right .list-group");
    private final By rightColumnRegisterLink = By.xpath("//aside[@id='column-right']//a[normalize-space()='Register']");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToRegister() {
        navigateTo("index.php?route=account/register");
    }

    public void navigateToLogin() {
        navigateTo("index.php?route=account/login");
    }

    public void navigateToForgottenPassword() {
        navigateTo("index.php?route=account/forgotten");
    }

    public void registerUserComprehensive(String fName, String lName, String email, String phone, String pass, String confirmPass, boolean agreePolicy) {
        navigateToRegister();
        ele.disableFormHtml5Validation();

        String actualFname = "Dynamic".equalsIgnoreCase(fName) ? faker.name().firstName() : fName;
        String actualLname = "Dynamic".equalsIgnoreCase(lName) || "User".equalsIgnoreCase(lName) && "Dynamic".equalsIgnoreCase(fName)
                ? faker.name().lastName() : lName;
        String actualEmail = "dynamic".equalsIgnoreCase(email) ? faker.internet().emailAddress() : email;
        String actualPhone = "dynamic".equalsIgnoreCase(phone) ? faker.phoneNumber().subscriberNumber(10) : phone;

        ele.type(fNameInput, actualFname);
        ele.type(lNameInput, actualLname);
        ele.type(emailInput, actualEmail);
        ele.type(telephoneInput, actualPhone);
        ele.type(passwordInput, pass);
        ele.type(confirmPasswordInput, confirmPass);

        if (agreePolicy) {
            ele.clickWithJS(privacyPolicyCheckbox);
        }
        ele.click(registerContinueBtn);
        ele.waitForAjaxToComplete();

        // Edge case: if testing existing email and account got created on initial try, create once then attempt duplicate
        if (email.contains("existing") && ele.isElementDisplayed(registerSuccessHeading)) {
            logout();
            navigateToRegister();
            ele.disableFormHtml5Validation();
            ele.type(fNameInput, "Robert");
            ele.type(lNameInput, "Smith");
            ele.type(emailInput, email);
            ele.type(telephoneInput, "9876543210");
            ele.type(passwordInput, pass);
            ele.type(confirmPasswordInput, confirmPass);
            ele.clickWithJS(privacyPolicyCheckbox);
            ele.click(registerContinueBtn);
            ele.waitForAjaxToComplete();
        }
    }

    public void registerWithNewsletter(String newsletter) {
        navigateToRegister();
        ele.disableFormHtml5Validation();
        ele.type(fNameInput, faker.name().firstName());
        ele.type(lNameInput, faker.name().lastName());
        ele.type(emailInput, faker.internet().emailAddress());
        ele.type(telephoneInput, faker.phoneNumber().subscriberNumber(10));
        ele.type(passwordInput, "Pass123!");
        ele.type(confirmPasswordInput, "Pass123!");
        if ("yes".equalsIgnoreCase(newsletter)) {
            ele.clickWithJS(newsletterYesRadio);
        }
        ele.clickWithJS(privacyPolicyCheckbox);
        ele.click(registerContinueBtn);
        ele.waitForAjaxToComplete();
    }

    public void login(String email, String password) {
        navigateToLogin();
        ele.type(loginEmailInput, email);
        ele.type(loginPasswordInput, password);
        ele.click(loginSubmitBtn);
        ele.waitForAjaxToComplete();
    }

    public void logout() {
        clickLogoutFromHeader();
    }

    public void requestPasswordReset(String email) {
        navigateToForgottenPassword();
        ele.type(forgottenEmailInput, email);
        ele.click(forgottenContinueBtn);
        ele.waitForAjaxToComplete();
    }

    public String getRegistrationStatus() {
        if (ele.isElementDisplayed(registerSuccessHeading)) {
            return "Account Created";
        }
        StringBuilder sb = new StringBuilder();
        if (ele.isElementDisplayed(alertDanger)) {
            sb.append(ele.getTextSafely(alertDanger).trim()).append(" ");
        }
        List<WebElement> errors = driver.findElements(textDanger);
        for (WebElement el : errors) {
            try {
                if (el.isDisplayed()) {
                    sb.append(el.getText().trim()).append(" ");
                }
            } catch (Exception ignored) {
            }
        }
        String result = sb.toString().trim();
        return result.isEmpty() ? "Unknown Error" : result;
    }

    public String getLoginStatus() {
        if (ele.isElementDisplayed(accountDashboardHeading)) {
            return "Login Successful";
        }
        if (ele.isElementDisplayed(alertDanger)) {
            return ele.getTextSafely(alertDanger).trim();
        }
        return "Unknown State";
    }

    public boolean isLogoutSuccessful() {
        return ele.isElementDisplayed(logoutSuccessHeading);
    }

    public boolean isPasswordResetConfirmationDisplayed() {
        return ele.isElementDisplayed(alertSuccess) || ele.isElementDisplayed(alertDanger);
    }

    public boolean isPrivacyPolicyCheckboxPresent() {
        return ele.isElementDisplayed(privacyPolicyCheckbox);
    }

    public boolean isRegistrationValidationErrorDisplayed() {
        return ele.isElementDisplayed(textDanger) || ele.isElementDisplayed(alertDanger);
    }

    public String getPasswordInputType() {
        return ele.getAttributeSafely(passwordInput, "type");
    }

    public String getConfirmPasswordInputType() {
        return ele.getAttributeSafely(confirmPasswordInput, "type");
    }

    public void clickLoginFromRegisterLink() {
        ele.click(loginLinkFromRegister);
        ele.waitForAjaxToComplete();
    }

    public boolean isRightColumnLinksDisplayed() {
        return ele.isElementDisplayed(rightColumnListGroup);
    }

    public void clickRightColumnRegisterLink() {
        ele.click(rightColumnRegisterLink);
        ele.waitForAjaxToComplete();
    }
}

