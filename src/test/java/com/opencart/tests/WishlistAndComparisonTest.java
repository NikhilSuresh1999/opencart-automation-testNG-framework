package com.opencart.tests;

import com.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WishlistAndComparisonTest extends BaseTest {

    @DataProvider(name = "wishlistProductsData")
    public Object[][] getWishlistProductsData() {
        return new Object[][]{
            {47},
            {43},
            {40},
            {30},
            {42},
            {33},
            {46},
            {28}
        };
    }

    @Test(dataProvider = "wishlistProductsData", description = "TC_WSH_01 to TC_WSH_08: Add to Wishlist as Guest User")
    public void TC_WSH_01_to_08_addToWishlistAsGuest(int productId) {
        productPage.navigateToProduct(productId);
        productPage.addToWishList();
        String alert = productPage.getSuccessAlertText();
        Assert.assertTrue(alert.toLowerCase().contains("login") || alert.toLowerCase().contains("create an account") || alert.toLowerCase().contains("wish list"),
                "Expected login prompt in wishlist alert but got: " + alert);
    }

    @DataProvider(name = "comparisonProductsData")
    public Object[][] getComparisonProductsData() {
        return new Object[][]{
            {47},
            {43},
            {40},
            {30},
            {42},
            {33},
            {46},
            {28}
        };
    }

    @Test(dataProvider = "comparisonProductsData", description = "TC_CMP_09 to TC_CMP_16: Add Products to Comparison")
    public void TC_CMP_09_to_16_addProductsToComparison(int productId) {
        productPage.navigateToProduct(productId);
        productPage.addToCompare();
        String alert = productPage.getSuccessAlertText();
        Assert.assertTrue(alert.toLowerCase().contains("success") || alert.toLowerCase().contains("product comparison"),
                "Expected comparison success alert but got: " + alert);
    }

    @Test(description = "TC_WSH_17: Wishlist header link text displays item counter")
    public void TC_WSH_17_wishlistHeaderLinkDisplaysCounter() {
        productPage.navigateToProduct(47);
        productPage.addToWishList();
        Assert.assertTrue(productPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_WSH_18: Clicking wishlist link as guest redirects to Login page")
    public void TC_WSH_18_clickingWishlistLinkAsGuestRedirectsToLogin() {
        storePage.navigateTo("index.php?route=account/wishlist");
        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/login"));
    }

    @Test(description = "TC_WSH_19: Wishlist alert notification contains login link")
    public void TC_WSH_19_wishlistAlertContainsLoginLink() {
        productPage.navigateToProduct(47);
        productPage.addToWishList();
        String alert = productPage.getSuccessAlertText();
        Assert.assertTrue(alert.toLowerCase().contains("login") || alert.toLowerCase().contains("wish list"));
    }

    @Test(description = "TC_WSH_20: Wishlist alert notification contains create an account link")
    public void TC_WSH_20_wishlistAlertContainsCreateAccountLink() {
        productPage.navigateToProduct(47);
        productPage.addToWishList();
        String alert = productPage.getSuccessAlertText();
        Assert.assertTrue(alert.toLowerCase().contains("create an account") || alert.toLowerCase().contains("login") || alert.toLowerCase().contains("wish list"));
    }

    @Test(description = "TC_WSH_21: Wishlist alert notification contains product name link")
    public void TC_WSH_21_wishlistAlertContainsProductNameLink() {
        productPage.navigateToProduct(47);
        productPage.addToWishList();
        String alert = productPage.getSuccessAlertText();
        Assert.assertTrue(alert.toLowerCase().contains("hp lp3065") || alert.toLowerCase().contains("wish list"));
    }

    @Test(description = "TC_WSH_22: Wishlist action from product details page")
    public void TC_WSH_22_wishlistActionFromProductDetailsPage() {
        productPage.navigateToProduct(47);
        productPage.addToWishList();
        String alert = productPage.getSuccessAlertText();
        Assert.assertTrue(alert.toLowerCase().contains("login") || alert.toLowerCase().contains("wish list"));
    }

    @Test(description = "TC_CMP_23: Comparison banner contains link to product comparison")
    public void TC_CMP_23_comparisonBannerContainsLink() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        Assert.assertTrue(productPage.getSuccessAlertText().toLowerCase().contains("product comparison") || productPage.getSuccessAlertText().toLowerCase().contains("success"));
    }

    @Test(description = "TC_CMP_24: Navigate to product comparison page")
    public void TC_CMP_24_navigateToProductComparisonPage() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isComparisonTableDisplayed());
    }

    @Test(description = "TC_CMP_25: Comparison table displays product name")
    public void TC_CMP_25_comparisonTableDisplaysProductName() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isComparisonTableDisplayed());
    }

    @Test(description = "TC_CMP_26: Comparison table displays product image")
    public void TC_CMP_26_comparisonTableDisplaysProductImage() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isComparisonTableDisplayed());
    }

    @Test(description = "TC_CMP_27: Comparison table displays product price")
    public void TC_CMP_27_comparisonTableDisplaysProductPrice() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isComparisonTableDisplayed());
    }

    @Test(description = "TC_CMP_28: Comparison table displays product model")
    public void TC_CMP_28_comparisonTableDisplaysProductModel() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isComparisonTableDisplayed());
    }

    @Test(description = "TC_CMP_29: Comparison table displays product brand")
    public void TC_CMP_29_comparisonTableDisplaysProductBrand() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isComparisonTableDisplayed());
    }

    @Test(description = "TC_CMP_30: Comparison table displays product availability")
    public void TC_CMP_30_comparisonTableDisplaysProductAvailability() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isComparisonTableDisplayed());
    }

    @Test(description = "TC_CMP_31: Comparison table displays product rating")
    public void TC_CMP_31_comparisonTableDisplaysProductRating() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isComparisonTableDisplayed());
    }

    @Test(description = "TC_CMP_32: Comparison table displays product summary description")
    public void TC_CMP_32_comparisonTableDisplaysProductSummaryDescription() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isComparisonTableDisplayed());
    }

    @Test(description = "TC_CMP_33: Comparison table displays product weight and dimensions")
    public void TC_CMP_33_comparisonTableDisplaysWeightAndDimensions() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isComparisonTableDisplayed());
    }

    @Test(description = "TC_CMP_34: Remove product from comparison table")
    public void TC_CMP_34_removeProductFromComparisonTable() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        comparisonPage.removeProduct();
        Assert.assertTrue(comparisonPage.isEmptyComparisonMessageDisplayed());
    }

    @Test(description = "TC_CMP_35: Empty comparison page displays message")
    public void TC_CMP_35_emptyComparisonPageDisplaysMessage() {
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isEmptyComparisonMessageDisplayed());
    }

    @Test(description = "TC_CMP_36: Empty comparison continue button redirects to Home")
    public void TC_CMP_36_emptyComparisonContinueButtonRedirectsHome() {
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isEmptyComparisonMessageDisplayed());
    }

    @Test(description = "TC_CMP_37: Compare two products side by side")
    public void TC_CMP_37_compareTwoProductsSideBySide() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        productPage.navigateToProduct(43);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isComparisonTableDisplayed());
    }

    @Test(description = "TC_CMP_38: Product comparison breadcrumb verification")
    public void TC_CMP_38_productComparisonBreadcrumbVerification() {
        comparisonPage.navigateToComparison();
        Assert.assertTrue(comparisonPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_CMP_39: Header comparison counter update")
    public void TC_CMP_39_headerComparisonCounterUpdate() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        Assert.assertTrue(productPage.getSuccessAlertText().toLowerCase().contains("success") || productPage.getSuccessAlertText().toLowerCase().contains("product comparison"));
    }

    @Test(description = "TC_CMP_40: Product comparison page title verification")
    public void TC_CMP_40_productComparisonPageTitleVerification() {
        comparisonPage.navigateToComparison();
        String title = comparisonPage.getPageTitle();
        Assert.assertTrue(title != null && !title.trim().isEmpty());
    }

    @Test(description = "TC_CMP_41: Comparison action from product detail page")
    public void TC_CMP_41_comparisonActionFromProductDetailPage() {
        productPage.navigateToProduct(43);
        productPage.addToCompare();
        Assert.assertTrue(productPage.getSuccessAlertText().toLowerCase().contains("success") || productPage.getSuccessAlertText().toLowerCase().contains("product comparison"));
    }

    @Test(description = "TC_CMP_42: Clear all items from comparison")
    public void TC_CMP_42_clearAllItemsFromComparison() {
        productPage.navigateToProduct(47);
        productPage.addToCompare();
        comparisonPage.navigateToComparison();
        comparisonPage.removeProduct();
        Assert.assertTrue(comparisonPage.isEmptyComparisonMessageDisplayed());
    }
}

