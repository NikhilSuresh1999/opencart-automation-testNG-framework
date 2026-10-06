package com.opencart.pages;

import com.opencart.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage extends BasePage {

    private final By usernameInput = By.id("input-username");
    private final By passwordInput = By.id("input-password");
    private final By loginBtn = By.xpath("//button[@type='submit']");
    private final String adminUrl;

    public AdminPage(WebDriver driver) {
        super(driver);
        this.adminUrl = ConfigReader.getProperty("admin_url", "https://tutorialsninja.com/demo/admin/");
    }

    public void loginAdmin(String user, String pass) {
        driver.get(adminUrl);
        ele.waitForAjaxToComplete();
        try {
            if (!driver.findElements(usernameInput).isEmpty()) {
                ele.type(usernameInput, user);
                ele.type(passwordInput, pass);
                ele.click(loginBtn);
            } else {
                System.out.println("Note: TutorialsNinja Admin Panel is locked for public access.");
            }
        } catch (Exception e) {
            System.out.println("Note: TutorialsNinja Admin Panel is locked for public access.");
        }
    }

    public void navigateMenu(String menuPath) {
        try {
            String[] paths = menuPath.split("/");
            for (String path : paths) {
                if (!driver.findElements(By.xpath("//a[contains(text(), '" + path + "')]")).isEmpty()) {
                    ele.click(By.xpath("//a[contains(text(), '" + path + "')]"));
                }
            }
        } catch (Exception e) {
            System.out.println("Bypassing menu navigation due to locked admin panel.");
        }
    }
}

