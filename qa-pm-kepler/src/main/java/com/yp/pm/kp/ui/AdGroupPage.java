package com.yp.pm.kp.ui;

import com.yp.enums.selenium.LocatorTypeEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AdGroupPage extends SeleniumWrapper {

    public AdGroupPage(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    public void clickCreateAdGroupButton() throws Exception {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.CREATE_AD_GROUP_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void loadingStatus() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterAdGroupName(String text) {
        waitForElementNotVisible(UIMapper.LOADING);
        type(UIMapper.AD_GROUP_NAME_TEXT_BOX, text);
    }

    public void enterHeadline(String text) {
        type(UIMapper.HEADLINE_TEXT_BOX, text);
    }

    public void enterDescription1(String text) {
        type(UIMapper.DESCRIPTION1_TEXT_BOX, text);
    }

    public void enterDescription2(String text) {
        type(UIMapper.DESCRIPTION2_TEXT_BOX, text);
    }

    public void enterDisplayUrl(String text) {
        type(UIMapper.DISPLAY_URL_TEXT_BOX, text);
    }

    public void enterDestinationUrl(String text) {
        type(UIMapper.DESTINATION_URL_TEXT_BOX, text);
    }

    public void enterKeyword(String text) {
        type(UIMapper.KEYWORD_TEXT_BOX, text);
    }

    public void enterAdGroupBid(Double amount) {
        type(UIMapper.AD_GROUP_BID_TEXT_BOX, String.valueOf(amount));
    }

    public void clickSubmitButton() throws Exception {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.SUBMIT_AD_GROUP);
        sleep();
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCancelButton() {
        click(UIMapper.CANCEL_AD_GROUP);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void selectAdGroup(String text) throws InterruptedException {
        String xpathLocator = "//td/a[normalize-space(.)='" + text + "']/../../td/input";
        if (!isElementPresent(xpathLocator, LocatorTypeEnum.XPATH)) {
            waitForElementNotVisible(UIMapper.LOADING);
        }
        click(xpathLocator, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDeleteAdGroup() throws InterruptedException {
        click(UIMapper.EDIT_STATUS_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.DELETE_STATUS_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void searchAdGroup(String name) throws InterruptedException {
        type(UIMapper.SEARCH_AD_GROUP_TEXT_BOX, name);
        click(UIMapper.AD_GROUP_SEARCH_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public ArrayList<String> getTableColumn() {
        waitForElementNotVisible(UIMapper.LOADING);
        List<WebElement> column = driver.findElements(By.xpath(UIMapper.AD_GROUP_TABLE_COLUMN));
        ArrayList<String> columnText = new ArrayList<>();
        for (WebElement element : column) {
            if (!element.getText().equals("") && !element.getText().toLowerCase().contains("total -")) {
                columnText.add(element.getText());
            }
        }
        return columnText;
    }

    public boolean checkAdGroupHeader(String text) throws InterruptedException {
        return isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public String getAdGroupTableText(int row, int column) throws InterruptedException {
        getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
    }

    public void clickTableHeader(int row) throws InterruptedException {
        click(UIMapper.ADEXTENSION_TABLE_HEAD + "(" + row + ")", LocatorTypeEnum.CSS);
    }

    public void selectAdGroupDropdown(int i) throws Exception {
        String[] siteLinkText = {"All ad groups", "All enabled ad groups", "All but deleted ad groups"};
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(2000);
        click(UIMapper.ADGROUP_FILTER_DROPDOWN, LocatorTypeEnum.CSS);
        sleep(3000);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(4000);
        click(siteLinkText[i], LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(4000);
    }

    public void updateAdGroupDropdown(int i) throws Exception {
        String[] siteLinkText = {"Enable", "Pause", "Delete"};
        click("div[id='adGroupEdits']>span[class='dropdown']>button[class='btn btn-xs btn-default dropdown-toggle']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(4000);
        click(siteLinkText[i], LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(3000);
        if (!isElementVisible(UIMapper.TABLE_BODY_VALID)) {
            waitForElementNotVisible(UIMapper.LOADING);
            sleep(2000);
        }
    }

    public void clickFilterButton() throws InterruptedException {
        click(UIMapper.ADGROUP_FILTER_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void selectCheckBox(int row, int column) throws InterruptedException {
        click("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")>input", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public double getTableSize() throws InterruptedException {
        List<WebElement> rowCollection = driver.findElements(By.cssSelector("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr"));
        return rowCollection.size();
    }

    public void clickAdGroupFilterButton() throws InterruptedException {
        click(UIMapper.ADGROUP_FILTER_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(2000);
    }

    public void clickDownloadButton() throws InterruptedException {
        click(UIMapper.DOWNLOAD_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickShowChartButton() throws InterruptedException {
        click(UIMapper.SHOW_CHART_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickFilterByDateDropdown() throws InterruptedException {
        click(UIMapper.FILTER_BY_DATE_DROPDOWN, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public boolean checkTableHeader(String text) throws InterruptedException {
        return isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public String getAdGroupNameTooLongErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADGROUP_NAME_TOO_LONG_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getAdGroupNameDuplicateErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADGROUP_DUPLICATE_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getBidRequiredErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.BID_REQUIRED_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getBidTooLargeErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.BID_TOO_LARGE_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getAdGroupCampaignRequiredError() throws InterruptedException {
        return getTextFromElement(UIMapper.AD_GROUP_CAMPAIGN_REQUIRED_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getAdGroupBidTooLowError() throws InterruptedException {
        return getTextFromElement(UIMapper.BID_TOO_SMALL_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getClicks() throws InterruptedException {
        return getTextFromElement(UIMapper.SUMMARY_STATS + ">td:nth-of-type(6)>span", LocatorTypeEnum.CSS);
    }

    public String getImpressions() throws InterruptedException {
        return getTextFromElement(UIMapper.SUMMARY_STATS + ">td:nth-of-type(7)>span", LocatorTypeEnum.CSS);
    }

    public String getConversions() throws InterruptedException {
        return getTextFromElement(UIMapper.SUMMARY_STATS + ">td:nth-of-type(12)>span", LocatorTypeEnum.CSS);
    }

    public String getConversionCost() throws InterruptedException {
        return getTextFromElement(UIMapper.SUMMARY_STATS + ">td:nth-of-type(13)>span", LocatorTypeEnum.CSS);
    }

    public void clickToday() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.DATE_RANGE_BUTTON);
        click(UIMapper.DATE_RANGE_TODAY, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }
}