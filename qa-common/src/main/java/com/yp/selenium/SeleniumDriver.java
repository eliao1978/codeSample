package com.yp.selenium;

import com.yp.util.LogUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class SeleniumDriver implements SeleniumDriverService {

    protected Logger logger = new LogUtil().getLogger();
    protected WebDriver driver = null;
    protected DesiredCapabilities capabilities = new DesiredCapabilities();

    protected SeleniumDriver(boolean takeScreenshot) {
        capabilities.setBrowserName(System.getProperty("selenium.driver"));
        capabilities.setPlatform(Platform.ANY);
        capabilities.setCapability("takesScreenshot", String.valueOf(takeScreenshot));
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public void quit() {
        driver.quit();
    }

    public void setImplicitWaitTimeout(long seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS).setScriptTimeout(seconds, TimeUnit.SECONDS);
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public void setPageLoadTimeout(long seconds){
        driver.manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
    }
}
