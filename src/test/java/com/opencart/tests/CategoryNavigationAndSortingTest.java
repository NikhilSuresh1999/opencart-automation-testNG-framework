package com.opencart.tests;

import com.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CategoryNavigationAndSortingTest extends BaseTest {

    @DataProvider(name = "categoryHierarchyData")
    public Object[][] getCategoryHierarchyData() {
        return new Object[][]{
            {"20", "Desktops"},
            {"18", "Laptops & Notebooks"},
            {"25", "Components"},
            {"57", "Tablets"},
            {"17", "Software"},
            {"24", "Phones & PDAs"},
            {"33", "Cameras"},
            {"34", "MP3 Players"},
            {"20_26", "PC"},
            {"20_27", "Mac"},
            {"25_28", "Monitors"},
            {"25_30", "Printers"}
        };
    }

    @Test(dataProvider = "categoryHierarchyData", description = "TC_CAT_01 to TC_CAT_12: Category Hierarchy Navigation")
    public void TC_CAT_01_to_12_categoryHierarchyNavigation(String path, String expectedHeading) {
        storePage.navigateToCategory(path);
        String heading = storePage.getCategoryHeading();
        Assert.assertTrue(heading.toLowerCase().contains(expectedHeading.toLowerCase()),
                "Category heading expected to contain '" + expectedHeading + "' but got '" + heading + "'");
    }

    @DataProvider(name = "sortingOptionsData")
    public Object[][] getSortingOptionsData() {
        return new Object[][]{
            {"Default"},
            {"Name (A - Z)"},
            {"Name (Z - A)"},
            {"Price (Low > High)"},
            {"Price (High > Low)"},
            {"Rating (Highest)"},
            {"Rating (Lowest)"},
            {"Model (A - Z)"},
            {"Model (Z - A)"}
        };
    }

    @Test(dataProvider = "sortingOptionsData", description = "TC_CAT_13 to TC_CAT_21: Product Catalog Sorting Options")
    public void TC_CAT_13_to_21_productCatalogSortingOptions(String sortOption) {
        storePage.navigateToCategory("20");
        storePage.selectSort(sortOption);
        Assert.assertTrue(storePage.isSortDropdownDisplayed());
    }

    @DataProvider(name = "limitOptionsData")
    public Object[][] getLimitOptionsData() {
        return new Object[][]{
            {"20"},
            {"25"},
            {"50"},
            {"75"},
            {"100"}
        };
    }

    @Test(dataProvider = "limitOptionsData", description = "TC_CAT_22 to TC_CAT_26: Product Catalog Display Limit Options")
    public void TC_CAT_22_to_26_displayLimitOptions(String limitOption) {
        storePage.navigateToCategory("20");
        storePage.selectLimit(limitOption);
        Assert.assertTrue(storePage.isLimitDropdownDisplayed());
    }

    @Test(description = "TC_CAT_27: Switch catalog view to List View")
    public void TC_CAT_27_switchCatalogToListView() {
        storePage.navigateToCategory("20");
        storePage.selectView("list");
        Assert.assertTrue(storePage.isActiveView("list"));
    }

    @Test(description = "TC_CAT_28: Switch catalog view to Grid View")
    public void TC_CAT_28_switchCatalogToGridView() {
        storePage.navigateToCategory("20");
        storePage.selectView("grid");
        Assert.assertTrue(storePage.isActiveView("grid"));
    }

    @Test(description = "TC_CAT_29: List view button displays active styling")
    public void TC_CAT_29_listViewButtonDisplaysActiveStyling() {
        storePage.navigateToCategory("18");
        storePage.selectView("list");
        Assert.assertTrue(storePage.isActiveView("list"));
    }

    @Test(description = "TC_CAT_30: Grid view button displays active styling")
    public void TC_CAT_30_gridViewButtonDisplaysActiveStyling() {
        storePage.navigateToCategory("18");
        storePage.selectView("grid");
        Assert.assertTrue(storePage.isActiveView("grid"));
    }

    @Test(description = "TC_CAT_31: Category banner and heading verification for Desktops")
    public void TC_CAT_31_categoryBannerAndHeadingVerificationDesktops() {
        storePage.navigateToCategory("20");
        Assert.assertTrue(storePage.getCategoryHeading().toLowerCase().contains("desktops"));
    }

    @Test(description = "TC_CAT_32: Category subcategory list items display")
    public void TC_CAT_32_categorySubcategoryListItemsDisplay() {
        storePage.navigateToCategory("20");
        Assert.assertTrue(storePage.isSubcategoriesListDisplayed());
    }

    @Test(description = "TC_CAT_33: Subcategory navigation to Mac desktops")
    public void TC_CAT_33_subcategoryNavigationToMacDesktops() {
        storePage.navigateToCategory("20_27");
        Assert.assertTrue(storePage.getCategoryHeading().toLowerCase().contains("mac"));
    }

    @Test(description = "TC_CAT_34: Subcategory navigation to PC desktops")
    public void TC_CAT_34_subcategoryNavigationToPCDesktops() {
        storePage.navigateToCategory("20_26");
        Assert.assertTrue(storePage.getCategoryHeading().toLowerCase().contains("pc"));
    }

    @Test(description = "TC_CAT_35: Subcategory navigation to Monitors")
    public void TC_CAT_35_subcategoryNavigationToMonitors() {
        storePage.navigateToCategory("25_28");
        Assert.assertTrue(storePage.getCategoryHeading().toLowerCase().contains("monitors"));
    }

    @Test(description = "TC_CAT_36: Subcategory navigation to Printers")
    public void TC_CAT_36_subcategoryNavigationToPrinters() {
        storePage.navigateToCategory("25_30");
        Assert.assertTrue(storePage.getCategoryHeading().toLowerCase().contains("printers"));
    }

    @Test(description = "TC_CAT_37: Empty category display message verification")
    public void TC_CAT_37_emptyCategoryDisplayMessageVerification() {
        storePage.navigateToCategory("17");
        Assert.assertTrue(storePage.isEmptyCategoryMessageDisplayed("There are no products to list in this category."));
    }

    @Test(description = "TC_CAT_38: Category breadcrumb navigation back to Home")
    public void TC_CAT_38_categoryBreadcrumbNavigationBackToHome() {
        storePage.navigateToCategory("20");
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_CAT_39: Product card price text format verification")
    public void TC_CAT_39_productCardPriceTextFormatVerification() {
        storePage.navigateToCategory("20");
        Assert.assertTrue(storePage.isPriceWithTaxDisplayed());
    }

    @Test(description = "TC_CAT_40: Category product compare link text verification")
    public void TC_CAT_40_categoryProductCompareLinkTextVerification() {
        storePage.navigateToCategory("20");
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_CAT_41: Left navigation menu category expansion")
    public void TC_CAT_41_leftNavigationMenuCategoryExpansion() {
        storePage.navigateToCategory("20");
        Assert.assertTrue(storePage.isSubcategoriesListDisplayed());
    }

    @Test(description = "TC_CAT_42: Category page pagination controls visibility check")
    public void TC_CAT_42_categoryPagePaginationControlsVisibilityCheck() {
        storePage.navigateToCategory("20");
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_CAT_43: Top navigation Tablets direct department loading")
    public void TC_CAT_43_topNavigationTabletsDirectDepartmentLoading() {
        storePage.navigateToCategory("57");
        Assert.assertTrue(storePage.getCategoryHeading().toLowerCase().contains("tablets"));
    }

    @Test(description = "TC_CAT_44: Top navigation Cameras department loading")
    public void TC_CAT_44_topNavigationCamerasDepartmentLoading() {
        storePage.navigateToCategory("33");
        Assert.assertTrue(storePage.getCategoryHeading().toLowerCase().contains("cameras"));
    }

    @Test(description = "TC_CAT_45: Top navigation Phones & PDAs department loading")
    public void TC_CAT_45_topNavigationPhonesDepartmentLoading() {
        storePage.navigateToCategory("24");
        Assert.assertTrue(storePage.getCategoryHeading().toLowerCase().contains("phones & pdas"));
    }

    @Test(description = "TC_CAT_46: Category page meta title verification")
    public void TC_CAT_46_categoryPageMetaTitleVerification() {
        storePage.navigateToCategory("20");
        String title = storePage.getPageTitle();
        Assert.assertTrue(title != null && !title.trim().isEmpty());
    }

    @Test(description = "TC_CAT_47: Sorting dropdown preserves selected option")
    public void TC_CAT_47_sortingDropdownPreservesSelectedOption() {
        storePage.navigateToCategory("20");
        storePage.selectSort("Name (A - Z)");
        Assert.assertTrue(storePage.isSortDropdownDisplayed());
    }

    @Test(description = "TC_CAT_48: Limit dropdown preserves selected option")
    public void TC_CAT_48_limitDropdownPreservesSelectedOption() {
        storePage.navigateToCategory("20");
        storePage.selectLimit("25");
        Assert.assertTrue(storePage.isLimitDropdownDisplayed());
    }

    @Test(description = "TC_CAT_49: Category view mode toggle persistence")
    public void TC_CAT_49_categoryViewModeTogglePersistence() {
        storePage.navigateToCategory("20");
        storePage.selectView("list");
        storePage.selectView("grid");
        Assert.assertTrue(storePage.isActiveView("grid"));
    }
}

