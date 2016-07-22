package com.yp.pm.kp.ui;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.enums.selenium.SelectTypeEnum;
import com.yp.util.LogUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.AWTException;

import java.util.ArrayList;
import java.util.List;

public class SeleniumWrapper {
    protected Logger logger = new LogUtil().getLogger();
    protected WebDriver driver = null;

    /**
     * @param driver selenium web driver object
     */
    protected SeleniumWrapper(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * navigate to web page
     *
     * @param url page url
     */
    public void open(String url) throws InterruptedException {
        open(url, false);
    }

    /**
     * navigate to web page
     *
     * @param url page url
     * @param ssl ssl flag
     */
    public void open(String url, boolean ssl) {
        if (ssl) {
            driver.get("https://".concat(url));
        } else {
            driver.get("http://".concat(url));
        }
    }

    /**
     * refresh web browser
     */
    public void refreshBrowser() {
        driver.navigate().refresh();
    }

    /**
     * emulate form submission action
     *
     * @param locator ui element
     */
    public void submit(String locator) {
        WebElement element = getElement(locator);

        if (element != null) {
            element.submit();
        } else {
            throw new NoSuchElementException("Form submission failed. Web element [" + locator + "]");
        }
    }

    /**
     * get text from ui element
     *
     * @param locator ui element
     * @return String text
     */
    public String getTextFromElement(String locator) {
        return getTextFromElement(locator, LocatorTypeEnum.CSS);
    }

    /**
     * get text from ui element
     *
     * @param locator ui element
     * @param type    locator type
     * @return String text
     */
    public String getTextFromElement(String locator, LocatorTypeEnum type) {
        WebElement element = getElement(locator, type);

        if (element != null) {
            return element.getText();
        } else {
            return null;
        }
    }


    public String getAttributeFromElement(String locator, LocatorTypeEnum type, String attribute) {
        WebElement element = getElement(locator, type);

        if (element != null) {
            return element.getAttribute(attribute);
        } else {
            return null;
        }
    }

    /**
     * emulate clear input text action
     *
     * @param locator ui element
     */
    public void clear(String locator) {
        clear(locator, LocatorTypeEnum.CSS);
    }

    /**
     * emulate clear input text action
     *
     * @param locator ui element
     * @param type    locator type
     */
    public void clear(String locator, LocatorTypeEnum type) {
        WebElement element = getElement(locator, type);

        if (element != null) {
            element.clear();
        } else {
            throw new NoSuchElementException("Failed to clear text field [" + locator + "]");
        }
    }

    /**
     * emulate input keyboard enter action with out specific web element associated to it
     *
     * @param text  text
     */
    public void type(String text) throws AWTException, InterruptedException {
        if (text.equalsIgnoreCase("enter")) {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_ENTER);
            Thread.sleep(50);
            r.keyRelease(KeyEvent.VK_ENTER);
        }
    }

    /**
     * emulate input text action
     *
     * @param locator ui element
     * @param text    text
     */
    public void type(String locator, String text) {
        type(locator, text, false, LocatorTypeEnum.CSS);
    }

    /**
     * emulate input text action
     *
     * @param locator ui element
     * @param text    text
     * @param type    locator type
     */
    public void type(String locator, String text, LocatorTypeEnum type) {
        type(locator, text, false, type);
    }

    /**
     * emulate input text action with clear text action
     *
     * @param locator ui element
     * @param text    text
     * @param isClear clear text flag
     * @param type    locator type
     */
    public void type(String locator, String text, boolean isClear, LocatorTypeEnum type) {
        WebElement element = getElement(locator, type);

        if (isClear) {
            element.clear();
        }
        if (element != null) {
            element.sendKeys(text);
        } else {
            throw new NoSuchElementException("Failed to input [" + text + "] into text field [" + locator + "]");
        }
    }

    /**
     * emulate click action
     *
     * @param locator ui element
     */
    public void click(String locator) {
        click(locator, LocatorTypeEnum.CSS);
    }

    /**
     * emulate click action
     *
     * @param locator ui element
     * @param type    locator type
     */
    public void click(String locator, LocatorTypeEnum type) {
        WebElement element = getElement(locator, type);

        if (type.equals(LocatorTypeEnum.JS)) {
            clickJS(locator, type);
        } else if (element != null) {
            element.click();
        } else {
            throw new NullPointerException("Failed to click element [" + locator + "]");
        }
    }

    /**
     *
     * @param locator
     * @param type
     */
    public void clickJS(String locator, LocatorTypeEnum type) {
        WebElement element = getElement(locator, type);
        if (element != null && element.isDisplayed()) {
            executeScript("arguments[0].click();", element);
        } else {
            throw new NoSuchElementException("Failed to click element [" + locator + "] using clickJS");
        }
    }

    /**
     *
     * @param locator
     */
    public void hover(String locator) {
        hover(locator, LocatorTypeEnum.CSS);
    }

    /**
     *
     * @param locator
     * @param type
     */
    public void hover(String locator, LocatorTypeEnum type) {
        WebElement element = getElement(locator, type);
        if (element != null) {
            Actions action = new Actions(driver);
            action.moveToElement(element).build().perform();
        } else {
            throw new NullPointerException("Unable to locate element for hover [" + locator + "]");
        }

    }

    /**
     *
     * @param locator
     * @param path
     */
    public void browseFile(String locator, String path) {
        WebElement browseFile = driver.findElement(By.xpath(locator));
        browseFile.sendKeys(path);
    }

    /**
     *
     * @param locator
     * @param type
     */
    public void hoverJS(String locator, LocatorTypeEnum type) {
        WebElement element = getElement(locator, type);
        String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";

        if (element != null && element.isDisplayed()) {
            executeScript(mouseOverScript, element);
        } else {
            throw new NullPointerException("Failed to hover element [" + locator + "] using hoverJS");
        }
    }

    /**
     *
     * @param loc
     * @param text
     */
    public void select(String loc, String text) {
        select(SelectTypeEnum.TEXT, loc, text);
    }

    /**
     *
     * @param script
     */
    public void executeScript(String script) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript(script);
        }
    }

    /**
     *
     * @param script
     * @param element
     */
    public void executeScript(String script, WebElement element) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript(script, element);
        }
    }

    /**
     *
     * @param type
     * @param locator
     * @param text
     */
    public void select(SelectTypeEnum type, String locator, String text) {
        WebElement element = getElement(locator);

        if (element != null) {
            Select select = new Select(element);
            for (SelectTypeEnum selectType : SelectTypeEnum.values()) {
                if (type.getValue().equalsIgnoreCase(selectType.getValue())) {
                    logger.debug("Select dropdown element [" + locator + "] using " + type.getLabel());
                    switch (type) {
                        case VALUE:
                            select.selectByValue(text);
                            break;
                        case INDEX:
                            select.selectByIndex(Integer.valueOf(text));
                            break;
                        default:
                            select.selectByVisibleText(text);
                    }
                    break;
                }
            }
        } else {
            throw new NullPointerException("Failed to select element [" + locator + "]");
        }
    }

    /**
     *
     * @param locator
     */
    public void waitForElementPresent(String locator) {
        waitForElementPresent(locator, Integer.valueOf(System.getProperty("selenium.explicitly.wait.time")), LocatorTypeEnum.CSS);
    }

    /**
     *
     * @param locator
     * @param timeOut
     * @param type
     */
    public void waitForElementPresent(String locator, int timeOut, LocatorTypeEnum type) {
        int i = 0;
        do {
            try {
                logger.debug("Waiting for element " + locator + " to present");
                i += 1;
                sleep();
            } catch (InterruptedException e) {
                logger.debug(e.getMessage());
            }

            if (i > timeOut) {
                logger.debug("Reached timeout limit [" + timeOut + "] seconds");
                throw new TimeoutException();
            }
        } while (!isElementPresent(locator, type));
    }

    /**
     *
     * @param locator
     */
    public void waitForElementNotPresent(String locator) {
        waitForElementNotPresent(locator, Integer.valueOf(System.getProperty("selenium.explicitly.wait.time")), LocatorTypeEnum.CSS);
    }

    /**
     *
     * @param locator
     * @param timeOut
     * @param type
     */
    public void waitForElementNotPresent(String locator, int timeOut, LocatorTypeEnum type) {
        int i = 0;
        while (isElementPresent(locator, type)) {
            try {
                logger.debug("Waiting for web element " + locator + " not visible");
                i += 1;
                sleep();
            } catch (InterruptedException e) {
                logger.debug(e.getMessage());
            }

            if (i > timeOut) {
                logger.debug("Reached timeout limit [" + timeOut + "] seconds");
                throw new TimeoutException();
            }
        }
    }

    /**
     *
     * @param locator
     * @throws InterruptedException
     */
    public void waitForElementVisible(String locator) throws InterruptedException {
        waitForElementVisible(locator, Integer.valueOf(System.getProperty("selenium.explicitly.wait.time")), LocatorTypeEnum.CSS);
    }

    /**
     *
     * @param locator
     * @param type
     * @throws InterruptedException
     */
    public void waitForElementVisible(String locator, LocatorTypeEnum type) throws InterruptedException {
        waitForElementVisible(locator, Integer.valueOf(System.getProperty("selenium.explicitly.wait.time")), type);
    }

    /**
     *
     * @param locator
     * @param timeOut
     * @param type
     * @throws InterruptedException
     */
    public void waitForElementVisible(String locator, int timeOut, LocatorTypeEnum type) throws InterruptedException {
        int i = 0;
        do {
            try {
                logger.debug("Waiting for web element " + locator + " visible");
                i += 1;
                sleep();
            } catch (InterruptedException e) {
                logger.debug(e.getMessage());
            }

            if (i > timeOut) {
                logger.debug("Reached timeout limit [" + timeOut + "] seconds");
                throw new TimeoutException();
            }
        } while (!isElementVisible(locator, type));
    }

    /**
     *
     * @param locator
     */
    public void waitForElementNotVisible(String locator) {
        waitForElementNotVisible(locator, Integer.valueOf(System.getProperty("selenium.explicitly.wait.time")), LocatorTypeEnum.CSS);
    }

    /**
     *
     * @param locator
     * @param type
     */
    public void waitForElementNotVisible(String locator, LocatorTypeEnum type) {
        waitForElementNotVisible(locator, Integer.valueOf(System.getProperty("selenium.explicitly.wait.time")), type);
    }

    /**
     *
     * @param locator
     * @param timeOut
     * @param type
     */
    public void waitForElementNotVisible(String locator, int timeOut, LocatorTypeEnum type) {
        int i = 0;

        try {
            sleep();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (isElementVisible(locator, type)) {
            try {
                logger.debug("Waiting for web element " + locator + " not visible");
                i += 1;
                sleep();
            } catch (InterruptedException e) {
                logger.debug(e.getMessage());
            }

            if (i > timeOut) {
                logger.debug("Reached timeout limit [" + timeOut + "] seconds");
                throw new TimeoutException();
            }
        }
    }

    /**
     *
     * @param locator
     * @return
     */
    public Boolean isElementPresent(String locator) {
        try {
            WebElement element = getElement(locator);
            logger.debug("Web element " + locator + " is present");
            return element != null;
        } catch (NoSuchElementException e) {
            return false;
        } catch (ElementNotFoundException e) {
            return false;
        }
    }

    /**
     *
     * @param locator
     * @param type
     * @return
     */
    public Boolean isElementPresent(String locator, LocatorTypeEnum type) {
        try {
            getElement(locator, type);
            logger.debug("Web element " + locator + " is present");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (ElementNotFoundException e) {
            return false;
        }
    }

    /**
     *
     * @param locator
     * @return
     */
    public Boolean isElementVisible(String locator) {
        try {
            WebElement element = getElement(locator);
            logger.debug("Web element " + locator + " is present");
            return (element != null && element.isDisplayed());
        } catch (NoSuchElementException e) {
            return false;
        } catch (ElementNotFoundException e) {
            return false;
        }
    }

    /**
     *
     * @param locator
     * @param type
     * @return
     */
    public Boolean isElementVisible(String locator, LocatorTypeEnum type) {
        try {
            WebElement element = getElement(locator, type);
            logger.debug("Web element " + locator + " is present");
            return (element != null && element.isDisplayed());
        } catch (NoSuchElementException e) {
            return false;
        } catch (ElementNotFoundException e) {
            return false;
        }
    }

    /**
     *
     * @param locator
     * @return
     */
    public Boolean isElementEnabled(String locator) {
        try {
            WebElement element = getElement(locator);
            logger.debug("Web element " + locator + " is enabled");
            return (element != null && element.isEnabled());
        } catch (NoSuchElementException e) {
            return false;
        } catch (ElementNotFoundException e) {
            return false;
        }
    }

    /**
     *
     * @throws InterruptedException
     */
    public void sleep() throws InterruptedException {
        Thread.sleep(Long.valueOf(System.getProperty("selenium.implicitly.wait.time")) * 1000);
    }

    /**
     *
     * @param millis
     * @throws InterruptedException
     */
    public void sleep(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    /**
     *
     * @param locator
     * @return
     */
    public List<WebElement> getElements(String locator) {
        return getElements(locator, LocatorTypeEnum.CSS);
    }

    /**
     *
     * @param locator
     * @param type
     * @return
     */
    public List<WebElement> getElements(String locator, LocatorTypeEnum type) {
        return driver.findElements(getLocator(locator, type));
    }

    /**
     *
     * @param locator
     * @return
     */
    private WebElement getElement(String locator) {
        return getElement(locator, LocatorTypeEnum.CSS);
    }

    /**
     *
     * @param locator
     * @param type
     * @return
     */
    private WebElement getElement(String locator, LocatorTypeEnum type) {
        try {
            if (type == LocatorTypeEnum.JS) {
                Thread.sleep(100);
                return (WebElement) ((JavascriptExecutor) driver).executeScript(locator);
            } else {
                Thread.sleep(1000);
                return driver.findElement(getLocator(locator, type));
            }
        } catch (NoSuchElementException | ElementNotFoundException | InterruptedException e) {
            logger.debug(e.getMessage());
        }

        return null;
    }

    /**
     *
     * @param locator
     * @param type
     * @return
     */
    private By getLocator(String locator, LocatorTypeEnum type) {
        By loc;

        switch (type) {
            case NAME:
                loc = By.name(locator);
                break;
            case ID:
                loc = By.id(locator);
                break;
            case XPATH:
                loc = By.xpath(locator);
                break;
            case LINK_TEXT:
                loc = By.linkText(locator);
                break;
            default:
                loc = By.cssSelector(locator);
        }

        return loc;
    }

    /**
     *
     * @return
     */
    public String getUrl() {
        return driver.getCurrentUrl();
    }
}