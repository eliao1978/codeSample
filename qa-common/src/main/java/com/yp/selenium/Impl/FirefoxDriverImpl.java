package com.yp.selenium.Impl;

import com.yp.selenium.SeleniumDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class FirefoxDriverImpl extends SeleniumDriver {

    public FirefoxDriverImpl(boolean takeScreenshot, boolean remote) {
        super(takeScreenshot);
        if (remote) {
            try {
                driver = new RemoteWebDriver(new URL("http://" + System.getProperty("selenium.remote.host") + ":4444/wd/hub"), capabilities, null);
            } catch (MalformedURLException e) {
                logger.debug(e.getMessage());
            }
        } else {
            driver = new FirefoxDriver(capabilities);
        }

        driver.manage().window().maximize();
    }
}