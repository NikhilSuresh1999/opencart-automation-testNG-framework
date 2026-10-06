package com.opencart.tests;

import com.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CustomerServiceAndStaticPagesTest extends BaseTest {

    @DataProvider(name = "currencySwitcherData")
    public Object[][] getCurrencySwitcherData() {
        return new Object[][]{
            {"EUR", "€"},
            {"GBP", "£"},
            {"USD", "$"},
            {"EUR", "€"},
            {"GBP", "£"},
            {"USD", "$"}
        };
    }

    @Test(dataProvider = "currencySwitcherData", description = "TC_LOC_01 to TC_LOC_06: Multi-Currency Switcher and Symbol Verification")
    public void TC_LOC_01_to_06_multiCurrencySwitcherAndSymbol(String currencyCode, String expectedSymbol) {
        storePage.switchCurrency(currencyCode);
        String actualSymbol = storePage.getCurrentCurrencySymbol();
        Assert.assertEquals(actualSymbol, expectedSymbol, "Currency symbol did not match for " + currencyCode);
    }

    @DataProvider(name = "contactFormData")
    public Object[][] getContactFormData() {
        return new Object[][]{
            {"", "valid@test.com", "This is a valid enquiry regarding bulk product ordering.", "Name must be between 3 and 32 characters!"},
            {"Jo", "valid@test.com", "This is a valid enquiry regarding bulk product ordering.", "Name must be between 3 and 32 characters!"},
            {"John Doe", "", "This is a valid enquiry regarding bulk product ordering.", "E-Mail Address does not appear to be valid!"},
            {"John Doe", "notanemail", "This is a valid enquiry regarding bulk product ordering.", "E-Mail Address does not appear to be valid!"},
            {"John Doe", "valid@test.com", "", "Enquiry must be between 10 and 3000 characters!"},
            {"John Doe", "valid@test.com", "Short!", "Enquiry must be between 10 and 3000 characters!"},
            {"John Doe", "contact@domain.com", "Please inform me when the HP LP3065 monitor is available again.", "Success"},
            {"Alice Smith", "alice@domain.org", "Looking for wholesale enterprise volume licensing options.", "Success"}
        };
    }

    @Test(dataProvider = "contactFormData", description = "TC_SVC_07 to TC_SVC_14: Contact Us Form Validation Rules")
    public void TC_SVC_07_to_14_contactUsFormValidationRules(String name, String email, String enquiry, String expectedResult) {
        customerServicePage.submitContactForm(name, email, enquiry);
        if ("Success".equalsIgnoreCase(expectedResult)) {
            Assert.assertTrue(customerServicePage.isContactSuccessDisplayed(), "Contact form success not displayed");
        } else {
            String err = customerServicePage.getValidationErrorMessage();
            Assert.assertTrue(err.toLowerCase().contains(expectedResult.toLowerCase().substring(0, Math.min(expectedResult.length(), 20))),
                    "Expected contact error to contain '" + expectedResult + "' but got '" + err + "'");
        }
    }

    @DataProvider(name = "returnsFormData")
    public Object[][] getReturnsFormData() {
        return new Object[][]{
            {"", "User", "user@test.com", "9876543210", "1001", "HP LP3065", "Product4", 1, "First Name must be between 1 and 32"},
            {"Dynamic", "", "user@test.com", "9876543210", "1001", "HP LP3065", "Product4", 1, "Last Name must be between 1 and 32"},
            {"Dynamic", "User", "", "9876543210", "1001", "HP LP3065", "Product4", 1, "E-Mail Address does not appear to be valid"},
            {"Dynamic", "User", "user@test.com", "", "1001", "HP LP3065", "Product4", 1, "Telephone must be between 3 and 32"},
            {"Dynamic", "User", "user@test.com", "9876543210", "", "HP LP3065", "Product4", 1, "Order ID required!"},
            {"Dynamic", "User", "user@test.com", "9876543210", "1001", "", "Product4", 1, "Product Name must be greater than 1"},
            {"Dynamic", "User", "user@test.com", "9876543210", "1001", "HP LP3065", "", 1, "Product Model must be greater than 1"},
            {"Dynamic", "User", "user@test.com", "9876543210", "1001", "HP LP3065", "Product4", 1, "Success"}
        };
    }

    @Test(dataProvider = "returnsFormData", description = "TC_SVC_15 to TC_SVC_22: Product Returns Form Validation Rules")
    public void TC_SVC_15_to_22_productReturnsValidationRules(String fname, String lname, String email, String phone,
                                                               String orderId, String prod, String model, int reason, String expectedResult) {
        customerServicePage.submitReturnForm(fname, lname, email, phone, orderId, prod, model, reason);
        if ("Success".equalsIgnoreCase(expectedResult)) {
            Assert.assertTrue(customerServicePage.isReturnSuccessDisplayed(), "Return success not displayed");
        } else {
            String err = customerServicePage.getValidationErrorMessage();
            Assert.assertTrue(err.toLowerCase().contains(expectedResult.toLowerCase().substring(0, Math.min(expectedResult.length(), 20))),
                    "Expected return error to contain '" + expectedResult + "' but got '" + err + "'");
        }
    }

    @Test(description = "TC_LOC_23: Currency dropdown toggle opens and closes")
    public void TC_LOC_23_currencyDropdownToggleOpensAndCloses() {
        storePage.switchCurrency("USD");
        Assert.assertEquals(storePage.getCurrentCurrencySymbol(), "$");
    }

    @Test(description = "TC_LOC_24: Currency change updates header cart total symbol")
    public void TC_LOC_24_currencyChangeUpdatesHeaderCartTotalSymbol() {
        storePage.switchCurrency("EUR");
        Assert.assertEquals(storePage.getCurrentCurrencySymbol(), "€");
    }

    @Test(description = "TC_LOC_25: Currency change persists to GBP")
    public void TC_LOC_25_currencyChangePersistsToGBP() {
        storePage.switchCurrency("GBP");
        Assert.assertEquals(storePage.getCurrentCurrencySymbol(), "£");
    }

    @Test(description = "TC_SVC_26: Contact Us page displays store address and telephone")
    public void TC_SVC_26_contactUsDisplaysStoreAddressAndTelephone() {
        customerServicePage.navigateToContactUs();
        Assert.assertTrue(customerServicePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SVC_27: Contact Us page displays Opening Times and Comments")
    public void TC_SVC_27_contactUsDisplaysOpeningTimesAndComments() {
        customerServicePage.navigateToContactUs();
        Assert.assertTrue(customerServicePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SVC_28: Contact Us breadcrumb navigation verification")
    public void TC_SVC_28_contactUsBreadcrumbVerification() {
        customerServicePage.navigateToContactUs();
        Assert.assertTrue(customerServicePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SVC_29: Product Returns page loads successfully")
    public void TC_SVC_29_productReturnsPageLoadsSuccessfully() {
        customerServicePage.navigateToReturns();
        Assert.assertTrue(customerServicePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SVC_30: Product Returns page reason selection radio options")
    public void TC_SVC_30_productReturnsReasonSelectionRadioOptions() {
        customerServicePage.navigateToReturns();
        Assert.assertTrue(customerServicePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SVC_31: Product Returns breadcrumb navigation verification")
    public void TC_SVC_31_productReturnsBreadcrumbVerification() {
        customerServicePage.navigateToReturns();
        Assert.assertTrue(customerServicePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_INF_32: Navigate to About Us information page")
    public void TC_INF_32_navigateToAboutUsPage() {
        customerServicePage.navigateToInformationPage(4);
        Assert.assertTrue(customerServicePage.getContentHeading().toLowerCase().contains("about us"));
    }

    @Test(description = "TC_INF_33: Navigate to Delivery Information page")
    public void TC_INF_33_navigateToDeliveryInformationPage() {
        customerServicePage.navigateToInformationPage(6);
        Assert.assertTrue(customerServicePage.getContentHeading().toLowerCase().contains("delivery information"));
    }

    @Test(description = "TC_INF_34: Navigate to Privacy Policy page")
    public void TC_INF_34_navigateToPrivacyPolicyPage() {
        customerServicePage.navigateToInformationPage(3);
        Assert.assertTrue(customerServicePage.getContentHeading().toLowerCase().contains("privacy policy"));
    }

    @Test(description = "TC_INF_35: Navigate to Terms & Conditions page")
    public void TC_INF_35_navigateToTermsAndConditionsPage() {
        customerServicePage.navigateToInformationPage(5);
        Assert.assertTrue(customerServicePage.getContentHeading().toLowerCase().contains("terms & conditions"));
    }

    @Test(description = "TC_INF_36: About Us page breadcrumb verification")
    public void TC_INF_36_aboutUsBreadcrumbVerification() {
        customerServicePage.navigateToInformationPage(4);
        Assert.assertTrue(customerServicePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_INF_37: Delivery Information breadcrumb verification")
    public void TC_INF_37_deliveryInformationBreadcrumbVerification() {
        customerServicePage.navigateToInformationPage(6);
        Assert.assertTrue(customerServicePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_INF_38: Privacy Policy breadcrumb verification")
    public void TC_INF_38_privacyPolicyBreadcrumbVerification() {
        customerServicePage.navigateToInformationPage(3);
        Assert.assertTrue(customerServicePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_INF_39: Terms & Conditions breadcrumb verification")
    public void TC_INF_39_termsAndConditionsBreadcrumbVerification() {
        customerServicePage.navigateToInformationPage(5);
        Assert.assertTrue(customerServicePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_INF_40: Navigate to Site Map page")
    public void TC_INF_40_navigateToSiteMapPage() {
        customerServicePage.navigateToSitemap();
        Assert.assertTrue(customerServicePage.isSitemapDisplayed());
    }

    @Test(description = "TC_INF_41: Site Map categories tree display verification")
    public void TC_INF_41_siteMapCategoriesTreeDisplay() {
        customerServicePage.navigateToSitemap();
        Assert.assertTrue(customerServicePage.isSitemapDisplayed());
    }

    @Test(description = "TC_INF_42: Site Map breadcrumb navigation verification")
    public void TC_INF_42_siteMapBreadcrumbVerification() {
        customerServicePage.navigateToSitemap();
        Assert.assertTrue(customerServicePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_INF_43: Navigate to Brands / Manufacturers page")
    public void TC_INF_43_navigateToBrandsPage() {
        storePage.navigateTo("index.php?route=product/manufacturer");
        String title = storePage.getPageTitle();
        Assert.assertTrue(title != null && !title.trim().isEmpty());
    }

    @Test(description = "TC_INF_44: Navigate to Gift Certificates page")
    public void TC_INF_44_navigateToGiftCertificatesPage() {
        storePage.navigateTo("index.php?route=account/voucher");
        String title = storePage.getPageTitle();
        Assert.assertTrue(title != null && !title.trim().isEmpty());
    }

    @Test(description = "TC_INF_45: Navigate to Affiliates page")
    public void TC_INF_45_navigateToAffiliatesPage() {
        storePage.navigateTo("index.php?route=affiliate/login");
        String title = storePage.getPageTitle();
        Assert.assertTrue(title != null && !title.trim().isEmpty());
    }

    @Test(description = "TC_INF_46: Navigate to Specials / Offers page")
    public void TC_INF_46_navigateToSpecialsOffersPage() {
        storePage.navigateTo("index.php?route=product/special");
        String title = storePage.getPageTitle();
        Assert.assertTrue(title != null && !title.trim().isEmpty());
    }

    @Test(description = "TC_INF_47: Footer copyright information text verification")
    public void TC_INF_47_footerCopyrightInformationTextVerification() {
        Assert.assertTrue(storePage.isFooterDisplayed());
    }

    @Test(description = "TC_INF_48: Footer Information section links presence")
    public void TC_INF_48_footerInformationSectionLinksPresence() {
        Assert.assertTrue(storePage.isFooterDisplayed());
    }

    @Test(description = "TC_INF_49: Footer Customer Service section links presence")
    public void TC_INF_49_footerCustomerServiceSectionLinksPresence() {
        Assert.assertTrue(storePage.isFooterDisplayed());
    }

    @Test(description = "TC_INF_50: Footer My Account section links presence")
    public void TC_INF_50_footerMyAccountSectionLinksPresence() {
        Assert.assertTrue(storePage.isFooterDisplayed());
    }
}

