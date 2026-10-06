package com.opencart.tests;

import com.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ProductSearchAndCatalogTest extends BaseTest {

    @DataProvider(name = "catalogKeywordsData")
    public Object[][] getCatalogKeywordsData() {
        return new Object[][]{
            {"MacBook", "MacBook"},
            {"iPhone", "iPhone"},
            {"Canon", "Canon"},
            {"Samsung", "Samsung"},
            {"Apple", "Apple"},
            {"Sony", "Sony"},
            {"HTC", "HTC"},
            {"Palm", "Palm"},
            {"HP", "HP"},
            {"Nikon", "Nikon"},
            {"iPod", "iPod"},
            {"Touch", "Touch"},
            {"Cinema", "Cinema"},
            {"VAIO", "VAIO"},
            {"SyncMaster", "SyncMaster"}
        };
    }

    @Test(dataProvider = "catalogKeywordsData", description = "TC_SRCH_01 to TC_SRCH_15: Product Search by Catalog Keywords")
    public void TC_SRCH_01_to_15_searchByCatalogKeywords(String keyword, String expectedProduct) {
        storePage.searchProduct(keyword);
        String gridResult = storePage.getGridResult();
        Assert.assertTrue(gridResult.toLowerCase().contains(expectedProduct.toLowerCase()),
                "Product grid expected to contain '" + expectedProduct + "' but got '" + gridResult + "'");
    }

    @DataProvider(name = "boundaryKeywordsData")
    public Object[][] getBoundaryKeywordsData() {
        return new Object[][]{
            {"InvalidProductXYZ", "No product matches"},
            {"999999999", "No product matches"},
            {"@#$%^&*", "No product matches"},
            {"NonExistentBrand", "No product matches"},
            {"ZeroResultGadget", "No product matches"},
            {"RandomString12345", "No product matches"},
            {"UnknownDevice99", "No product matches"},
            {"NotInCatalog", "No product matches"},
            {"XXXXXXXXXXXXX", "No product matches"},
            {"UnmatchedSearchTerm", "No product matches"}
        };
    }

    @Test(dataProvider = "boundaryKeywordsData", description = "TC_SRCH_16 to TC_SRCH_25: Non-existent and Boundary Keywords")
    public void TC_SRCH_16_to_25_searchNonExistentAndBoundaryKeywords(String keyword, String expectedMessage) {
        storePage.searchProduct(keyword);
        String gridResult = storePage.getGridResult();
        Assert.assertTrue(gridResult.toLowerCase().contains(expectedMessage.toLowerCase()),
                "Product grid expected to contain '" + expectedMessage + "' but got '" + gridResult + "'");
    }

    @Test(description = "TC_SRCH_26: Search with partial product keyword 'Mac'")
    public void TC_SRCH_26_searchPartialKeywordMac() {
        storePage.searchProduct("Mac");
        Assert.assertTrue(storePage.getGridResult().toLowerCase().contains("mac"));
    }

    @Test(description = "TC_SRCH_27: Search with partial product keyword 'Pho'")
    public void TC_SRCH_27_searchPartialKeywordPho() {
        storePage.searchProduct("Pho");
        Assert.assertTrue(storePage.getGridResult().toLowerCase().contains("iphone"));
    }

    @Test(description = "TC_SRCH_28: Search with partial product keyword 'Can'")
    public void TC_SRCH_28_searchPartialKeywordCan() {
        storePage.searchProduct("Can");
        Assert.assertTrue(storePage.getGridResult().toLowerCase().contains("canon"));
    }

    @Test(description = "TC_SRCH_29: Case-insensitive search with lowercase 'macbook'")
    public void TC_SRCH_29_searchCaseInsensitiveLowercase() {
        storePage.searchProduct("macbook");
        Assert.assertTrue(storePage.getGridResult().toLowerCase().contains("macbook"));
    }

    @Test(description = "TC_SRCH_30: Case-insensitive search with uppercase 'MACBOOK'")
    public void TC_SRCH_30_searchCaseInsensitiveUppercase() {
        storePage.searchProduct("MACBOOK");
        Assert.assertTrue(storePage.getGridResult().toLowerCase().contains("macbook"));
    }

    @Test(description = "TC_SRCH_31: Search with mixed case 'mAcBoOk'")
    public void TC_SRCH_31_searchMixedCase() {
        storePage.searchProduct("mAcBoOk");
        Assert.assertTrue(storePage.getGridResult().toLowerCase().contains("macbook"));
    }

    @Test(description = "TC_SRCH_32: Search utilizing header search button click")
    public void TC_SRCH_32_searchUtilizingHeaderSearchButtonClick() {
        storePage.searchProduct("HP");
        Assert.assertTrue(storePage.getGridResult().toLowerCase().contains("hp"));
    }

    @Test(description = "TC_SRCH_33: Search utilizing Enter key in search box")
    public void TC_SRCH_33_searchUtilizingEnterKey() {
        storePage.searchProductWithEnter("iPhone");
        Assert.assertTrue(storePage.getGridResult().toLowerCase().contains("iphone"));
    }

    @Test(description = "TC_SRCH_34: Advanced search navigating to search results page")
    public void TC_SRCH_34_advancedSearchNavigatingToResultsPage() {
        storePage.navigateToSearchPage();
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SRCH_35: Search within category 'Desktops'")
    public void TC_SRCH_35_searchWithinCategoryDesktops() {
        storePage.performAdvancedSearch("Mac", "Desktops", false, false);
        Assert.assertTrue(storePage.getGridResult().toLowerCase().contains("mac"));
    }

    @Test(description = "TC_SRCH_36: Search within category 'Laptops & Notebooks'")
    public void TC_SRCH_36_searchWithinCategoryLaptops() {
        storePage.performAdvancedSearch("HP", "Laptops & Notebooks", false, false);
        Assert.assertTrue(storePage.getGridResult().toLowerCase().contains("hp"));
    }

    @Test(description = "TC_SRCH_37: Search within category 'Components'")
    public void TC_SRCH_37_searchWithinCategoryComponents() {
        storePage.performAdvancedSearch("Monitor", "Components", true, false);
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SRCH_38: Search within category 'Tablets'")
    public void TC_SRCH_38_searchWithinCategoryTablets() {
        storePage.performAdvancedSearch("Samsung", "Tablets", false, false);
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SRCH_39: Search within category 'Cameras'")
    public void TC_SRCH_39_searchWithinCategoryCameras() {
        storePage.performAdvancedSearch("Canon", "Cameras", false, false);
        Assert.assertTrue(storePage.getGridResult().toLowerCase().contains("canon"));
    }

    @Test(description = "TC_SRCH_40: Search with subcategory checkbox enabled")
    public void TC_SRCH_40_searchWithSubcategoryCheckboxEnabled() {
        storePage.performAdvancedSearch("Apple", "Desktops", true, false);
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SRCH_41: Search with subcategory checkbox disabled")
    public void TC_SRCH_41_searchWithSubcategoryCheckboxDisabled() {
        storePage.performAdvancedSearch("Apple", "Desktops", false, false);
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SRCH_42: Search with product description checkbox enabled")
    public void TC_SRCH_42_searchWithDescriptionCheckboxEnabled() {
        storePage.performAdvancedSearch("Intel", "All Categories", false, true);
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SRCH_43: Search with product description checkbox disabled")
    public void TC_SRCH_43_searchWithDescriptionCheckboxDisabled() {
        storePage.performAdvancedSearch("Intel", "All Categories", false, false);
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SRCH_44: Search criteria input field retains searched keyword")
    public void TC_SRCH_44_searchCriteriaInputRetainsKeyword() {
        storePage.searchProduct("MacBook");
        Assert.assertTrue(storePage.getSearchCriteriaInputValue().toLowerCase().contains("macbook"));
    }

    @Test(description = "TC_SRCH_45: Search result count indicator validation")
    public void TC_SRCH_45_searchResultCountIndicatorValidation() {
        storePage.searchProduct("MacBook");
        Assert.assertTrue(storePage.isSearchResultCountDisplayed());
    }

    @Test(description = "TC_SRCH_46: Search page breadcrumb trail verification")
    public void TC_SRCH_46_searchPageBreadcrumbTrailVerification() {
        storePage.searchProduct("Canon");
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SRCH_47: Search for model number 'Product 15'")
    public void TC_SRCH_47_searchForModelNumberProduct15() {
        storePage.searchProduct("Product 15");
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SRCH_48: Search for model number 'Product 16'")
    public void TC_SRCH_48_searchForModelNumberProduct16() {
        storePage.searchProduct("Product 16");
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }

    @Test(description = "TC_SRCH_49: Search with leading and trailing whitespaces")
    public void TC_SRCH_49_searchWithLeadingAndTrailingWhitespaces() {
        storePage.searchProduct("  MacBook  ");
        Assert.assertTrue(storePage.getGridResult().toLowerCase().contains("macbook"));
    }

    @Test(description = "TC_SRCH_50: Direct navigation to search page with empty query parameter")
    public void TC_SRCH_50_directNavigationToSearchPageEmptyQuery() {
        storePage.navigateToSearchPage();
        Assert.assertTrue(storePage.isBreadcrumbDisplayed());
    }
}

