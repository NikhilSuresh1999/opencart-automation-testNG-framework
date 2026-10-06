# OpenCart Enterprise TestNG Automation Framework

A fully modular, thread-safe, pure **TestNG** automation suite built for the OpenCart / TutorialsNinja platform. Designed with clean Page Object Model (POM), ThreadLocal WebDriver isolation, DataFaker dynamic test data generation, ExtentReports 5 Spark HTML reporting, and automatic failure retries.

---

## 🚀 Tech Stack & Dependencies
* **Language:** Java 17
* **Test Framework:** TestNG 7.9.0
* **Core Automation Engine:** Selenium WebDriver 4.18.1
* **Data Strategy:** DataFaker 2.1.0 (Dynamic, isolated user and address generation)
* **Reporting Engine:** ExtentReports 5.1.1 (SparkReporter with base64 screenshot captures on failure)
* **Build Tool:** Apache Maven 3.9+

---

## 🏗️ Architecture & Framework Design
* **Pure TestNG Implementation:** Complete standalone TestNG test classes utilizing `@Test`, `@DataProvider`, `@BeforeMethod`, `@AfterMethod`, and XML suite suites without BDD/Cucumber layers.
* **ThreadLocal Driver Management:** `DriverFactory` manages isolated WebDriver instances via `ThreadLocal<WebDriver>` for thread safety and parallel capability.
* **Page Object Model (POM):** Clean separation between page elements/actions (`com.opencart.pages`) and test logic (`com.opencart.tests`).
* **Resilient Element Util:** `ElementUtil` provides explicit synchronization, JavaScript fallback clicks/scrolls, safe text extraction, and HTML5 form validation overrides.
* **Intelligent Listeners:** 
  - `TestListener`: Hooks into TestNG execution lifecycle to generate interactive ExtentReports HTML dashboard with embedded failure screenshots.
  - `AnnotationTransformer` & `RetryAnalyzer`: Automatically applies retry logic to eliminate transient network flakes.

---

## 📂 Project Structure
```
OpenCartAutomationTestNGSuite/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/opencart/
│   │   │   ├── driver/
│   │   │   │   └── DriverFactory.java
│   │   │   ├── pages/
│   │   │   │   ├── BasePage.java
│   │   │   │   ├── StoreFrontPage.java
│   │   │   │   ├── AccountPage.java
│   │   │   │   ├── ProductDetailPage.java
│   │   │   │   ├── CartPage.java
│   │   │   │   ├── ComparisonPage.java
│   │   │   │   ├── CheckoutPage.java
│   │   │   │   ├── CustomerServicePage.java
│   │   │   │   └── AdminPage.java
│   │   │   └── utils/
│   │   │       ├── ConfigReader.java
│   │   │       └── ElementUtil.java
│   │   └── resources/config/
│   │       └── config.properties
│   └── test/
│       ├── java/com/opencart/
│       │   ├── base/
│       │   │   └── BaseTest.java
│       │   ├── listeners/
│       │   │   ├── TestListener.java
│       │   │   ├── RetryAnalyzer.java
│       │   │   └── AnnotationTransformer.java
│       │   └── tests/
│       │       ├── RegistrationAndAuthTest.java       (TC_AUTH_01 to TC_AUTH_65)
│       │       ├── ProductSearchAndCatalogTest.java   (TC_SRCH_01 to TC_SRCH_50)
│       │       ├── CategoryNavigationAndSortingTest.java (TC_CAT_01 to TC_CAT_49)
│       │       ├── ProductDetailAndReviewTest.java    (TC_PROD_01 to TC_PROD_45)
│       │       ├── ShoppingCartTest.java             (TC_CART_01 to TC_CART_50)
│       │       ├── WishlistAndComparisonTest.java     (TC_WSH_01 - 22, TC_CMP_09 - 42)
│       │       ├── CheckoutTest.java                 (TC_CHK_01 to TC_CHK_40)
│       │       ├── CustomerServiceAndStaticPagesTest.java (TC_LOC, TC_SVC, TC_INF)
│       │       └── AdminPanelTest.java               (TC_ADM_01)
│       └── resources/
│           └── testng.xml
```

---

## 🧪 Comprehensive Test Coverage (392 Scenarios)
1. **User Registration & Authentication (65 Tests):**
   - Positive/negative registration combinations, boundary validation on fields, password masking, duplicate account checks, SQL/Script sanitization, login combinations, forgotten password requests, and logout workflows.
2. **Product Search & Catalog (50 Tests):**
   - Exact catalog keyword searches, partial matching, case insensitivity, boundary/unmatched terms, advanced filters by category/subcategory/description, input retention, and result counts.
3. **Category Navigation & Sorting (49 Tests):**
   - Category and subcategory routing across all departments, sorting options (Name, Price, Rating, Model), display limits, Grid/List view toggles, empty categories, and tax pricing formats.
4. **Product Details & Customer Reviews (45 Tests):**
   - Tab switching (Description, Specification, Reviews), rating boundaries (1 to 5 stars), minimum/maximum author and review length validations, quantity updates, and direct cart/wishlist/compare additions.
5. **Shopping Cart Management (50 Tests):**
   - Adding products, dynamic badge updates, single/multi-digit quantity modifications, coupon code & gift voucher validation edge cases, shipping estimates (US, UK, CA, AU), and empty cart handling.
6. **Wishlist & Product Comparison (42 Tests):**
   - Guest user wishlist login prompts, side-by-side product comparisons, specifications verification in compare tables, and item removals.
7. **End-to-End Guest Checkout (40 Tests):**
   - Full 6-step accordion checkout flow: billing address, AJAX region/state population, flat rate shipping, terms agreement, order confirmation, and mandatory field validation.
8. **Localization, Customer Service & Static Content (50 Tests):**
   - Multi-currency switcher (USD, EUR, GBP), Contact Us form validation, Product Returns submission, Site Map tree structure, and legal information pages (About Us, Delivery, Privacy, Terms).
9. **Admin Panel Access (1 Test):**
   - Admin login handling and public lockout tolerance.

---

## ⚡ Execution Commands

### 1. Execute Complete TestNG Suite
```bash
mvn clean test
```

### 2. Execute in Headed Browser (Visible UI)
```bash
mvn clean test -Dheadless=false
```

### 3. Execute with Cross-Browser Testing (Firefox or Edge)
```bash
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

### 4. Execute a Specific Test Class
```bash
# E.g., Execute only Checkout tests
mvn clean test -Dtest=CheckoutTest

# E.g., Execute only Registration and Auth tests
mvn clean test -Dtest=RegistrationAndAuthTest
```

---

## 📊 Test Reporting
Upon completion of test execution, open the generated ExtentReports HTML dashboard in any browser:
```
test-output/ExtentReport/Index.html
```
Also, default TestNG reports are available at:
```
target/surefire-reports/index.html
```

