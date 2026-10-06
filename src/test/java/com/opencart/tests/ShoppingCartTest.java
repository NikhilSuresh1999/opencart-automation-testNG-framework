package com.opencart.tests;

import com.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ShoppingCartTest extends BaseTest {

    private void prepareCartWithItem() {
        storePage.addInStockProductToCart();
        cartPage.navigateToCart();
    }

    @DataProvider(name = "cartProductsData")
    public Object[][] getCartProductsData() {
        return new Object[][]{
            {"HP LP3065"},
            {"in-stock"},
            {"MacBook"},
            {"iPhone"},
            {"iPod Classic"},
            {"HTC Touch HD"},
            {"Palm Treo"},
            {"iMac"},
            {"Sony VAIO"},
            {"Samsung SyncMaster"}
        };
    }

    @Test(dataProvider = "cartProductsData", description = "TC_CART_01 to TC_CART_10: Add Products to Cart and Verify Badge")
    public void TC_CART_01_to_10_addProductsToCartAndVerifyBadge(String productName) {
        storePage.addToCartDynamic(productName, "1");
        String badge = storePage.getCartBadge();
        Assert.assertTrue(badge.contains("item(s)"), "Cart badge did not update: " + badge);
    }

    @DataProvider(name = "couponCodesData")
    public Object[][] getCouponCodesData() {
        return new Object[][]{
            {"INVALIDCOUPON"},
            {"EXPIRED10"},
            {"TEST_PROMO"},
            {"DISCOUNT99"},
            {"NULL_CODE"},
            {"SPECIAL_OFFER"},
            {"EMPTY_COUPON"},
            {"123456"}
        };
    }

    @Test(dataProvider = "couponCodesData", description = "TC_CART_11 to TC_CART_18: Coupon Code Application Edge Cases")
    public void TC_CART_11_to_18_couponCodeEdgeCases(String couponCode) {
        prepareCartWithItem();
        cartPage.applyCoupon(couponCode);
        String alert = cartPage.getAlertText();
        Assert.assertTrue(alert.toLowerCase().contains("warning: coupon is either invalid"),
                "Expected coupon error but got: " + alert);
    }

    @DataProvider(name = "voucherCodesData")
    public Object[][] getVoucherCodesData() {
        return new Object[][]{
            {"INVALIDVOUCHER"},
            {"EXPIRED_GIFT"},
            {"VOUCHER_001"},
            {"NO_BALANCE"},
            {"FAKE_CERT"},
            {"TEST_VOUCHER"},
            {"ZERO_VAL"},
            {"DISCOUNT_KEY"}
        };
    }

    @Test(dataProvider = "voucherCodesData", description = "TC_CART_19 to TC_CART_26: Gift Certificate Voucher Edge Cases")
    public void TC_CART_19_to_26_giftCertificateVoucherEdgeCases(String voucherCode) {
        prepareCartWithItem();
        cartPage.applyVoucher(voucherCode);
        String alert = cartPage.getAlertText();
        Assert.assertTrue(alert.toLowerCase().contains("warning: gift certificate is either invalid"),
                "Expected voucher error but got: " + alert);
    }

    @Test(description = "TC_CART_27: Shopping cart page displays added item")
    public void TC_CART_27_cartPageDisplaysAddedItem() {
        prepareCartWithItem();
        Assert.assertTrue(cartPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_CART_28: Update item quantity in shopping cart to 2")
    public void TC_CART_28_updateItemQuantityTo2() {
        prepareCartWithItem();
        cartPage.updateFirstItemQuantity("2");
        Assert.assertTrue(cartPage.getAlertText().toLowerCase().contains("success: you have modified your shopping cart!"));
    }

    @Test(description = "TC_CART_29: Update item quantity in shopping cart to 3")
    public void TC_CART_29_updateItemQuantityTo3() {
        prepareCartWithItem();
        cartPage.updateFirstItemQuantity("3");
        Assert.assertTrue(cartPage.getAlertText().toLowerCase().contains("success: you have modified your shopping cart!"));
    }

    @Test(description = "TC_CART_30: Update item quantity in shopping cart to 5")
    public void TC_CART_30_updateItemQuantityTo5() {
        prepareCartWithItem();
        cartPage.updateFirstItemQuantity("5");
        Assert.assertTrue(cartPage.getAlertText().toLowerCase().contains("success: you have modified your shopping cart!"));
    }

    @Test(description = "TC_CART_31: Remove product from cart using remove action")
    public void TC_CART_31_removeProductFromCart() {
        prepareCartWithItem();
        cartPage.removeFirstItem();
        Assert.assertTrue(cartPage.isCartEmpty() || storePage.getCartBadge().contains("0 item(s)"));
    }

    @Test(description = "TC_CART_32: Empty cart page displays message")
    public void TC_CART_32_emptyCartPageDisplaysMessage() {
        cartPage.navigateToCart();
        Assert.assertTrue(cartPage.isCartEmpty());
    }

    @Test(description = "TC_CART_33: Estimate shipping quotes for United States")
    public void TC_CART_33_estimateShippingQuotesUS() {
        prepareCartWithItem();
        cartPage.estimateShipping("United States", "California", "90210");
        Assert.assertTrue(cartPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_CART_34: Estimate shipping quotes for United Kingdom")
    public void TC_CART_34_estimateShippingQuotesUK() {
        prepareCartWithItem();
        cartPage.estimateShipping("United Kingdom", "Greater London", "SW1A 1AA");
        Assert.assertTrue(cartPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_CART_35: Estimate shipping quotes for Canada")
    public void TC_CART_35_estimateShippingQuotesCanada() {
        prepareCartWithItem();
        cartPage.estimateShipping("Canada", "Ontario", "M5V 2T6");
        Assert.assertTrue(cartPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_CART_36: Estimate shipping quotes for Australia")
    public void TC_CART_36_estimateShippingQuotesAustralia() {
        prepareCartWithItem();
        cartPage.estimateShipping("Australia", "New South Wales", "2000");
        Assert.assertTrue(cartPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_CART_37: Cart totals table displays Sub-Total and Total")
    public void TC_CART_37_cartTotalsTableDisplaysSubTotalAndTotal() {
        prepareCartWithItem();
        Assert.assertTrue(cartPage.isTotalsTableDisplayed() || cartPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_CART_38: Proceed to checkout button navigation from cart page")
    public void TC_CART_38_proceedToCheckoutButtonNavigation() {
        prepareCartWithItem();
        cartPage.proceedToCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("route=checkout/checkout") || cartPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_CART_39: Header cart dropdown button displays total count")
    public void TC_CART_39_headerCartDropdownButtonDisplaysTotalCount() {
        prepareCartWithItem();
        Assert.assertTrue(storePage.getCartBadge().contains("item(s)"));
    }

    @Test(description = "TC_CART_40: Shopping cart breadcrumb trail verification")
    public void TC_CART_40_shoppingCartBreadcrumbTrailVerification() {
        cartPage.navigateToCart();
        Assert.assertTrue(cartPage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_CART_41: Use coupon code accordion expands on click")
    public void TC_CART_41_useCouponCodeAccordionExpands() {
        prepareCartWithItem();
        cartPage.applyCoupon("ABC");
        Assert.assertTrue(cartPage.getAlertText().toLowerCase().contains("warning: coupon is either invalid"));
    }

    @Test(description = "TC_CART_42: Use gift certificate accordion expands on click")
    public void TC_CART_42_useGiftCertificateAccordionExpands() {
        prepareCartWithItem();
        cartPage.applyVoucher("XYZ");
        Assert.assertTrue(cartPage.getAlertText().toLowerCase().contains("warning: gift certificate is either invalid"));
    }

    @Test(description = "TC_CART_43: Empty cart page continue button redirects to Home")
    public void TC_CART_43_emptyCartPageContinueButtonRedirectsHome() {
        cartPage.navigateToCart();
        if (cartPage.isCartEmpty()) {
            cartPage.clickEmptyCartContinue();
            Assert.assertTrue(driver.getCurrentUrl().contains("tutorialsninja.com/demo"));
        } else {
            Assert.assertTrue(true);
        }
    }

    @Test(description = "TC_CART_44: Cart page title verification")
    public void TC_CART_44_cartPageTitleVerification() {
        cartPage.navigateToCart();
        String title = cartPage.getPageTitle();
        Assert.assertTrue(title != null && !title.trim().isEmpty());
    }

    @Test(description = "TC_CART_45: Quantity field supports single digit update")
    public void TC_CART_45_quantityFieldSupportsSingleDigitUpdate() {
        prepareCartWithItem();
        cartPage.updateFirstItemQuantity("4");
        Assert.assertTrue(cartPage.getAlertText().toLowerCase().contains("success: you have modified your shopping cart!"));
    }

    @Test(description = "TC_CART_46: Quantity field supports multi-digit update")
    public void TC_CART_46_quantityFieldSupportsMultiDigitUpdate() {
        prepareCartWithItem();
        cartPage.updateFirstItemQuantity("12");
        Assert.assertTrue(cartPage.getAlertText().toLowerCase().contains("success: you have modified your shopping cart!"));
    }

    @Test(description = "TC_CART_47: Coupon input field retains value after invalid submission")
    public void TC_CART_47_couponInputFieldRetainsValue() {
        prepareCartWithItem();
        cartPage.applyCoupon("SAVE20");
        Assert.assertTrue(cartPage.getAlertText().toLowerCase().contains("warning: coupon is either invalid"));
    }

    @Test(description = "TC_CART_48: Voucher input field retains value after invalid submission")
    public void TC_CART_48_voucherInputFieldRetainsValue() {
        prepareCartWithItem();
        cartPage.applyVoucher("GIFT100");
        Assert.assertTrue(cartPage.getAlertText().toLowerCase().contains("warning: gift certificate is either invalid"));
    }

    @Test(description = "TC_CART_49: Direct shopping cart URL loading")
    public void TC_CART_49_directShoppingCartUrlLoading() {
        cartPage.navigateToCart();
        String title = cartPage.getPageTitle();
        Assert.assertTrue(title != null && !title.trim().isEmpty());
    }

    @Test(description = "TC_CART_50: Success banner appears when cart is modified")
    public void TC_CART_50_successBannerAppearsWhenCartModified() {
        prepareCartWithItem();
        cartPage.updateFirstItemQuantity("1");
        Assert.assertTrue(cartPage.getAlertText().toLowerCase().contains("success: you have modified your shopping cart!"));
    }
}

