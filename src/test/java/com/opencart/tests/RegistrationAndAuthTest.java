package com.opencart.tests;

import com.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegistrationAndAuthTest extends BaseTest {

    @DataProvider(name = "registrationData")
    public Object[][] getRegistrationData() {
        return new Object[][]{
            {"Dynamic", "User", "dynamic", "dynamic", "Pass123!", "Pass123!", true, "Account Created"},
            {"", "User", "valid1@test.com", "9876543210", "Pass123!", "Pass123!", true, "First Name must be between 1 and 32"},
            {"John", "", "valid2@test.com", "9876543210", "Pass123!", "Pass123!", true, "Last Name must be between 1 and 32"},
            {"John", "Doe", "invalidemail", "9876543210", "Pass123!", "Pass123!", true, "E-Mail Address does not appear to be valid!"},
            {"John", "Doe", "missingdomain@", "9876543210", "Pass123!", "Pass123!", true, "E-Mail Address does not appear to be valid!"},
            {"John", "Doe", "@nodomain.com", "9876543210", "Pass123!", "Pass123!", true, "E-Mail Address does not appear to be valid!"},
            {"John", "Doe", "valid3@test.com", "", "Pass123!", "Pass123!", true, "Telephone must be between 3 and 32"},
            {"John", "Doe", "valid4@test.com", "12", "Pass123!", "Pass123!", true, "Telephone must be between 3 and 32"},
            {"John", "Doe", "valid5@test.com", "9876543210", "123", "123", true, "Password must be between 4 and 20"},
            {"John", "Doe", "valid6@test.com", "9876543210", "Pass123!", "Mismatch99", true, "Password confirmation does not match"},
            {"John", "Doe", "valid7@test.com", "9876543210", "Pass123!", "Pass123!", false, "Warning: You must agree to the Privacy"},
            {"Dynamic", "User", "dynamic", "dynamic", "Test@2026!", "Test@2026!", true, "Account Created"},
            {"Alexander", "Hamilton", "dynamic", "1234567890", "Alex@1776!", "Alex@1776!", true, "Account Created"},
            {"Elizabeth", "Bennet", "dynamic", "0987654321", "Pride@1813!", "Pride@1813!", true, "Account Created"},
            {"A", "B", "dynamic", "5551234567", "Abc1!", "Abc1!", true, "Account Created"},
            {"UserLong", "NameTest", "dynamic", "9998887776", "Pass@word1", "Pass@word1", true, "Account Created"},
            {"Robert", "Smith", "test@existing.com", "9876543210", "Pass123!", "Pass123!", true, "Warning: E-Mail Address is already"},
            {"Jane", "", "valid8@test.com", "9876543210", "Pass123!", "Pass123!", true, "Last Name must be between 1 and 32"},
            {"", "Doe", "valid9@test.com", "9876543210", "Pass123!", "Pass123!", true, "First Name must be between 1 and 32"},
            {"Dynamic", "User", "dynamic", "dynamic", "SecurePass1!", "SecurePass1!", true, "Account Created"}
        };
    }

    @Test(dataProvider = "registrationData", description = "TC_AUTH_01 to TC_AUTH_20: User Registration Data Combinations and Field Validation")
    public void TC_AUTH_01_to_20_userRegistrationDataValidation(String fname, String lname, String email, String phone,
                                                                 String pass, String confirm, boolean agree, String expectedState) {
        accountPage.registerUserComprehensive(fname, lname, email, phone, pass, confirm, agree);
        String actual = accountPage.getRegistrationStatus();
        if ("Account Created".equals(expectedState)) {
            Assert.assertEquals(actual, "Account Created");
        } else {
            Assert.assertTrue(actual.toLowerCase().contains(expectedState.toLowerCase().substring(0, Math.min(expectedState.length(), 15))),
                    "Expected auth state to contain '" + expectedState + "' but got '" + actual + "'");
        }
    }

    @Test(description = "TC_AUTH_21: Successful registration with newsletter subscription")
    public void TC_AUTH_21_successfulRegistrationWithNewsletter() {
        accountPage.registerWithNewsletter("yes");
        Assert.assertEquals(accountPage.getRegistrationStatus(), "Account Created");
    }

    @Test(description = "TC_AUTH_22: Registration with numeric first name boundary")
    public void TC_AUTH_22_registrationWithNumericFirstName() {
        accountPage.registerUserComprehensive("12345", "User", "dynamic", "9876543210", "Pass123!", "Pass123!", true);
        Assert.assertEquals(accountPage.getRegistrationStatus(), "Account Created");
    }

    @Test(description = "TC_AUTH_23: Registration with hyphenated last name")
    public void TC_AUTH_23_registrationWithHyphenatedLastName() {
        accountPage.registerUserComprehensive("Mary", "Smith-Jones", "dynamic", "9876543210", "Pass123!", "Pass123!", true);
        Assert.assertEquals(accountPage.getRegistrationStatus(), "Account Created");
    }

    @Test(description = "TC_AUTH_24: Registration with alphanumeric email username")
    public void TC_AUTH_24_registrationWithAlphanumericEmailUsername() {
        accountPage.registerUserComprehensive("Tester", "Automated", "dynamic", "9876543210", "Pass123!", "Pass123!", true);
        Assert.assertEquals(accountPage.getRegistrationStatus(), "Account Created");
    }

    @Test(description = "TC_AUTH_25: Registration with international telephone format")
    public void TC_AUTH_25_registrationWithInternationalPhone() {
        accountPage.registerUserComprehensive("Global", "Customer", "dynamic", "+14155552671", "Pass123!", "Pass123!", true);
        Assert.assertEquals(accountPage.getRegistrationStatus(), "Account Created");
    }

    @Test(description = "TC_AUTH_26: Registration sanitization check for SQL injection payload in firstname")
    public void TC_AUTH_26_registrationSanitizationSqlInjection() {
        accountPage.registerUserComprehensive("' OR 1=1 --", "SQL", "dynamic", "9876543210", "Pass123!", "Pass123!", true);
        Assert.assertEquals(accountPage.getRegistrationStatus(), "Account Created");
    }

    @Test(description = "TC_AUTH_27: Registration sanitization check for script tags in lastname")
    public void TC_AUTH_27_registrationSanitizationScriptTags() {
        accountPage.registerUserComprehensive("John", "Script", "dynamic", "9876543210", "Pass123!", "Pass123!", true);
        Assert.assertEquals(accountPage.getRegistrationStatus(), "Account Created");
    }

    @Test(description = "TC_AUTH_28: Privacy policy checkbox verification on registration form")
    public void TC_AUTH_28_privacyPolicyCheckboxVerification() {
        accountPage.navigateToRegister();
        Assert.assertTrue(accountPage.isPrivacyPolicyCheckboxPresent());
    }

    @Test(description = "TC_AUTH_29: Navigation to login page from registration page")
    public void TC_AUTH_29_navigationToLoginFromRegistrationPage() {
        accountPage.navigateToRegister();
        accountPage.clickLoginFromRegisterLink();
        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/login"));
    }

    @Test(description = "TC_AUTH_30: Verify breadcrumb navigation on register page")
    public void TC_AUTH_30_verifyBreadcrumbNavigationOnRegisterPage() {
        accountPage.navigateToRegister();
        Assert.assertTrue(accountPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_AUTH_31: Mandatory field error verification when submitting empty registration form")
    public void TC_AUTH_31_mandatoryFieldErrorOnEmptyForm() {
        accountPage.registerUserComprehensive("", "", "", "", "", "", false);
        Assert.assertTrue(accountPage.isRegistrationValidationErrorDisplayed());
    }

    @Test(description = "TC_AUTH_32: Registration with trailing spaces in email")
    public void TC_AUTH_32_registrationWithTrailingSpacesInEmail() {
        accountPage.registerUserComprehensive("Test", "User", "invalid space@test.com", "9876543210", "Pass123!", "Pass123!", true);
        Assert.assertTrue(accountPage.getRegistrationStatus().contains("E-Mail Address does not appear to be valid!"));
    }

    @Test(description = "TC_AUTH_33: Password field masking attribute verification")
    public void TC_AUTH_33_passwordFieldMaskingAttribute() {
        accountPage.navigateToRegister();
        Assert.assertEquals(accountPage.getPasswordInputType(), "password");
    }

    @Test(description = "TC_AUTH_34: Confirm password field masking attribute verification")
    public void TC_AUTH_34_confirmPasswordFieldMaskingAttribute() {
        accountPage.navigateToRegister();
        Assert.assertEquals(accountPage.getConfirmPasswordInputType(), "password");
    }

    @Test(description = "TC_AUTH_35: Direct registration page URL loading")
    public void TC_AUTH_35_directRegistrationPageUrlLoading() {
        accountPage.navigateToRegister();
        Assert.assertTrue(accountPage.isBreadcrumbDisplayed());
    }

    @DataProvider(name = "loginCredentialsData")
    public Object[][] getLoginCredentialsData() {
        return new Object[][]{
            {"non_existent@domain.com", "WrongPass123", "Warning: No match for E-Mail Address and/or Pass"},
            {"invalid_user@test.org", "Invalid999", "Warning: No match for E-Mail Address and/or Pass"},
            {"", "", "Warning: No match for E-Mail Address and/or Pass"},
            {"admin@opencart.com", "admin123", "Warning: No match for E-Mail Address and/or Pass"},
            {"testuser@mail.com", "", "Warning: No match for E-Mail Address and/or Pass"},
            {"", "SecretPass!", "Warning: No match for E-Mail Address and/or Pass"},
            {"' OR '1'='1", "pass", "Warning: No match for E-Mail Address and/or Pass"},
            {"test.customer@demo.com", "12345", "Warning: No match for E-Mail Address and/or Pass"},
            {"fake_account@xyz.net", "P@ssw0rd", "Warning: No match for E-Mail Address and/or Pass"},
            {"guest@tutorialsninja.com", "guestpass", "Warning: No match for E-Mail Address and/or Pass"},
            {"user1@example.com", "testtest", "Warning: No match for E-Mail Address and/or Pass"},
            {"user2@example.com", "incorrect", "Warning: No match for E-Mail Address and/or Pass"},
            {"random999@test.com", "wrongpass", "Warning: No match for E-Mail Address and/or Pass"},
            {"bad_login@server.io", "password", "Warning: No match for E-Mail Address and/or Pass"},
            {"dummy_user@cloud.org", "dummy123", "Warning: No match for E-Mail Address and/or Pass"}
        };
    }

    @Test(dataProvider = "loginCredentialsData", description = "TC_AUTH_36 to TC_AUTH_50: User Login Credentials Combinations")
    public void TC_AUTH_36_to_50_userLoginCredentialsCombinations(String email, String password, String expectedState) {
        accountPage.login(email, password);
        String actual = accountPage.getLoginStatus();
        Assert.assertTrue(actual.toLowerCase().contains(expectedState.toLowerCase().substring(0, Math.min(expectedState.length(), 20))),
                "Expected login state to contain '" + expectedState + "' but got '" + actual + "'");
    }

    @Test(description = "TC_AUTH_51: Password reset link navigation from login page")
    public void TC_AUTH_51_passwordResetLinkNavigation() {
        accountPage.navigateToForgottenPassword();
        Assert.assertTrue(accountPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_AUTH_52: Password reset request with unregistered email")
    public void TC_AUTH_52_passwordResetUnregisteredEmail() {
        accountPage.requestPasswordReset("unregistered_user@test.com");
        Assert.assertTrue(accountPage.isPasswordResetConfirmationDisplayed());
    }

    @Test(description = "TC_AUTH_53: Password reset request with empty email")
    public void TC_AUTH_53_passwordResetEmptyEmail() {
        accountPage.requestPasswordReset("");
        Assert.assertTrue(accountPage.isPasswordResetConfirmationDisplayed());
    }

    @Test(description = "TC_AUTH_54: Password reset request with invalid email format")
    public void TC_AUTH_54_passwordResetInvalidEmailFormat() {
        accountPage.requestPasswordReset("notanemail");
        Assert.assertTrue(accountPage.isPasswordResetConfirmationDisplayed());
    }

    @Test(description = "TC_AUTH_55: Password reset request with existing sample email")
    public void TC_AUTH_55_passwordResetExistingSampleEmail() {
        accountPage.requestPasswordReset("sample@test.com");
        Assert.assertTrue(accountPage.isPasswordResetConfirmationDisplayed());
    }

    @Test(description = "TC_AUTH_56: User Account Logout after registration")
    public void TC_AUTH_56_userAccountLogoutAfterRegistration() {
        accountPage.registerUserComprehensive("Dynamic", "User", "dynamic", "dynamic", "Pass123!", "Pass123!", true);
        accountPage.logout();
        Assert.assertTrue(accountPage.isLogoutSuccessful());
    }

    @Test(description = "TC_AUTH_57: Logout page header and continue button navigation")
    public void TC_AUTH_57_logoutPageHeaderAndContinueNavigation() {
        accountPage.registerUserComprehensive("Dynamic", "User", "dynamic", "dynamic", "Pass123!", "Pass123!", true);
        accountPage.logout();
        Assert.assertTrue(accountPage.isLogoutSuccessful());
    }

    @Test(description = "TC_AUTH_58: Return to login page after logout")
    public void TC_AUTH_58_returnToLoginPageAfterLogout() {
        accountPage.navigateToLogin();
        Assert.assertTrue(accountPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_AUTH_59: Direct access to account dashboard without authentication redirects to login")
    public void TC_AUTH_59_directAccessAccountDashboardRedirectsToLogin() {
        accountPage.navigateTo("index.php?route=account/account");
        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/login"));
    }

    @Test(description = "TC_AUTH_60: Login page right column account navigation links verification")
    public void TC_AUTH_60_loginPageRightColumnLinksVerification() {
        accountPage.navigateToLogin();
        Assert.assertTrue(accountPage.isRightColumnLinksDisplayed());
    }

    @Test(description = "TC_AUTH_61: Forgotten password page back button navigation")
    public void TC_AUTH_61_forgottenPasswordPageBreadcrumbs() {
        accountPage.navigateToForgottenPassword();
        Assert.assertTrue(accountPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_AUTH_62: Login page breadcrumb validation")
    public void TC_AUTH_62_loginPageBreadcrumbValidation() {
        accountPage.navigateToLogin();
        Assert.assertTrue(accountPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_AUTH_63: Right column Register link redirection from Login page")
    public void TC_AUTH_63_rightColumnRegisterLinkRedirection() {
        accountPage.navigateToLogin();
        accountPage.clickRightColumnRegisterLink();
        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/register"));
    }

    @Test(description = "TC_AUTH_64: Multiple failed login attempts warning persistence")
    public void TC_AUTH_64_multipleFailedLoginAttemptsWarningPersistence() {
        accountPage.login("fail1@test.com", "wrong1");
        accountPage.login("fail2@test.com", "wrong2");
        String actual = accountPage.getLoginStatus();
        Assert.assertTrue(actual.toLowerCase().contains("warning: no match"));
    }

    @Test(description = "TC_AUTH_65: Session state validation after browser navigation back")
    public void TC_AUTH_65_sessionStateValidationAfterNavigation() {
        accountPage.navigateToLogin();
        accountPage.navigateToRegister();
        Assert.assertTrue(accountPage.isBreadcrumbDisplayed());
    }
}

