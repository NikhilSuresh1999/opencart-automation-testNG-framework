package com.opencart.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementUtil {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
        int timeout = ConfigReader.getIntProperty("explicit_timeout", 15);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public void click(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (Exception e) {
            clickWithJS(locator);
        }
    }

    public void clickWithJS(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        if (text != null && !text.isEmpty()) {
            el.sendKeys(text);
        }
    }

    public void selectByVisibleText(By locator, String text) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator))).selectByVisibleText(text);
    }

    public void waitForOptionPresent(By locator, String optionText) {
        try {
            wait.until(d -> {
                Select s = new Select(d.findElement(locator));
                for (WebElement opt : s.getOptions()) {
                    if (opt.getText().trim().equalsIgnoreCase(optionText.trim())) {
                        return true;
                    }
                }
                return false;
            });
        } catch (Exception ignored) {
        }
    }

    public void selectByValue(By locator, String value) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator))).selectByValue(value);
    }

    public void scrollToElement(By locator) {
        try {
            WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
        } catch (Exception ignored) {
        }
    }

    public String getTextSafely(By locator) {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String text = el.getText();
            if (text == null || text.trim().isEmpty()) {
                text = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText || arguments[0].textContent;", el);
            }
            return text != null ? text.trim() : "";
        } catch (Exception e) {
            return "";
        }
    }

    public String getAttributeSafely(By locator, String attribute) {
        try {
            WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return el.getAttribute(attribute);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isElementDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            return !driver.findElements(locator).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForAjaxToComplete() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".fa-spinner, .spinner-border")));
        } catch (Exception ignored) {
        }
    }

    public void disableFormHtml5Validation() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "var forms = document.getElementsByTagName('form'); for(var i=0; i<forms.length; i++) { forms[i].setAttribute('novalidate', 'novalidate'); }"
            );
        } catch (Exception ignored) {
        }
    }
}

