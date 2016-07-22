package com.yp.selenium.Impl;

import com.yp.selenium.SeleniumDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SafariDriverImpl extends SeleniumDriver {

    public SafariDriverImpl(boolean takeScreenshot, boolean remote) {
        super(takeScreenshot);

        capabilities.setCapability("cleanSession", true);
        capabilities.setCapability("platform", Platform.MAC);

        if (remote) {
            try {
                driver = new RemoteWebDriver(new URL("http://" + System.getProperty("selenium.remote.host") + ":4444/wd/hub"), capabilities, null);
            } catch (MalformedURLException e) {
                logger.debug(e.getMessage());
            }
        } else {
            driver = new SafariDriver(capabilities);
        }

        driver.manage().window().maximize();
    }
}