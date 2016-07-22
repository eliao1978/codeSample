package com.yp.selenium;

import org.openqa.selenium.WebDriver;

public interface SeleniumDriverService {
    void quit();

    void setImplicitWaitTimeout(long seconds);

    void setPageLoadTimeout(long seconds);

    void deleteAllCookies();

    WebDriver getWebDriver();
}