package com.yp.pm.kp.ui;

import com.yp.enums.selenium.LocatorTypeEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CampaignPage extends SeleniumWrapper {
    int attempts = 0;
    int Max_Wait = 4;

    public CampaignPage(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    public void clickCreateCampaignButton() {
        click(UIMapper.CREATE_CAMPAIGN_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCreateConversionButton() {
        click(UIMapper.CREATE_CONVERSION);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public boolean isCreateCampaignButtonActive() {
        boolean trueCheck = true;
        boolean falseCheck = false;
     if (isElementPresent(UIMapper.CREATE_CAMPAIGN_BUTTON) == trueCheck) {
         return trueCheck;
     }
     else {
         return falseCheck;
     }
    }

    public boolean isCreateConversionButtonActive() {
        boolean trueCheck = true;
        boolean falseCheck = false;
        if (isElementPresent(UIMapper.CREATE_CONVERSION) == trueCheck) {
            return trueCheck;
        }
        else {
            return falseCheck;
        }
    }

    public void enterCampaignName(String name) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        type(UIMapper.CAMPAIGN_NAME_TEXT_BOX, name);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void submit() throws Exception {
        click(UIMapper.SUBMIT_CAMPAIGN);
        waitForElementNotVisible(UIMapper.LOADING);
        if (isElementVisible(UIMapper.SETTINGS_TAB_ERROR)) {
            logger.info(getTextFromElement(UIMapper.SETTINGS_TAB_ERROR));
        }
        sleep(2000);
    }

    public void cancel() throws Exception {
        click(UIMapper.CANCEL_CAMPAIGN);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void cancelAdGroup() throws InterruptedException {
        click(UIMapper.CANCEL_AD_GROUP);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDelete() throws InterruptedException {
        click(UIMapper.EDIT_STATUS_BUTTON);
        click(UIMapper.DELETE_STATUS_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(2000);
    }

    public void clickPause() throws Exception {
        click(UIMapper.EDIT_STATUS_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.PAUSE_STATUS_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(1000);
    }

    public void selectBudget() throws Exception {
        waitForElementNotVisible(UIMapper.LOADING);
        if(verifyTextPresent("Apply a budget from the shared library") && isElementVisible(UIMapper.BUDGET_INPUT_RADIO)) {
            clickJS(UIMapper.BUDGET_INPUT_RADIO, LocatorTypeEnum.CSS);
            waitForElementNotVisible(UIMapper.LOADING);
        }
        else if(verifyTextPresent("Apply a budget from the shared library") && !isElementVisible(UIMapper.BUDGET_INPUT_RADIO)) {
            logger.info("Missing budget radio button, account may not have a budget created");
        }
    }


    public void clickCampaignsTab() throws InterruptedException {
        click(UIMapper.ALL_CAMPAIGNS);
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.CAMPAIGNS_TAB);
        waitForElementNotVisible(UIMapper.LOADING);
        if (verifyTextPresent("No records to load")) {
            refreshBrowser();
            waitForElementNotVisible(UIMapper.LOADING);
        }
    }

    public void selectCampaign(String item) throws Exception {
        waitForElementNotVisible(UIMapper.LOADING);
        String jsLocator = "var robot_td = document.getElementsByTagName('td'); for (var i = 0; i < robot_td.length; i++) {if(robot_td[i].innerHTML.indexOf('" + item + "')>=0) { return robot_td[i].parentElement.getElementsByTagName('td')[0].getElementsByTagName('input')[0];}  }";
        String xpathLocator = "//td/a[text()='" + item + "']/../preceding-sibling::td/input";
        waitForElementNotVisible(UIMapper.LOADING);
        if (!isElementPresent(xpathLocator, LocatorTypeEnum.XPATH)) {
            refreshBrowser();
            waitForElementNotVisible(UIMapper.LOADING);
        }
        if (isElementPresent(xpathLocator, LocatorTypeEnum.XPATH)) {
            click(xpathLocator, LocatorTypeEnum.XPATH);
        } else if (isElementPresent(jsLocator, LocatorTypeEnum.JS)) {
            click(jsLocator, LocatorTypeEnum.JS);
        } else {
            throw new Exception("Failed to find method to select input checkbox OR no campaigns displayed to select");
        }
    }

    public boolean checkCampaignHeader(String text) throws InterruptedException {
        return isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public void clickTableHeader(int row) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.ADEXTENSION_TABLE_HEAD + "(" + row + ")");
    }

    public void clickTableHeader(String text) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        clickJS("//table[@class='table table-bordered table-hover ng-scope ng-isolate-scope']/thead/tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getCampaignTableText(int row, int column) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
    }

    public void enterSearchText(String text) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        type(UIMapper.SEARCH_CAMPAIGN, text, true, LocatorTypeEnum.CSS);
    }

    public void submitSearchText() throws InterruptedException {
        click(UIMapper.FILTER_SUBMIT_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public double getTableSize() throws InterruptedException {
        List<WebElement> rowCollection = driver.findElements(By.cssSelector("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr"));
        return rowCollection.size();
    }

    public void clickFilterButton() throws InterruptedException {
        click(UIMapper.CAMPAIGN_FILTER_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void campaignDropdown(int i) throws InterruptedException {
        String[] siteLinkText = {"All campaigns", "All enabled campaigns", "All but deleted campaigns"};
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.CAMPAIGN_TYPE_DROPDOWN, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        click(siteLinkText[i], LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public boolean waitForSelection() throws InterruptedException {
        String[] siteLink = {"All campaigns"};
        while (isElementVisible(siteLink[0], LocatorTypeEnum.LINK_TEXT) && attempts < Max_Wait) {
            waitForElementNotVisible(UIMapper.LOADING);
        }
        return true;
    }

    public boolean isSubmitSuccess() throws InterruptedException {
        String[] siteLink = {"All campaigns"};
        while (!isElementVisible(siteLink[0], LocatorTypeEnum.LINK_TEXT) && attempts < Max_Wait) {
            waitForElementNotVisible(UIMapper.LOADING);
        }
        return true;
    }

    public void updateCampaignDropdown(int i) throws InterruptedException {
        String[] siteLinkText = {"Enable", "Pause", "Delete"};
        click(UIMapper.EDIT_STATUS_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(1000);
        waitForElementNotVisible(UIMapper.LOADING);
        click(siteLinkText[i], LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(1000);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void selectCheckBox(int row, int column) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")>input");
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void loadingStatus() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
    }

    //Change History Methods

    public void clickChangeHistory() throws InterruptedException {
        click("//a[contains(text(),'Change History')]", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void selectTodayChangeHistory() throws InterruptedException {
        click("//button[@type='button']", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        click("//ul[@class='dropdown-menu']/li[@class='ng-scope ng-binding'][1]", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        refreshBrowser();
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getHistoryTableText(int row, int column) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("table[class='table table-bordered table-hover ng-isolate-scope']>tbody>tr:nth-of-type" + "(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);

    }

    public void removeLocation() throws InterruptedException {
        String cssPath = "table[class='table loc-table']>tbody>tr>td:nth-of-type(2)>a[ng-click='createCampaignCtrl.removeCampaignRecord(geo)']";
        hover(cssPath, LocatorTypeEnum.CSS);
        click(cssPath, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);

    }

    public void enterCityText(String text) throws InterruptedException {
        type("div[id='locSearch']>input[class='form-control ng-pristine ng-valid']", text, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        click("div[id='locSearch']>button[ng-click='showSearchResults()']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        click("table[class='table loc-table']>tbody>tr:nth-of-type(2)>td:nth-of-type(2)>span[ng-show='!sr.isAlreadyTargetedSetting && !sr.isAlreadyExcluded']>a[ng-click='addToCampaignGeoRecords(sr); sr.isAlreadyTargetedSetting = !sr.isAlreadyTargetedSetting']", LocatorTypeEnum.CSS);

    }

    public void clickSaveButton() throws InterruptedException {
        click("div[class='pm-actions']>button[class='btn btn-xs btn-primary'][ng-click='updateCampaignGeoRecords()']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterAdGroupName(String text) {
        type(UIMapper.AD_GROUP_NAME_TEXT_BOX, text);
    }

    public void enterAdBroupBid(Double amount) {
        type(UIMapper.AD_GROUP_BID_TEXT_BOX, String.valueOf(amount));
    }

    public void adGroupsubmit() throws InterruptedException {
        click(UIMapper.SUBMIT_AD_GROUP);
        waitForElementNotVisible(UIMapper.LOADING);

    }

    public void selectCampaign() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        hover("table[class='table table-bordered table-hover ng-isolate-scope']>tbody>tr:nth-of-type(2)>td:nth-of-type(3)", LocatorTypeEnum.CSS);
        click("table[class='table table-bordered table-hover ng-isolate-scope']>tbody>tr:nth-of-type(2)>td:nth-of-type(3)>a", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        String editLocation = "div[class='form-group']>div[class='col-md-10 pm-settings-value']>p[class='pm-edit-loc']>a[ng-click='!hasPermission || showEditLocationsTab()']";
        waitForElementVisible(editLocation );
        click(editLocation , LocatorTypeEnum.CSS);
    }


    public String ValidateAddButton() throws InterruptedException {
        return getTextFromElement("table[class='table loc-table']>tbody>tr:nth-of-type(1)>td:nth-of-type(2)>span[ng-show='!sr.isAlreadyTargetedSetting && !sr.isAlreadyExcluded']>a[ng-click='addToCampaignGeoRecords(sr); sr.isAlreadyTargetedSetting = !sr.isAlreadyTargetedSetting']", LocatorTypeEnum.CSS);
    }

    public String validateAddedText() throws InterruptedException {
        return getTextFromElement("table[class='table loc-table']>tbody>tr:nth-of-type(2)>td:nth-of-type(2)>span", LocatorTypeEnum.CSS);
    }

    public void clickAdvanceSearchAddAll() throws InterruptedException {
        click(UIMapper.ADVANCE_SEARCH_ADD_ALL, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickAdvanceSearchExcludeAll() throws InterruptedException {
        click(UIMapper.ADVANCE_SEARCH_EXCLUDE_ALL);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickAdvanceSearch(String Text) throws InterruptedException {
        click(UIMapper.ADVANCE_SEARCH_LINK);
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.ADVANCE_SEARCH_TEXT_BOX, LocatorTypeEnum.CSS);
        type(UIMapper.ADVANCE_SEARCH_TEXT_BOX, Text, true, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        click("/html/body/div[7]/div/div/div/div[2]/div[3]/div[1]/form[1]/div/button", LocatorTypeEnum.XPATH);
    }

    public void clickAdvanceSearchTargetRemove(int row) throws InterruptedException {
        click("//*[@class='tgt-results']/table[1]/tbody/" + "tr[" + row + "]/td[2]/a", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickAdvanceSearchExcludedRemove() throws InterruptedException {
        click("table:nth-of-type(2)>tbody>tr>td>a[ng-click='removeFromCampaignGeoRecords(geo, $index); geo.isAlreadyExcluded = !geo.isAlreadyExcluded']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getSearchResultTextStatus(int row) throws InterruptedException {
        return getTextFromElement("div[class='search-results']>table[class='table loc-table']>tbody>tr:nth-of-type" + "(" + row + ")>td:nth-of-type(2)", LocatorTypeEnum.CSS);
    }

    public String getExcludedLocationText() throws InterruptedException {
        return getTextFromElement("table:nth-of-type(2)>tbody", LocatorTypeEnum.CSS);
    }

    public String getChangeHistoryText() throws InterruptedException {
        return getTextFromElement("div[class='ng-scope']>div[class='pm-body-title ng-scope']>h1", LocatorTypeEnum.CSS);
    }

    public String getCampaignCreatedText() throws InterruptedException {
        return getTextFromElement(UIMapper.CAMPAIGN_NAME_TEXT, LocatorTypeEnum.CSS);
    }

    public boolean checkTableHeader(String text) throws InterruptedException {
        return isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public void clickShowChartButton() throws InterruptedException {
        click(UIMapper.SHOW_CHART_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickFilterByDateDropdown() throws InterruptedException {
        click(UIMapper.FILTER_BY_DATE_DROPDOWN, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDownloadButton() throws InterruptedException {
        click(UIMapper.DOWNLOAD_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickFilterSubmitButton() throws InterruptedException {
        click(UIMapper.FILTER_SUBMIT_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCampaignFilterButton() throws InterruptedException {
        click(UIMapper.CAMPAIGN_FILTER_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickEndDate() throws Exception {
        click(UIMapper.CAMPAIGN_END_DATE_CALENDAR_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDate() throws InterruptedException {
        click(UIMapper.CAMPAIGN_SELECT_PAST_DATE, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickChangeHistoryFilterButton() throws InterruptedException {
        click(UIMapper.CHANGE_HISTORY_FILTER_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCalendarBackButton() throws InterruptedException {
        click("div[id='ui-datepicker-div']>div>a>span[class='ui-icon ui-icon-circle-triangle-w']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }


    public void clickRemoveLocation() throws InterruptedException {
        click(UIMapper.CAMPAIIGN_REMOVE_LOCATION_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickLocationSearchButton() throws InterruptedException {
        click(UIMapper.CAMPAIGN_LOCATION_SEARCH_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getCampaignNameErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.CAMPAIGN_NAME_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getCampaignNameTooLongErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.CAMPAIGN_NAME_TOO_LONG_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getLocationRequiredError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.CAMPAIGN_LOCATION_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String getMissingSearchLocation() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.CAMPAIGN_MISSING_SEARCH_LOCATION_ERROR, LocatorTypeEnum.CSS);
    }

    public String getStartDateErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.CAMPAIGN_START_DATE_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getEndDateErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.CAMPAIGN_END_DATE_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getBudgetRequiredErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.BUDGET_REQUIRED_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getDuplicateCampaignErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.CAMPAIGN_DUPLICATE_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public void clickRadiusTargetingTab() throws InterruptedException {
        click(UIMapper.ADVANCE_SEARCH_RADIUS_TARGETING_TAB, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickRadiusTargetingSEARCH_BUTTON() throws InterruptedException {
        click(UIMapper.ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_LOCATION_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterRadiusTargettingLocationText(String text) throws Exception {
        clear(UIMapper.ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_LOCATION_TEXT_BOX);
        type(UIMapper.ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_LOCATION_TEXT_BOX, text);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clearMiles() throws Exception {
        clear(UIMapper.ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_LOCATION_MILES_TEXT_BOX);
    }

    public void enterRadiusTargetingMilesText(String text) throws Exception {
        type(UIMapper.ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_LOCATION_MILES_TEXT_BOX, text);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getRadiusTargetingNoInputSearchError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADVANCE_SEARCH_RADIUS_TARGETING_NO_SEARCH_LOCATION_ERROR, LocatorTypeEnum.CSS);
    }

    public String getRadiusTargetingInvalidInputMilesError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADVANCE_SEARCH_RADIUS_TARGETING_INVALID_MILES_ERROR, LocatorTypeEnum.CSS);
    }

    public String getRadiusTargetingMilesMaximumRangeError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_MILES_MAX_RANGE_ERROR, LocatorTypeEnum.CSS);
    }


    public String getRadiusTargetingMilesMinimumRangeError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_MILES_MIN_RANGE_ERROR, LocatorTypeEnum.CSS);
    }

    public String getRadiusTargetingInvalidInputSearchError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_INVALID_ADDRESS_ERROR, LocatorTypeEnum.CSS);
    }

    public void clickBulkLocationTab() throws InterruptedException {
        click(UIMapper.ADVANCE_SEARCH_BULK_LOCATIONS_TAB, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickBulkLocationSearchButton() throws InterruptedException {
        click(UIMapper.ADVANCE_SEARCH_BULK_LOCATION_SEARCH_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterBulkLocations(String text) throws Exception {
        clear(UIMapper.ADVANCE_SEARCH_BULK_LOCATIONS_TEXT_BOX);
        type(UIMapper.ADVANCE_SEARCH_BULK_LOCATIONS_TEXT_BOX, text);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getBulkLocationNoInputError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADVANCE_SEARCH_BULK_LOCATION_NO_INPUT_ERROR, LocatorTypeEnum.CSS);
    }

    public String getBulkLocationInvalidInputError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADVANCE_SEARCH_BULK_LOCATION_INVALID_INPUT_ERROR, LocatorTypeEnum.CSS);
    }

}