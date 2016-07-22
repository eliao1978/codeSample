package com.yp.pm.kp.ui;

import com.yp.enums.selenium.LocatorTypeEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SettingsPage extends SeleniumWrapper {
    int attempts = 0;
    int Max_Wait = 4;

    public SettingsPage(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    public void clickSaveLocationButton() throws Exception {
        waitForElementNotVisible(UIMapper.LOADING);
        clickJS(UIMapper.SAVE_LOCATION_BUTTON,LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.SAVE_LOCATION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickLocationsCheckall() throws InterruptedException
    {
        waitForElementVisible(UIMapper.LOCATIONS_CHECKALL,LocatorTypeEnum.XPATH);
        click(UIMapper.LOCATIONS_CHECKALL,LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickAdScheduleCheckall() throws InterruptedException
    {
        waitForElementVisible(UIMapper.ADSCHEDULES_CHECKALL,LocatorTypeEnum.XPATH);
        click(UIMapper.ADSCHEDULES_CHECKALL,LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickLocationsTab() throws InterruptedException {
        waitForElementVisible(UIMapper.LOCATIONS_TAB);
        click(UIMapper.LOCATIONS_TAB);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void createAdSchedule(String day, String startTime, String endTime) throws InterruptedException {
        waitForElementVisible(UIMapper.ADD_SCHEDULE_BUTTON);
        click(UIMapper.ADD_SCHEDULE_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        select(UIMapper.AD_SCHEDULE_DAY_SELECT, day);
        waitForElementNotVisible(UIMapper.LOADING);
        select(UIMapper.SCHEDULE_START_TIME_SELECT, startTime);
        waitForElementNotVisible(UIMapper.LOADING);
        select(UIMapper.SCHEDULE_END_TIME_SELECT, endTime);
    }

    public void clickAdScheduleTab() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click("Ad schedule", LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCancelAdSchedule() throws InterruptedException {
        click(UIMapper.CANCEL_SCHEDULE_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public boolean geoLocationInSettingsTabHeader(String text) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public void clickSaveCampaignLocationButton() throws InterruptedException {
        click(UIMapper.SAVE_CAMPAIGN_LOCATION_BUTTON);
        waitForElementNotVisible(UIMapper.SAVE_CAMPAIGN_LOCATION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDeleteLocationButton() throws InterruptedException {
        click(UIMapper.DELETE_EXTENSION_BUTTON, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(1000);
    }

    public void clickDeleteAdScheduleButton() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.DELETE_EXTENSION_BUTTON, LocatorTypeEnum.XPATH);
        if (isElementVisible("div[class='error pm-no-row-selected-error']")) {
            logger.info(getTextFromElement("div[class='error pm-no-row-selected-error']"));
        }
        waitForElementNotVisible(UIMapper.LOADING);

    }

    public void clickSaveScheduleButton() throws Exception {
        click(UIMapper.SAVE_SCHEDULE_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickSaveCampaignName() throws Exception {
        click(UIMapper.UPDATE_CAMPAIGN_NAME);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickSaveAdgroup() throws InterruptedException {
        click(UIMapper.SUBMIT_AD_GROUP);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickBidAdjustmentButton() throws InterruptedException {
        waitForElementVisible(UIMapper.BID_ADJUSTMENT_BUTTON);
        click(UIMapper.BID_ADJUSTMENT_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickBidAdjustmentAdScheduleButton() throws InterruptedException {
        waitForElementVisible(UIMapper.BID_ADJUSTMENT_ADSCHEDULE_BUTTON);
        click(UIMapper.BID_ADJUSTMENT_ADSCHEDULE_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickMakeChangesButton() throws InterruptedException {
        waitForElementVisible(UIMapper.MAKE_CHANGES_BUTTON,LocatorTypeEnum.XPATH);
        click(UIMapper.MAKE_CHANGES_BUTTON,LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickScheduleMakeChangesButton() throws InterruptedException {
        waitForElementVisible(UIMapper.MAKE_SCHEDULE_CHANGES_BUTTON,LocatorTypeEnum.XPATH);
        click(UIMapper.MAKE_SCHEDULE_CHANGES_BUTTON,LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterIncreaseByTextField(String increaseByPer) throws InterruptedException, AWTException {
        waitForElementVisible(UIMapper.INCREASE_BY_TEXTFIELD,LocatorTypeEnum.CSS);
        click(UIMapper.INCREASE_BY_TEXTFIELD,LocatorTypeEnum.CSS);
        type(UIMapper.INCREASE_BY_TEXTFIELD,increaseByPer,LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING,LocatorTypeEnum.CSS);
    }

    public void clickGeoLocation(String newGeoLocation) throws InterruptedException {
        selectItem(newGeoLocation);
    }

    public void selectSchedule(String date) throws InterruptedException {
        selectItem(date);
    }


    public void selectItem(String item) throws InterruptedException {
        String jsLocator = "var robot_td = document.getElementsByTagName('td'); for (var i = 0; i < robot_td.length; i++) {if(robot_td[i].innerHTML.indexOf('<span class=\"ng-scope ng-binding\">" + item + "')>=0) { return robot_td[i].parentElement.getElementsByTagName('td')[0].getElementsByTagName('input')[0];}}";
        waitForElementVisible(jsLocator, LocatorTypeEnum.JS);
        clickJS(jsLocator, LocatorTypeEnum.JS);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(1000);
    }

    public void clickSubmit() throws Exception {
        click(UIMapper.SUBMIT_CAMPAIGN);
        waitForElementNotVisible(UIMapper.LOADING);
        if (isElementVisible(UIMapper.SETTINGS_TAB_ERROR)) {
            throw new Exception(getTextFromElement(UIMapper.SETTINGS_TAB_ERROR));
        }
    }

    public void clickCreateCampaign() throws InterruptedException {
        click(UIMapper.CREATE_CAMPAIGN_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }


    public void enterGeoLocation(String text) throws Exception {
        waitForElementVisible(UIMapper.LOCATION_NAME_TEXT_BOX);
        click(UIMapper.LOCATION_NAME_TEXT_BOX);
        type(UIMapper.LOCATION_NAME_TEXT_BOX, text);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(2000);
        click(UIMapper.SEARCH_LOCATION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(2000);
    }

    public void loadingStatus() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getTableText(int row, int column) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
    }

    public String getExcludedText(int row, int column) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("table[class='table table-bordered table-hover ng-isolate-scope']>tbody>tr:nth-of-type" + "(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
    }

    public void selectExcludedText(int row, int column) throws InterruptedException {
        click("table[class='table table-bordered table-hover ng-isolate-scope']>tbody>tr:nth-of-type" + "(" + row + ")>td:nth-of-type(" + column + ")>input", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void selectAllExcludedText() throws InterruptedException {
        click("div[ng-show='showExcludedLocations']>table>thead>tr>th>input[ng-model='checkAllModel']");
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickExcludedDeleteButton() throws InterruptedException {
        click("div[id='geoExcludedEdits']>button[class='btn btn-xs btn-default dropdown-toggle'][ng-click='bulkExcludedDelete()']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public double getTableSize() throws InterruptedException {
        List<WebElement> rowCollection = driver.findElements(By.cssSelector("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr"));
        return rowCollection.size();
    }

    public void clickDeviceTab() throws InterruptedException {
        click("Device", LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public boolean deviceNames(String text) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return isElementPresent("//tr/td[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public String campaignSettingsLabel() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("div[id='indexPageId']>div[class='pm-body ng-scope']>div[class='pm-container has-lnav']>div[class='tab-content']", LocatorTypeEnum.CSS);

    }

    public String getCampaignText() {
        return getTextFromElement(UIMapper.CAMPAIGNS_SETTINGS_TAB + "span[ng-show='!showEditName']", LocatorTypeEnum.CSS);

    }

    public String getCampaignHeaderText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("div[class='pm-body-title ng-scope']>h1", LocatorTypeEnum.CSS);
    }

    public String getNetworkText() {
        return getTextFromElement(UIMapper.CAMPAIGNS_SETTINGS_TAB + "span[ng-show='!showNetworks']", LocatorTypeEnum.CSS);
    }

    public String getTypeText() {
        return getTextFromElement("div[id='campaignContext']>div[class='no-hover']>section[class='pm-create-section']>div[class='form-group']>div[class='col-md-6 pm-settings-value']", LocatorTypeEnum.CSS);
    }

    public String getDeviceText() {
        return getTextFromElement("div[id='campaignContext']>div>section:nth-of-type(3)>div>div>span[class='pm-settings-value ng-binding']", LocatorTypeEnum.CSS);

    }

    public String getBudgetText() {
        return getTextFromElement(UIMapper.CAMPAIGNS_SETTINGS_TAB + "span[ng-show='!showEditBudget']", LocatorTypeEnum.CSS);
    }

    public String currentDate() throws InterruptedException {
        String str = "MMM";
        if (str.length() > 3) {
            str = str.substring(3);
        }
        DateFormat dateFormat = new SimpleDateFormat(str + " dd, YYYY");
        Calendar cal = Calendar.getInstance();
        return (dateFormat.format(cal.getTime()));
    }


    public String getDisplayDate() {
        return getTextFromElement("div[id='campaignContext']>div[class='no-hover']>section[class='pm-create-section']>div[class='pm-settings-link']>div[ng-show='!showSchedule']>div[class='form-group']>div[class='col-md-6']>span[ng-show='!showEditStartDate']", LocatorTypeEnum.CSS);

    }

    public void selectAddTargetLocation(int row) throws InterruptedException {
        click("table[class='table loc-table']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(2)>span:nth-of-type(3)>a[ng-click='addToCampaignGeoRecords(sr); sr.isAlreadyTargetedSetting = !sr.isAlreadyTargetedSetting']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }


    public void selectExcludeTargetLocation(int row) throws InterruptedException {
        hover("table[class='table loc-table']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(2)>span:nth-of-type(4)>a[ng-click='excludeToCampaignGeoRecords(sr); sr.isAlreadyExcluded = !sr.isAlreadyExcluded']", LocatorTypeEnum.CSS);
        click("table[class='table loc-table']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(2)>span:nth-of-type(4)>a[ng-click='excludeToCampaignGeoRecords(sr); sr.isAlreadyExcluded = !sr.isAlreadyExcluded']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void addAllTargetLocation() throws InterruptedException {
        click("table[class='table loc-table']>thead>tr>th>div[ng-show='settingSearchResults.length != 0']>a[ng-click='addAllSearchResults()']", LocatorTypeEnum.CSS);
        waitForElementVisible("span[ng-show='sr.isAlreadyTargetedSetting']");
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void excludeAllTargetLocation() throws InterruptedException {
        click("table[class='table loc-table']>thead>tr>th>div[ng-show='settingSearchResults.length != 0']>a[ng-click='excludeAllSearchResults()']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getTextFromExcludedLocation(int column) throws InterruptedException {
        return getTextFromElement("div[class='well well-sm edit-location']>table:nth-of-type(2)>tbody>tr>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
    }

    public String getTextFromAllMatchedResults(int row) throws InterruptedException {
        return getTextFromElement("div[class='search-results ng-binding']>table>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(1)", LocatorTypeEnum.CSS);
    }

    public String getTextFromTargetLocation(int row, int column) throws InterruptedException {
        return getTextFromElement("div[class='well well-sm edit-location']>table:nth-of-type(1)>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
    }

    public String getDuplicateAdScheduleErrorText() throws InterruptedException {
        return getTextFromElement(UIMapper.DUPLICATE_AD_SCHEDULE_ERROR, LocatorTypeEnum.CSS);
    }

    public String getTimeOverLapErrorText() throws InterruptedException {
        return getTextFromElement(UIMapper.TIME_PERIOD_OVERLAP_ERROR, LocatorTypeEnum.CSS);
    }

    public String getInvalidEndTimeError() throws InterruptedException {
        return getTextFromElement(UIMapper.INVALID_END_TIME_ERROR, LocatorTypeEnum.CSS);
    }

    public void clickExcludedLocation() throws InterruptedException {
        click("//*[@class='ng-scope']/a[@ng-click='showExcludedLocations = !showExcludedLocations']", LocatorTypeEnum.XPATH);
       waitForElementNotVisible(UIMapper.LOADING);

    }

    public void clickExcludeAll() throws InterruptedException {
        click("div[class='search-results ng-binding']>table[class='table loc-table']>thead>tr>th[class='small']>div[ng-show='settingSearchResults.length != 0']>a[ng-click='excludeAllSearchResults()']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);

    }

    public void clickSettingsSubTabs(int i) throws InterruptedException {
        click("//ul[@class='pagination pagination-sm']/li[" + i + "]/a", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);

    }

    public void validateSettingsTab(int i) throws InterruptedException {
        isElementPresent("//ul[@class='pagination pagination-sm']/li[" + i + "]/a", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickExcludedLocationLink() throws InterruptedException {
        click(UIMapper.EXCLUDED_LOCATION_LINK);
        waitForElementNotVisible(UIMapper.LOADING);

    }

    public void clickLocationButton() throws InterruptedException {
        waitForElementVisible(UIMapper.ADD_LOCATION_BUTTON);
        click(UIMapper.ADD_LOCATION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);

    }



    public void clickCancelLocationButton() throws InterruptedException {
        click(UIMapper.CANCEL_LOCATION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickAddTargetLocation() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementVisible(UIMapper.ADD_TARGET_LOCATION);
        click(UIMapper.ADD_TARGET_LOCATION);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(2000);
    }

    public boolean checkTableHeader(String text) throws InterruptedException {
        return isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public String getNoMatchFoundText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.GEO_LOCATION_NO_MATCH_FOUND, LocatorTypeEnum.CSS);
    }

    public void enterCampaignName(String text) throws Exception {
        type(UIMapper.CAMPAIGN_NAME_TEXT_BOX, text);
    }

    public void selectBudget() throws Exception {
        click(UIMapper.BUDGET_INPUT_RADIO);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterAdGroupName(String text) throws Exception {
        waitForElementNotVisible(UIMapper.LOADING);
        type(UIMapper.AD_GROUP_NAME_TEXT_BOX, text);
    }

    public void enterHeadlineOne(String text) throws Exception {
        type(UIMapper.HEADLINE_TEXT_BOX, text);
    }

    public void enterDescriptionOne(String text) throws Exception {
        type(UIMapper.DESCRIPTION1_TEXT_BOX, text);
    }

    public void enterDescriptionTwo(String text) throws Exception {
        type(UIMapper.DESCRIPTION2_TEXT_BOX, text);
    }

    public void enterDisplayUrl(String text) throws Exception {
        type(UIMapper.DISPLAY_URL_TEXT_BOX, text);
    }

    public void enterDestinationUrl(String text) throws Exception {
        type(UIMapper.DESTINATION_URL_TEXT_BOX, text);
    }

    public void enterAdGroupBid(String text) throws Exception {
        type(UIMapper.AD_GROUP_BID_TEXT_BOX, text);
    }

    public void enterEditSettings(String text) throws Exception {
        type(UIMapper.EDIT_SETTINGS_CAMPAIGN_NAME_TEXT_BOX, text, true, LocatorTypeEnum.CSS);
    }

    public void clickEditCampaign() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementVisible(UIMapper.EDIT_CAMPAIGN);
        click(UIMapper.EDIT_CAMPAIGN);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCampaign(String text) throws InterruptedException {
        click("//tr/td/a[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickSettingsTab() throws InterruptedException {
        click(UIMapper.SETTINGS_TAB);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public boolean isSettingPageDisplayed() {
        while (!isElementVisible(UIMapper.EDIT_CAMPAIGN) && attempts < Max_Wait) {
            waitForElementNotVisible(UIMapper.LOADING);
        }
        return true;
    }

    public void selectTableRow(int row, int column) throws Exception {
        waitForElementVisible("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")>input");
        click("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")>input", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }


    public void enterBid(int row, int column, String bid) throws Exception {
        type("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")>form>div>input", bid, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public boolean waitForErrorMessage() throws InterruptedException {
        if (!isElementVisible("form[class='ng-scope ng-dirty ng-invalid ng-invalid-number']>div>div:nth-of-type(1)", LocatorTypeEnum.CSS) && attempts < Max_Wait) {
            waitForElementVisible("form[class='ng-scope ng-dirty ng-invalid ng-invalid-number']>div>div:nth-of-type(1)");
        }
        return true;
    }

    public void clickEditCampaignLocation() throws InterruptedException {
        Thread.sleep(2000);
        click(UIMapper.EDIT_CAMPAIGN_LOCATION);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(2000);
    }


    public void clickBidSaveButton() throws InterruptedException {
        click("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(2)>td:nth-of-type(3)>form>div>button:nth-of-type(2)", LocatorTypeEnum.CSS);
        waitForElementNotVisible("button[class='btn btn-xs btn-primary'][ng-click='save()']");
        waitForElementNotVisible(UIMapper.LOADING);
    }


    public boolean checkDuplicateError() throws InterruptedException {
        while (!isElementVisible(UIMapper.DUPLICATE_AD_SCHEDULE_ERROR, LocatorTypeEnum.CSS) && attempts < Max_Wait) {
            waitForElementNotVisible(UIMapper.LOADING);
        }
        return true;
    }

    public void clickBidCancelButton() throws InterruptedException {
        click("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(2)>td:nth-of-type(3)>form>div>button:nth-of-type(1)", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDecreaseBid() throws InterruptedException {
        //Select 1 to decrease by and select 2 to increase by
        click("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(2)>td:nth-of-type(3)>form>div>span>div>button", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        click("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(2)>td:nth-of-type(3)>form>div>span>div>ul>li:nth-of-type(1)>a", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCancelCampaign() throws InterruptedException {
        click(UIMapper.CANCEL_CAMPAIGN);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickBidModifier(int row, int column) throws InterruptedException {
        waitForElementNotVisible(UIMapper.NO_RECORDS_TO_LOAD, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        hover("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
        sleep(1000);
        executeScript("document.getElementsByClassName('fa fa-pencil')[0].setAttribute('id','pencil')");
        clickJS("pencil", LocatorTypeEnum.ID);
        waitForElementNotVisible(UIMapper.LOADING);
    }


    public String getDecreaseBidAdjustmentErrorText() throws InterruptedException {
        return getTextFromElement(UIMapper.BID_ADJUSTMENT_ERROR);

    }

    public String getIncreaseBidAdjustmentErrorText() throws InterruptedException {
        return getTextFromElement("form[class='ng-scope ng-valid ng-dirty']>div>div:nth-of-type(3)", LocatorTypeEnum.CSS);

    }

    public String getInvalidBidError() throws InterruptedException {
        return getTextFromElement("form[class='ng-scope ng-dirty ng-invalid ng-invalid-number']>div>div:nth-of-type(1)", LocatorTypeEnum.CSS);

    }

    public String getEnterWholeNumberError() throws InterruptedException {
        return getTextFromElement("form[class='ng-scope ng-valid ng-dirty']>div>div:nth-of-type(5)", LocatorTypeEnum.CSS);

    }


}