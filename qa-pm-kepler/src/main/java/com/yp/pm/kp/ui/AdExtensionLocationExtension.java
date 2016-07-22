package com.yp.pm.kp.ui;

import com.yp.enums.selenium.LocatorTypeEnum;
import org.openqa.selenium.WebDriver;


public class AdExtensionLocationExtension extends SeleniumWrapper {

    public AdExtensionLocationExtension(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    public void click_Panel_Edit_Extension() throws InterruptedException {
        click(UIMapper.SITE_EXTENSION_EDIT_BUTTON, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void click_Edit_Extension() throws InterruptedException {
        click(UIMapper.SITE_EXTENSION_EDIT_EXTENSION_BUTTON, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDeleteButton(String siteLinkExtension) throws InterruptedException {
        if (System.getProperty("selenium.driver").equalsIgnoreCase("safari")) {
            hoverJS(UIMapper.SITE_EXTENSION_EDIT_BUTTON, LocatorTypeEnum.XPATH);
        } else {
            hover(UIMapper.SITE_EXTENSION_EDIT_BUTTON, LocatorTypeEnum.XPATH);
        }
        Thread.sleep(1000);
        executeScript("(function() { ul = document.getElementsByTagName('ul'); for (var i = 0; i < ul.length; i++) {if(ul[i].innerHTML.indexOf('" + siteLinkExtension + "')>=0) { ul[i].getElementsByTagName('a')[0].setAttribute('id','deleteExtension')}  } } )();");
        click("deleteExtension", LocatorTypeEnum.ID);
        Thread.sleep(1000);
        click(UIMapper.DELETE_CLOSAL_MODEL_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(2000);
    }


    public void click_EXTENSION_BUTTON() throws InterruptedException {
        waitForElementPresent(UIMapper.EXTENSION_BUTTON);
        click(UIMapper.EXTENSION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void click_New_Location_Extension_BUTTON() throws InterruptedException {
        click(UIMapper.NEW_EXTENSION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }


    public void clickStaticFilterButton() throws InterruptedException {
        click(UIMapper.STATIC_FILTERS, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickFilterByDateDropdown() throws InterruptedException {
        click(UIMapper.FILTER_BY_DATE_DROPDOWN, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void importExtension(String path) throws InterruptedException {
        click("div[class='pm-tab-content ng-scope']>div[id='cmpExtensionEdit']>button[class='btn btn-xs btn-primary'][ng-click='importAdExtension()']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        browseFile("/html/body/div[2]/div[2]/div[2]/div[2]/div/div/div[6]/div/div[1]/form/input", path);
        waitForElementNotVisible(UIMapper.LOADING);
        click("form[class='ng-pristine ng-valid']>div[class='pm-actions']>button[ng-click='importAdExtensionFile(uploadingFile)']", LocatorTypeEnum.CSS);
        Thread.sleep(120000);
        waitForElementNotVisible(UIMapper.LOADING);
        click("div[class='pm-filters']>button[class='btn btn-xs btn-default'][ng-click='reloadAdExtension()']", LocatorTypeEnum.CSS);
    }

    public void locationExtension(String extension) throws InterruptedException {
        click(UIMapper.EXTENSION_DROPDOWN);
        sleep();
        waitForElementNotVisible(UIMapper.LOADING);
        sleep();
        click(extension, LocatorTypeEnum.LINK_TEXT);
        sleep();
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void locationExtension() throws InterruptedException {
        locationExtension("Location Extensions");
    }

    public void staticFilterSelectAllAdExtension() throws InterruptedException {
        click("span[items='staticFilters']>div>ul>li:nth-of-type(1)>a", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }


    public void staticFilterSelectAllButDeletedAdExtension() throws InterruptedException {
        click("span[items='staticFilters']>div>ul>li:nth-of-type(2)>a", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }


    public void enterAddressLineOne(String addressLine1) throws InterruptedException {
        type(UIMapper.NEW_LOCATION_ADDRESS_LINE_ONE_TEXT_BOX, addressLine1, true, LocatorTypeEnum.CSS);
    }

    public void enterAddressLineTwo(String addressLine2) throws InterruptedException {
        type(UIMapper.NEW_LOCATION_ADDRESS_LINE_TWO_TEXT_BOX, addressLine2, true, LocatorTypeEnum.CSS);
    }

    public void enterCity(String city) throws InterruptedException {
        type(UIMapper.NEW_LOCATION_CITY_TEXT_BOX, city, true, LocatorTypeEnum.CSS);
    }

    public void enterState(String state) throws InterruptedException {
        type(UIMapper.NEW_LOCATION_STATE_TEXT_BOX, state, true, LocatorTypeEnum.CSS);
    }

    public void enterZip(String zip) throws InterruptedException {
        type(UIMapper.NEW_LOCATION_ZIP_TEXT_BOX, String.valueOf(zip), true, LocatorTypeEnum.CSS);
    }

    public void enterCompanyName(String company) throws InterruptedException {
        type(UIMapper.NEW_LOCATION_COMPANY_NAME_TEXT_BOX, company, true, LocatorTypeEnum.CSS);
    }

    public void enterPhoneNumber(String phone) throws InterruptedException {
        type(UIMapper.NEW_LOCATION_PHONE_NUMBER_TEXT_BOX, String.valueOf(phone), true, LocatorTypeEnum.CSS);
    }

    public void saveButton() throws InterruptedException {
        click(UIMapper.NEW_LOCATION_SAVE_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void cancelButton() {
        click(UIMapper.AD_EXTENSION_CANCEL_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void checkForAddressError() throws Exception {
        if (isElementVisible("//div[contains(@class, 'error') and text() = 'Unable to find latitude coordinate from the given address']", LocatorTypeEnum.XPATH)) {
            logger.info("Unable to find latitude and longitude coordinate from the given address");
            throw new Exception("Unable to find latitude and longitude coordinate from the given address");
        }
    }

    public String getLocationTableText(int row, int column) throws InterruptedException {
        return getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
    }

    public void locationExtensionDeleteByRemoveButton(int row, int column) throws InterruptedException {
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


    public void locationExtensionClickEditAlertText(int row, int column) throws InterruptedException {
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
        click(UIMapper.LOCATION_EXTENSION_EDIT_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String validateEditButtonModalTextLine(int lineNumber) throws InterruptedException {
        return getTextFromElement("div[class='modal-content']>div[class='modal-body ng-scope']>p:nth-of-type" + "(" + lineNumber + ")", LocatorTypeEnum.CSS);
    }

    public boolean checkLocationHeader(String text) throws InterruptedException {
        if (isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH)) {
            return (isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH));
        } else {
            return false;
        }
    }

    public void clickTableHeader(String text) throws InterruptedException {
        if (isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH)) {
            click("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
            waitForElementNotVisible(UIMapper.LOADING);
        } else {
            throw new NullPointerException("Failed to click element + \"//tr/th[normalize-space(.)='\" + text + \"']\"");
        }
    }

    public boolean checkTableHeader(String text) throws InterruptedException {
        return isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public String getLocationExtensionAddressErrorText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOCATION_ADDRESS_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String getLocationExtensionCityErrorText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOCATION_CITY_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String getLocationExtensionStateErrorText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOCATION_STATE_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String getAddress1TooLongError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOCATION_ADDRESS1_TOO_LONG__ERROR, LocatorTypeEnum.CSS);
    }

    public String getAddress2TooLongError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOCATION_ADDRESS2_TOO_LONG__ERROR, LocatorTypeEnum.CSS);
    }

    public String getCityTooLongError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOCATION_CITY_TOO_LONG_ERROR, LocatorTypeEnum.CSS);
    }

    public String getStateTooLongError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOCATION_STATE_TOO_LONG_ERROR, LocatorTypeEnum.CSS);
    }

    public String getZipcodeTooLongError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOCATION_ZIP_CODE_TOO_LONG_ERROR, LocatorTypeEnum.CSS);
    }

    public String getInvalidZipCodeError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOCATION_ZIP_CODE_INVALID_ERROR, LocatorTypeEnum.CSS);
    }

    public String getPhoneTooLongError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOCATION_PHONE_TOO_LONG_ERROR, LocatorTypeEnum.CSS);
    }

    public String getLocationExtensionZipcodeErrorText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADEXTENSION_LOCATION_ZIPCODE_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

}