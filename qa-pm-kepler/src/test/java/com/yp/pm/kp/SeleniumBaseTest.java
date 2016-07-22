package com.yp.pm.kp;

import com.yp.selenium.Impl.ChromeDriverImpl;
import com.yp.selenium.Impl.FirefoxDriverImpl;
import com.yp.selenium.Impl.HtmlUnitImpl;
import com.yp.selenium.Impl.SafariDriverImpl;
import com.yp.selenium.SeleniumDriverService;
import com.yp.enums.selenium.SeleniumDriverTypeEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;

public class SeleniumBaseTest extends BaseBeTest {

    protected WebDriver webDriver = null;
    protected SeleniumDriverService seleniumDriver = null;

    @Rule
    public SeleniumTestWatcher testWatcher = new SeleniumTestWatcher();

    @Before
    public void start() throws Exception {
        boolean takeScreenshot = (!System.getProperty("selenium.screenshot").equalsIgnoreCase("false"));
        boolean remote = (!System.getProperty("selenium.remote").equalsIgnoreCase("false"));

        // config Selenium driver
        SeleniumDriverTypeEnum type = SeleniumDriverTypeEnum.fromValue(System.getProperty("selenium.driver"));
        logger.debug("Driver Type: " + type.getLabel());
        switch (type) {
            case HTMLUNIT:
                seleniumDriver = new HtmlUnitImpl();
                break;
            case PHANTOMJS:
                break;
            case FIREFOX:
                seleniumDriver = new FirefoxDriverImpl(takeScreenshot, remote);
                break;
            case SAFARI:
                seleniumDriver = new SafariDriverImpl(takeScreenshot, remote);
                break;
            default:
                seleniumDriver = new ChromeDriverImpl(takeScreenshot, remote);
        }

        seleniumDriver.deleteAllCookies();
        seleniumDriver.setImplicitWaitTimeout(Long.valueOf(System.getProperty("selenium.implicitly.wait.time")));

        if (type != SeleniumDriverTypeEnum.SAFARI) {
            seleniumDriver.setPageLoadTimeout(Long.valueOf(System.getProperty("selenium.page.load.time")));
        }

        // config testWatcher
        webDriver = seleniumDriver.getWebDriver();
        testWatcher.setWebDriver(webDriver);
        testWatcher.setSeleniumDriverService(seleniumDriver);
        testWatcher.setTestClassName(this.getClass().getName());
        testWatcher.setTestMethodName(testName.getMethodName());
    }

    @After
    public void end() throws Exception {
        testWatcher = null;
    }
}