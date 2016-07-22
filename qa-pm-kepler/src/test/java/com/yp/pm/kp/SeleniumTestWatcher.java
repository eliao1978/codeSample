package com.yp.pm.kp;

import com.yp.selenium.SeleniumDriverService;
import com.yp.util.LogUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SeleniumTestWatcher extends TestWatcher {
    public Logger logger = new LogUtil().getLogger();
    private WebDriver webDriver;
    private SeleniumDriverService seleniumDriverService;
    private String testMethodName;
    private String testClassName;

    public SeleniumTestWatcher() {
    }

    @Override
    protected void succeeded(Description description) {
    }

    @Override
    protected void failed(Throwable e, Description description) {
        if (!System.getProperty("selenium.screenshot").equalsIgnoreCase("false")) {
            String fileNamePrefix = testClassName.concat(".").concat(testMethodName).concat("_");
            String pageSourceFilePath = "pagesource/".concat(fileNamePrefix).concat(String.valueOf(System.currentTimeMillis()));

            // get page source
            File pageSource = new File(pageSourceFilePath);
            try {
                logger.info("Page Source: " + pageSource.getAbsolutePath());
                BufferedWriter output = new BufferedWriter(new FileWriter(pageSource));
                output.write(webDriver.getPageSource());
                output.close();
            } catch (IOException io) {
                logger.debug(io.getMessage());
            }

            // take screenshot
            File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            File screenshotCopy = new File("screenshot/".concat(fileNamePrefix).concat(screenshot.getName().replace("screenshot", "")));
            try {
                logger.info("Screenshot: " + screenshotCopy.getAbsolutePath());
                FileUtils.copyFile(screenshot, screenshotCopy);
            } catch (IOException io) {
                logger.debug(io.getMessage());
            }
        }
    }

    @Override
    protected void finished(Description description) {
        webDriver.close();
        seleniumDriverService.quit();
    }

    public void setWebDriver(WebDriver driver) {
        this.webDriver = driver;
    }

    public void setSeleniumDriverService(SeleniumDriverService service) {
        this.seleniumDriverService = service;
    }

    public void setTestMethodName(String name) {
        this.testMethodName = name;
    }

    public void setTestClassName(String name) {
        this.testClassName = name;
    }
}
