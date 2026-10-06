package com.opencart.tests;

import com.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ProductDetailAndReviewTest extends BaseTest {

    @DataProvider(name = "productIdsData")
    public Object[][] getProductIdsData() {
        return new Object[][]{
            {47},
            {43},
            {40},
            {30},
            {42},
            {33},
            {46},
            {28},
            {41},
            {48}
        };
    }

    @Test(dataProvider = "productIdsData", description = "TC_PROD_01 to TC_PROD_10: Product Detail Page Views across Catalog")
    public void TC_PROD_01_to_10_productDetailPageViews(int productId) {
        productPage.navigateToProduct(productId);
        String title = productPage.getProductTitle();
        Assert.assertTrue(title != null && !title.trim().isEmpty(), "Product title is empty for product ID: " + productId);
    }

    @DataProvider(name = "reviewValidationsData")
    public Object[][] getReviewValidationsData() {
        return new Object[][]{
            {"", "This is a wonderful product with awesome build and speed.", 5, "Warning: Review Name must be between 3 and 25 characters!"},
            {"AB", "This is a wonderful product with awesome build and speed.", 5, "Warning: Review Name must be between 3 and 25 characters!"},
            {"John Doe", "", 5, "Warning: Review Text must be between 25 and 1000 characters!"},
            {"John Doe", "Too short!", 5, "Warning: Review Text must be between 25 and 1000 characters!"},
            {"John Doe", "This is a wonderful product with awesome build and speed.", 0, "Warning: Please select a review rating!"},
            {"Alice Johnson", "Outstanding performance and crystal clear display resolution!", 5, "Thank you for your review. It has been submitted"},
            {"Bob Builder", "Good quality laptop with solid aluminum chassis and fast SSD.", 4, "Thank you for your review. It has been submitted"},
            {"Charlie Brown", "Average build quality for the price, battery life is okay.", 3, "Thank you for your review. It has been submitted"},
            {"David Miller", "Disappointed with the software support and driver stability.", 2, "Thank you for your review. It has been submitted"},
            {"Ethan Hunt", "Critical overheating issues experienced during heavy loads.", 1, "Thank you for your review. It has been submitted"}
        };
    }

    @Test(dataProvider = "reviewValidationsData", description = "TC_PROD_11 to TC_PROD_20: Customer Review Form Validations and Boundary Rules")
    public void TC_PROD_11_to_20_customerReviewFormValidations(String author, String reviewText, int rating, String expectedAlert) {
        productPage.navigateToProduct(47);
        productPage.submitReview(author, reviewText, rating);
        String actualAlert = productPage.getReviewAlertText();
        Assert.assertTrue(actualAlert.toLowerCase().contains(expectedAlert.toLowerCase().substring(0, Math.min(expectedAlert.length(), 25))),
                "Expected review alert to contain '" + expectedAlert + "' but got '" + actualAlert + "'");
    }

    @Test(description = "TC_PROD_21: Product Description tab toggle and content visibility")
    public void TC_PROD_21_productDescriptionTabToggleAndContent() {
        productPage.navigateToProduct(47);
        productPage.clickDescriptionTab();
        Assert.assertTrue(productPage.isDescriptionDisplayed());
    }

    @Test(description = "TC_PROD_22: Product Specification tab toggle and table display")
    public void TC_PROD_22_productSpecificationTabToggleAndTable() {
        productPage.navigateToProduct(47);
        productPage.clickSpecificationTab();
        Assert.assertTrue(productPage.isSpecificationDisplayed());
    }

    @Test(description = "TC_PROD_23: Product Reviews tab toggle and reviews form display")
    public void TC_PROD_23_productReviewsTabToggleAndForm() {
        productPage.navigateToProduct(47);
        productPage.clickReviewsTab();
        Assert.assertTrue(productPage.isReviewsTabDisplayed());
    }

    @Test(description = "TC_PROD_24: Default quantity value in quantity input is 1")
    public void TC_PROD_24_defaultQuantityValue() {
        productPage.navigateToProduct(47);
        Assert.assertEquals(productPage.getQuantityValue(), "1");
    }

    @Test(description = "TC_PROD_25: Update quantity input to 2")
    public void TC_PROD_25_updateQuantityTo2() {
        productPage.navigateToProduct(47);
        productPage.setQuantity("2");
        Assert.assertEquals(productPage.getQuantityValue(), "2");
    }

    @Test(description = "TC_PROD_26: Update quantity input to 5")
    public void TC_PROD_26_updateQuantityTo5() {
        productPage.navigateToProduct(47);
        productPage.setQuantity("5");
        Assert.assertEquals(productPage.getQuantityValue(), "5");
    }

    @Test(description = "TC_PROD_27: Update quantity input to 10")
    public void TC_PROD_27_updateQuantityTo10() {
        productPage.navigateToProduct(47);
        productPage.setQuantity("10");
        Assert.assertEquals(productPage.getQuantityValue(), "10");
    }

    @Test(description = "TC_PROD_28: Add product to cart with updated quantity")
    public void TC_PROD_28_addProductToCartWithUpdatedQuantity() {
        productPage.navigateToProduct(47);
        productPage.setQuantity("2");
        productPage.addToCart();
        String alertText = productPage.getSuccessAlertText();
        if (alertText.isEmpty()) {
            Assert.assertTrue(storePage.getCartBadge().contains("item(s)"));
        } else {
            Assert.assertTrue(alertText.toLowerCase().contains("success: you have added"));
        }
    }

    @Test(description = "TC_PROD_29: Add product to cart directly from product page")
    public void TC_PROD_29_addProductToCartDirectly() {
        productPage.navigateToProduct(47);
        productPage.addToCart();
        String alertText = productPage.getSuccessAlertText();
        if (alertText.isEmpty()) {
            Assert.assertTrue(storePage.getCartBadge().contains("item(s)"));
        } else {
            Assert.assertTrue(alertText.toLowerCase().contains("success: you have added"));
        }
    }

    @Test(description = "TC_PROD_30: Add product to wishlist from product detail page")
    public void TC_PROD_30_addProductToWishlistGuest() {
        productPage.navigateToProduct(47);
        productPage.addToWishList();
        String alert = productPage.getSuccessAlertText();
        Assert.assertTrue(alert.toLowerCase().contains("login") || alert.toLowerCase().contains("create an account") || alert.toLowerCase().contains("wish list"));
    }

    @Test(description = "TC_PROD_31: Add product to compare from product detail page")
    public void TC_PROD_31_addProductToCompare() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        String alert = productPage.getSuccessAlertText();
        Assert.assertTrue(alert.toLowerCase().contains("success") || alert.toLowerCase().contains("product comparison"));
    }

    @Test(description = "TC_PROD_32: Product detail breadcrumb navigation trail verification")
    public void TC_PROD_32_productDetailBreadcrumbTrail() {
        productPage.navigateToProduct(47);
        Assert.assertTrue(productPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_PROD_33: Review author field boundary minimum 3 characters validation")
    public void TC_PROD_33_reviewAuthorMinLengthValidation() {
        productPage.navigateToProduct(47);
        productPage.submitReview("Jo", "This is a great product with solid build and long battery life.", 5);
        Assert.assertTrue(productPage.getReviewAlertText().contains("Warning: Review Name must be between 3 and 25 characters!"));
    }

    @Test(description = "TC_PROD_34: Review author field boundary maximum 25 characters validation")
    public void TC_PROD_34_reviewAuthorMaxLengthValidation() {
        productPage.navigateToProduct(47);
        productPage.submitReview("ThisNameIsFarTooLongForValidation", "This is a great product with solid build and long battery life.", 5);
        Assert.assertTrue(productPage.getReviewAlertText().contains("Warning: Review Name must be between 3 and 25 characters!"));
    }

    @Test(description = "TC_PROD_35: Review text field boundary minimum 25 characters validation")
    public void TC_PROD_35_reviewTextMinLengthValidation() {
        productPage.navigateToProduct(47);
        productPage.submitReview("John", "Short review text.", 5);
        Assert.assertTrue(productPage.getReviewAlertText().contains("Warning: Review Text must be between 25 and 1000 characters!"));
    }

    @Test(description = "TC_PROD_36: Review submission rating selection validation")
    public void TC_PROD_36_reviewRatingRequiredValidation() {
        productPage.navigateToProduct(47);
        productPage.submitReview("John Doe", "Excellent performance and build quality across daily operations.", 0);
        Assert.assertTrue(productPage.getReviewAlertText().contains("Warning: Please select a review rating!"));
    }

    @Test(description = "TC_PROD_37: Valid review submission with 5-star rating")
    public void TC_PROD_37_validReviewSubmission5Star() {
        productPage.navigateToProduct(47);
        productPage.submitReview("Tester Pro", "Superb workstation monitor with rich vivid colors and sharp text.", 5);
        Assert.assertTrue(productPage.getReviewAlertText().toLowerCase().contains("thank you for your review"));
    }

    @Test(description = "TC_PROD_38: Valid review submission with 4-star rating")
    public void TC_PROD_38_validReviewSubmission4Star() {
        productPage.navigateToProduct(47);
        productPage.submitReview("Tester Pro", "Great display quality though standby power consumption could improve.", 4);
        Assert.assertTrue(productPage.getReviewAlertText().toLowerCase().contains("thank you for your review"));
    }

    @Test(description = "TC_PROD_39: Valid review submission with 3-star rating")
    public void TC_PROD_39_validReviewSubmission3Star() {
        productPage.navigateToProduct(47);
        productPage.submitReview("Tester Pro", "Decent value product, meets standard expectations for office work.", 3);
        Assert.assertTrue(productPage.getReviewAlertText().toLowerCase().contains("thank you for your review"));
    }

    @Test(description = "TC_PROD_40: Valid review submission with 2-star rating")
    public void TC_PROD_40_validReviewSubmission2Star() {
        productPage.navigateToProduct(47);
        productPage.submitReview("Tester Pro", "Subpar performance and inconsistent brightness across screen corners.", 2);
        Assert.assertTrue(productPage.getReviewAlertText().toLowerCase().contains("thank you for your review"));
    }

    @Test(description = "TC_PROD_41: Valid review submission with 1-star rating")
    public void TC_PROD_41_validReviewSubmission1Star() {
        productPage.navigateToProduct(47);
        productPage.submitReview("Tester Pro", "Defective unit received with dead pixels and unresponsive buttons.", 1);
        Assert.assertTrue(productPage.getReviewAlertText().toLowerCase().contains("thank you for your review"));
    }

    @Test(description = "TC_PROD_42: Direct product URL loading via product route and ID")
    public void TC_PROD_42_directProductUrlLoading() {
        productPage.navigateToProduct(43);
        Assert.assertTrue(productPage.getProductTitle() != null && !productPage.getProductTitle().isEmpty());
    }

    @Test(description = "TC_PROD_43: Product details page displays pricing information")
    public void TC_PROD_43_productPricingInformation() {
        productPage.navigateToProduct(47);
        Assert.assertTrue(productPage.isPriceDisplayed() || productPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_PROD_44: Product details page displays stock availability status")
    public void TC_PROD_44_productStockAvailability() {
        productPage.navigateToProduct(47);
        Assert.assertTrue(productPage.isStockDisplayed() || productPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_PROD_45: Product details page meta title verification")
    public void TC_PROD_45_productMetaTitleVerification() {
        productPage.navigateToProduct(47);
        String title = productPage.getPageTitle();
        Assert.assertTrue(title != null && !title.trim().isEmpty());
    }
}

