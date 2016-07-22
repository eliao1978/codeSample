package com.yp.selenium.Impl;

import com.yp.selenium.SeleniumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class ChromeDriverImpl extends SeleniumDriver {

    public ChromeDriverImpl(boolean takeScreenshot, boolean remote) {
        super(takeScreenshot);
        if (remote) {
            try {
                driver = new RemoteWebDriver(new URL("http://" + System.getProperty("selenium.remote.host") + ":4444/wd/hub"), DesiredCapabilities.chrome());
            } catch (MalformedURLException e) {
                logger.debug(e.getMessage());
            }
        } else {
            System.setProperty("webdriver.chrome.driver", "../qa-common/src/main/resources/chrome/chromedriver");
            driver = new ChromeDriver(capabilities);
        }

        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1500, 1000));
    }
}