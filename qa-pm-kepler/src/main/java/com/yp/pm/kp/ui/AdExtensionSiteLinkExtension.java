package com.yp.pm.kp.ui;

import com.yp.enums.selenium.LocatorTypeEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.lang.String;
import java.awt.AWTException;

import java.util.List;


public class AdExtensionSiteLinkExtension extends SeleniumWrapper {

    public AdExtensionSiteLinkExtension(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    public void clickPanelEditExtension() throws InterruptedException {
        click(UIMapper.SITE_EXTENSION_EDIT_BUTTON, LocatorTypeEnum.XPATH);
    }

    public void clickSiteLinKEditExtension() throws InterruptedException {
        click(UIMapper.SITE_EXTENSION_EDIT_EXTENSION_BUTTON, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void deleteSiteLinkExtension() {
        click(UIMapper.DELETE_CLOSAL_MODEL_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickExtensionButton() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.EXTENSION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDownloadButton() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.DOWNLOAD_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDownloadButtonListOptions(String option) throws InterruptedException {
        if (option.equalsIgnoreCase("excelCsv")) {
            waitForElementNotVisible(UIMapper.LOADING);
            click(UIMapper.DOWNLOAD_BUTTON_XLCSV, LocatorTypeEnum.XPATH);
            waitForElementNotVisible(UIMapper.LOADING);
        }
        else if (option.equalsIgnoreCase("csv")) {
            waitForElementNotVisible(UIMapper.LOADING);
            click(UIMapper.DOWNLOAD_BUTTON_CSV, LocatorTypeEnum.XPATH);
            waitForElementNotVisible(UIMapper.LOADING);
        }
        else if (option.equalsIgnoreCase("excelCsvGz")){
            waitForElementNotVisible(UIMapper.LOADING);
            click(UIMapper.DOWNLOAD_BUTTON_XLCSVGZ, LocatorTypeEnum.XPATH);
            waitForElementNotVisible(UIMapper.LOADING);
        }
        else if (option.equalsIgnoreCase("csvGz")){
            waitForElementNotVisible(UIMapper.LOADING);
            click(UIMapper.DOWNLOAD_BUTTON_CSVGZ, LocatorTypeEnum.XPATH);
            waitForElementNotVisible(UIMapper.LOADING);
        }
    }
    public void sendKeyPress (String text) throws AWTException, InterruptedException {
        String focusString = "window.focus();";
        executeScript(focusString);
        type(text);
        Thread.sleep(1000);
    }

    public void clickNewSiteLinkExtensionButton() throws InterruptedException {
        click(UIMapper.NEW_EXTENSION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickSaveButton() throws InterruptedException {
        click(UIMapper.SAVE_NEW_SITE_LINK);
        waitForElementNotVisible("//div[@class='modal-header ng-scope/h5[normalize-space(.)='New Sitelink']", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCancelButton() {
        click(UIMapper.AD_EXTENSION_CANCEL_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterLinkText(String name) throws InterruptedException {
        type(UIMapper.SITELINKTEXT, name, true, LocatorTypeEnum.CSS);
    }

    public void enterLinkurl(String url) throws InterruptedException {
        type(UIMapper.SITELINKURL, url, true, LocatorTypeEnum.CSS);
    }

    public String getSiteLinkTableText(int row, int column) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
    }

    public void selectSiteLinkDropDownValues(int i) throws InterruptedException {
        String[] siteLinkText = {"Clicks", "Impr.", "Avg. CPC", "Cost", "Avg. Pos.", "CTR"};
        click(UIMapper.ADEXTENSION_FILTER_DROPDOWN, LocatorTypeEnum.CSS);
        sleep();
        click(siteLinkText[i], LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    /*
    public void deleteSiteLinkExtension() {
        click(UIMapper.DELETE_CLOSAL_MODEL_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }
    */

    public void clickFilter() throws InterruptedException {
        click(UIMapper.SITELINK_FILTER_BUTTON, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);

    }

    public boolean checkSiteLinkHeader(String text) throws InterruptedException {
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

    public void clickSiteLinkFilterButton() throws InterruptedException {
        click(UIMapper.SITELINK_FILTER_BUTTON, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickAdExtensionFilterButton() throws InterruptedException {
        click(UIMapper.ADEXTENSION_FILTER_DROPDOWN, LocatorTypeEnum.CSS);
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

    public boolean checkTableHeader(String text) throws InterruptedException {
        return isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public String getSiteLinkExtensionLinkTextErrorText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.SITELINK_LINK_TEXT_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String getSiteLinkExtensionLinkUrlErrorText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.SITELINK_LINK_URL_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String validateUrl(int row, int column) throws InterruptedException {
        hover("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
        return getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(2)>td:nth-of-type(2)>div[class='dropdown sitelink-menu ng-scope']>div[class='dropdown-menu ng-binding']>a[class='ng-binding']", LocatorTypeEnum.CSS);

    }

    public void deleteSiteLinkExtensionByRemoveButton(int row, int column) throws InterruptedException {
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

    public void clickDeleteButton(String siteLinkExtension) throws InterruptedException {
        Thread.sleep(2000);
        if (System.getProperty("selenium.driver").equalsIgnoreCase("safari")) {
            hoverJS(UIMapper.SITE_EXTENSION_EDIT_BUTTON, LocatorTypeEnum.XPATH);
        } else {
            hover(UIMapper.SITE_EXTENSION_EDIT_BUTTON, LocatorTypeEnum.XPATH);
        }
        Thread.sleep(1000);
        executeScript("(function() { ul = document.getElementsByTagName('ul'); for (var i = 0; i < ul.length; i++) {if(ul[i].innerHTML.indexOf('" + siteLinkExtension + "')>=0) { ul[i].getElementsByTagName('a')[0].setAttribute('id','deleteExtension')}  } } )();");
        click("deleteExtension", LocatorTypeEnum.ID);
        Thread.sleep(1000);
    }

    public void selectStaticFilterAllAdExtension() throws InterruptedException {
        click("span[items='staticFilters']>div>ul>li:nth-of-type(1)>a", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void selectStaticFilterAllButDeletedAdExtension() throws InterruptedException {
        click("span[items='staticFilters']>div>ul>li:nth-of-type(2)>a", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getLinkTextTooLongError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.SITELINK_TEXT_TOO_LONG_ERROR, LocatorTypeEnum.CSS);
    }

    public String getLinkUrlTooLongError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.SITELINK_URL_TOO_LONG_ERROR, LocatorTypeEnum.CSS);
    }

    public String getInvalidLinkUrlError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.SITELINK_LINK_URL_INVALID_CHARACTERS_ERROR, LocatorTypeEnum.CSS);
    }

    public double getTableSize() throws InterruptedException {
        List<WebElement> rowCollection = driver.findElements(By.cssSelector("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr"));
        return rowCollection.size();
    }

    public String getError() throws InterruptedException {
        String path = "form[name='newSitelinkExtForm']>div>div>div[class='error ng-binding']";
        waitForElementVisible(path);
        return getTextFromElement(path);
    }

}