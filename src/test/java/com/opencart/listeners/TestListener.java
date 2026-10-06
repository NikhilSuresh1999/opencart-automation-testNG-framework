package com.opencart.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.opencart.driver.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class TestListener implements ITestListener {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public synchronized void onStart(ITestContext context) {
        if (extent == null) {
            String reportDir = "test-output" + File.separator + "ExtentReport";
            new File(reportDir).mkdirs();
            String reportPath = reportDir + File.separator + "Index.html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("OpenCart TestNG Automation Execution Report");
            spark.config().setReportName("OpenCart Enterprise TestNG Test Suite");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Environment", "OpenCart / TutorialsNinja Demo");
            extent.setSystemInfo("Framework", "TestNG Modular POM");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Object[] params = result.getParameters();
        if (params != null && params.length > 0) {
            StringBuilder sb = new StringBuilder(testName).append(" [");
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i]);
                if (i < params.length - 1) sb.append(", ");
            }
            sb.append("]");
            testName = sb.toString();
        }

        ExtentTest test = extent.createTest(testName, result.getMethod().getDescription());
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        testThread.set(test);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        ExtentTest test = testThread.get();
        if (test != null) {
            test.log(Status.PASS, "Test Passed Successfully");
        }
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        ExtentTest test = testThread.get();
        if (test != null) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
            WebDriver driver = DriverFactory.getDriver();
            if (driver != null) {
                try {
                    String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                    test.fail("Failure Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        ExtentTest test = testThread.get();
        if (test != null) {
            test.log(Status.SKIP, "Test Skipped: " + result.getThrowable());
        }
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}

