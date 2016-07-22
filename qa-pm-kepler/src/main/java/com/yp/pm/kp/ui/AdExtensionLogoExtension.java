package com.yp.pm.kp.ui;

import com.yp.enums.selenium.LocatorTypeEnum;
import org.openqa.selenium.WebDriver;


public class AdExtensionLogoExtension extends SeleniumWrapper {

    public AdExtensionLogoExtension(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    public void clickLogoExtension(String extension) throws InterruptedException {
        click(UIMapper.EXTENSION_DROPDOWN);
        sleep();
        click(extension, LocatorTypeEnum.LINK_TEXT);
        sleep();
        waitForElementNotVisible(UIMapper.LOADING);
        sleep();
    }

    public void logoExtension() throws InterruptedException {
        clickLogoExtension("Logo Extensions");
    }

    public void clickExtensionButton() throws InterruptedException {
        waitForElementPresent(UIMapper.EXTENSION_BUTTON);
        click(UIMapper.EXTENSION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickNewLogoExtensionButton() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.NEW_EXTENSION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterLabel(String label) throws InterruptedException {
        type(UIMapper.NEW_LOGO_EXTENSION_LABEL_TEXTBOX, label, true, LocatorTypeEnum.CSS);
    }

    public void browseFile(String path) throws InterruptedException {
        browseFile(UIMapper.NEW_LOGO_EXTENSION_FILE, path);
        sleep();
    }

    public void clickSave() throws InterruptedException {
        click(UIMapper.NEW_LOGO_EXTENSION_SAVE_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCancel() {
        click(UIMapper.NEW_LOGO_EXTENSION_CANCEL_BUTTON);
    }

    public void click_Panel_Edit_Extension() throws InterruptedException {
        click(UIMapper.SITE_EXTENSION_EDIT_BUTTON, LocatorTypeEnum.XPATH);
    }

    public void click_Edit_Extension() throws InterruptedException {
        click(UIMapper.SITE_EXTENSION_EDIT_EXTENSION_BUTTON, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDeleteButton(String siteLinkExtension) throws InterruptedException {
        ;
        String xpathLocator = "//ul[@class='pm-select-list']/li/span[normalize-space(.)='" + siteLinkExtension + "']/..";
        if (!isElementVisible(xpathLocator, LocatorTypeEnum.XPATH)) {
            waitForElementVisible(xpathLocator, LocatorTypeEnum.XPATH);
        }
        hover(xpathLocator, LocatorTypeEnum.XPATH);
        click(xpathLocator + "/a[@class='delete']", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.CONFIRM_DELETE_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getLogoTableText(int row, int column) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
    }

    public boolean checkLogoHeader(String text) throws InterruptedException {
        if (isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH)) {
            return (isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH));
        } else {
            return false;
        }
    }

    public void clickTableHeader(String text) throws InterruptedException {
        if (isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH)) {
            click("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
        } else {
            throw new NullPointerException("Failed to click element + \"//tr/th[normalize-space(.)='\" + text + \"']\"");

        }
    }

    public boolean checkTableHeader(String text) throws InterruptedException {
        return isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public void clickStaticFilterButton() throws InterruptedException {
        click(UIMapper.STATIC_FILTERS, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickFilterByDateDropdown() throws InterruptedException {
        click(UIMapper.FILTER_BY_DATE_DROPDOWN, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getLogoExtensionLabelRequiredErrorText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOGO_LABEL_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String getLogoExtensionMaxImageSizeError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOGO_IMAGE_SIZE_ERROR, LocatorTypeEnum.CSS);
    }

    public String getLogoExtensionInvalidFileFormatError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOGO_INVALID_FILE_FORMAT_ERROR, LocatorTypeEnum.CSS);
    }

    public String getLogoExtensionImageRequiredErrorText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOGO_IMAGE_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String getLogoExtensionLabelTooLong() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOGO_LABEL_TOO_LONG, LocatorTypeEnum.CSS);
    }

    public void deleteLogoExtensionByRemoveButton(int row, int column) throws InterruptedException {
        click(UIMapper.SELECT_CAMPAIGN_AD_EXTENSION, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(1000);
        click(UIMapper.SITE_EXTENSION_EDIT_BUTTON, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(1000);
        click(UIMapper.EDITED_AD_EXTENSION_SAVE_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(2000);
        click("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")>input", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(2000);
        click(UIMapper.LOCATION_REMOVE_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(2000);
        click(UIMapper.DELETE_CLOSAL_MODEL_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(2000);
    }

    public void staticFilterSelectAllAdExtension() throws InterruptedException {
        click("span[items='staticFilters']>div>ul>li:nth-of-type(1)>a", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void staticFilterSelectAllButDeletedAdExtension() throws InterruptedException {
        click("span[items='staticFilters']>div>ul>li:nth-of-type(2)>a", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

}

