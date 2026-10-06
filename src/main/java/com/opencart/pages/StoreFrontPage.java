package com.opencart.pages;

import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class StoreFrontPage extends BasePage {
    private final Faker faker = new Faker();

    // Search & Grid Locators
    private final By searchBox = By.name("search");
    private final By searchBtn = By.cssSelector("#search button");
    private final By productTitles = By.cssSelector("div.product-layout h4 a");
    private final By emptySearchMsg = By.xpath("//p[contains(text(), 'There is no product') or contains(text(), 'no products to list')]");
    
    // Advanced Search Locators
    private final By advSearchInput = By.id("input-search");
    private final By advCategorySelect = By.name("category_id");
    private final By advSubCategoryCheckbox = By.name("sub_category");
    private final By advDescriptionCheckbox = By.name("description");
    private final By advSearchBtn = By.id("button-search");
    private final By searchResultCount = By.xpath("//div[@id='content']//div[contains(text(), 'Showing')] | //div[@id='content']//h2");

    // Catalog controls
    private final By sortDropdown = By.id("input-sort");
    private final By limitDropdown = By.id("input-limit");
    private final By listViewBtn = By.id("list-view");
    private final By gridViewBtn = By.id("grid-view");
    private final By categoryHeading = By.xpath("//div[@id='content']//h2");
    private final By subcategoriesList = By.xpath("//div[@id='content']//ul | //div[@id='content']//h3");
    private final By priceTax = By.cssSelector(".price-tax, .price");
    private final By compareTotalLink = By.id("compare-total");

    // Currency Switcher Locators
    private final By currencyDropdownBtn = By.xpath("//form[@id='form-currency']//button[contains(@class,'dropdown-toggle')]");
    private final By currencySymbolText = By.xpath("//form[@id='form-currency']//strong");
    
    // Cart Locators
    private final By addToCartBtn = By.xpath("(//button[contains(@onclick, 'cart.add')])[1]");
    private final By productAddToCartBtn = By.id("button-cart");
    private final By cartTotalBtn = By.id("cart-total");

    public StoreFrontPage(WebDriver driver) {
        super(driver);
    }

    public void searchProduct(String keyword) {
        ele.type(searchBox, keyword);
        ele.click(searchBtn);
        ele.waitForAjaxToComplete();
    }

    public void searchProductWithEnter(String keyword) {
        driver.findElement(searchBox).sendKeys(keyword + Keys.ENTER);
        ele.waitForAjaxToComplete();
    }

    public String getGridResult() {
        if (ele.isElementDisplayed(emptySearchMsg)) {
            return "No product matches";
        }
        return ele.getTextSafely(productTitles);
    }

    public void navigateToSearchPage() {
        navigateTo("index.php?route=product/search");
    }

    public void performAdvancedSearch(String keyword, String category, boolean searchSubcat, boolean searchDesc) {
        navigateToSearchPage();
        ele.type(advSearchInput, keyword);
        if (category != null && !category.isEmpty() && !"All Categories".equalsIgnoreCase(category)) {
            ele.selectByVisibleText(advCategorySelect, category);
        }
        if (searchSubcat) {
            ele.clickWithJS(advSubCategoryCheckbox);
        }
        if (searchDesc) {
            ele.clickWithJS(advDescriptionCheckbox);
        }
        ele.click(advSearchBtn);
        ele.waitForAjaxToComplete();
    }

    public String getSearchCriteriaInputValue() {
        return ele.getAttributeSafely(advSearchInput, "value");
    }

    public boolean isSearchResultCountDisplayed() {
        return ele.isElementDisplayed(searchResultCount);
    }

    public void navigateToCategory(String path) {
        navigateTo("index.php?route=product/category&path=" + path);
    }

    public String getCategoryHeading() {
        return ele.getTextSafely(categoryHeading);
    }

    public void selectSort(String sortOption) {
        ele.selectByVisibleText(sortDropdown, sortOption);
        ele.waitForAjaxToComplete();
    }

    public boolean isSortDropdownDisplayed() {
        return ele.isElementDisplayed(sortDropdown);
    }

    public void selectLimit(String limitOption) {
        ele.selectByVisibleText(limitDropdown, limitOption);
        ele.waitForAjaxToComplete();
    }

    public boolean isLimitDropdownDisplayed() {
        return ele.isElementDisplayed(limitDropdown);
    }

    public void selectView(String view) {
        if ("list".equalsIgnoreCase(view)) {
            ele.click(listViewBtn);
        } else {
            ele.click(gridViewBtn);
        }
        ele.waitForAjaxToComplete();
    }

    public boolean isActiveView(String view) {
        if ("list".equalsIgnoreCase(view)) {
            return ele.isElementDisplayed(By.cssSelector("#list-view.active, .product-list"));
        } else {
            return ele.isElementDisplayed(By.cssSelector("#grid-view.active, .product-grid"));
        }
    }

    public boolean isSubcategoriesListDisplayed() {
        return ele.isElementDisplayed(subcategoriesList);
    }

    public boolean isEmptyCategoryMessageDisplayed(String expectedMsg) {
        return ele.isElementDisplayed(By.xpath("//div[@id='content']//p[contains(text(),'" + expectedMsg + "')]"));
    }

    public boolean isPriceWithTaxDisplayed() {
        return ele.isElementDisplayed(priceTax);
    }

    public boolean isCompareTotalLinkDisplayed() {
        return ele.isElementDisplayed(compareTotalLink);
    }

    public void switchCurrency(String currencyCode) {
        ele.click(currencyDropdownBtn);
        By currBtn = By.name(currencyCode.toUpperCase());
        ele.click(currBtn);
        ele.waitForAjaxToComplete();
    }

    public String getCurrentCurrencySymbol() {
        return ele.getTextSafely(currencySymbolText);
    }

    public void addInStockProductToCart() {
        navigateTo("index.php?route=product/product&product_id=47");
        ele.scrollToElement(productAddToCartBtn);
        ele.click(productAddToCartBtn);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(d -> {
                String txt = d.findElement(cartTotalBtn).getText();
                return txt != null && txt.contains("item(s)") && !txt.startsWith("0 item(s)");
            });
        } catch (Exception ignored) {
        }
    }

    public void addToCartDynamic(String product, String qty) {
        if ("HP LP3065".equalsIgnoreCase(product) || "in-stock".equalsIgnoreCase(product)) {
            addInStockProductToCart();
            return;
        }
        searchProduct(product);
        ele.click(addToCartBtn);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(d -> {
                String txt = d.findElement(cartTotalBtn).getText();
                return txt != null && txt.contains("item(s)") && !txt.startsWith("0 item(s)");
            });
        } catch (Exception ignored) {
        }
    }

    @Override
    public String getCartBadge() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(d -> {
                String txt = d.findElement(cartTotalBtn).getText();
                return txt != null && txt.contains("item(s)");
            });
        } catch (Exception ignored) {
        }
        return ele.getTextSafely(cartTotalBtn);
    }
}

