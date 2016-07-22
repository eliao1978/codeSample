package com.yp.pm.kp.ui;


import com.yp.enums.selenium.LocatorTypeEnum;
import org.openqa.selenium.WebDriver;

public class AdExtensionCallExtension extends SeleniumWrapper {

    public AdExtensionCallExtension(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    public void callExtension() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        clickCallExtension("Call Extensions");
    }

    public void click_Panel_Edit_Extension() throws InterruptedException {
        click(UIMapper.SITE_EXTENSION_EDIT_BUTTON, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void selectCallExtension(String text) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click("//tr/td/span[normalize-space(.)='" + text + "']/../../td/input[@type='checkbox']", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void click_Ad_Extension_Edit() throws InterruptedException {
        click(UIMapper.AD_EXTENSION_EDIT, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void click_Edit_Extension() throws InterruptedException {
        click(UIMapper.SITE_EXTENSION_EDIT_EXTENSION_BUTTON, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDeleteButton(String siteLinkExtension) throws Exception {
        waitForElementNotVisible(UIMapper.LOADING);
        if (System.getProperty("selenium.driver").equalsIgnoreCase("safari")) {
            hoverJS(UIMapper.SITE_EXTENSION_EDIT_BUTTON, LocatorTypeEnum.XPATH);
        } else {
            hover(UIMapper.SITE_EXTENSION_EDIT_BUTTON, LocatorTypeEnum.XPATH);
        }
        executeScript("(function() { ul = document.getElementsByTagName('ul'); for (var i = 0; i < ul.length; i++) {if(ul[i].innerHTML.indexOf('" + siteLinkExtension + "')>=0) { ul[i].getElementsByTagName('a')[0].setAttribute('id','deleteExtension')}  } } )();");
        click("deleteExtension", LocatorTypeEnum.ID);
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.CONFIRM_DELETE_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickRemoveButton() throws Exception {
        click(UIMapper.AD_EXTENSION_REMOVE_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void confirmDelete() throws Exception {
        click(UIMapper.AD_EXTENSION_CONFIRM_DELETE);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCallExtension(String extension) throws InterruptedException {
        click(UIMapper.EXTENSION_DROPDOWN);
        sleep();
        waitForElementNotVisible(UIMapper.LOADING);
        sleep();
        click(extension, LocatorTypeEnum.LINK_TEXT);
        sleep();
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void click_EXTENSION_BUTTON() throws InterruptedException {
        click(UIMapper.EXTENSION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void click_New_Call_Extension_BUTTON() throws InterruptedException {
        click(UIMapper.NEW_EXTENSION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterPhone(String Phone) throws InterruptedException {
        type(UIMapper.NEW_CALL_EXTENSION_PHONENUMBER, String.valueOf(Phone), true, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickSaveButton() throws InterruptedException {
        click(UIMapper.NEW_CALL_EXTENSION_SAVE_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCancelButton() throws InterruptedException {
        click(UIMapper.AD_EXTENSION_CANCEL_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getCallTableText(int row, int column) throws InterruptedException {
        getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
    }

    public boolean checkCallHeader(String text) throws InterruptedException {
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

    public String getCallExtensionPhoneRequiredErrorText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_CALLEXTENSION_ERROR, LocatorTypeEnum.CSS);
    }

    public String getCallExtensionPhoneNumberTooLongErrorText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_CALL_EXTENSION_TOO_LONG_ERROR, LocatorTypeEnum.CSS);
    }

    public void checkNoRecordsToLoad() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.NO_RECORDS_TO_LOAD);
    }

    public void deleteCallExtensionByRemoveButton(int row, int column) throws InterruptedException {
        click(UIMapper.SELECT_CAMPAIGN_AD_EXTENSION, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.SITE_EXTENSION_EDIT_BUTTON, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.EDITED_AD_EXTENSION_SAVE_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        refreshBrowser();
        waitForElementNotVisible(UIMapper.LOADING);
        click("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")>input", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.LOCATION_REMOVE_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.DELETE_CLOSAL_MODEL_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void selectStaticFilterAllAdExtension() throws InterruptedException {
        click("span[items='staticFilters']>div>ul>li:nth-of-type(1)>a", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void selectStaticFilterAllButDeletedAdExtension() throws InterruptedException {
        click("span[items='staticFilters']>div>ul>li:nth-of-type(2)>a", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

}