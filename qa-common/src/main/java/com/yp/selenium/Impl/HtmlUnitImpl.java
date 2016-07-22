package com.yp.selenium.Impl;

import com.yp.selenium.SeleniumDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.concurrent.TimeUnit;

public class HtmlUnitImpl extends SeleniumDriver {

    public HtmlUnitImpl() {
        super(false);
        capabilities.setJavascriptEnabled(false);
        driver = new HtmlUnitDriver(capabilities);
        driver.manage().timeouts().pageLoadTimeout(Integer.valueOf(System.getProperty("selenium.page.load.time")), TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
}
