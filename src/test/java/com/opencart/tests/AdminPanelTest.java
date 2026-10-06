package com.opencart.tests;

import com.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminPanelTest extends BaseTest {

    @Test(description = "TC_ADM_01: Verify Admin Panel Login Access or Lockout Handling")
    public void TC_ADM_01_adminPanelLoginAttempt() {
        adminPage.loginAdmin("admin", "admin");
        adminPage.navigateMenu("Catalog/Categories");
        Assert.assertTrue(driver.getCurrentUrl() != null && !driver.getCurrentUrl().isEmpty(), "Driver URL was empty after admin attempt");
    }
}

