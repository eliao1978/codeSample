package com.yp.pm.kp.ui;

import com.yp.enums.selenium.LocatorTypeEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AdPage extends SeleniumWrapper {
    int attempt = 0;
    int retry = 5;

    public AdPage(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    public void clickCreateAdButton() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.CREATE_AD_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void selectAdGroup(String text) throws Exception {
        String adGroupElement;
        for (int i = 1; i <= 30; i++) {
            adGroupElement = "div[ng-show='displayAdgroupSelect']>div[class='panel-body']>ul[class='pm-select-list'] :nth-child(" + i + "n)";
            if (getTextFromElement(adGroupElement).equalsIgnoreCase(text)) {
                click(adGroupElement);
                waitForElementNotVisible(UIMapper.LOADING);
                waitForElementPresent("input[id='agHl'][name='headline']");
                break;
            }
        }
    }

    public void selectAdGroupX(String item) throws InterruptedException {
        click("//ul[@class='pm-select-list']/li[normalize-space(.)='" + item + "']", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterHeadline(String text) {
        type(UIMapper.HEADLINE_TEXT_BOX, text);
    }

    public void enterDescription1(String text) {
        type(UIMapper.DESCRIPTION1_TEXT_BOX, text);
    }

    public void editHeadline(String text) {
        type(UIMapper.EDIT_HEADLINE_TEXT_BOX, text);
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

    public void enterDestinationUrlJS(String text) {
        executeScript("document.getElementById('agDestURL').focus();");
        executeScript("document.getElementById('agDestURL').value='" + text + "'");
    }

    public void enableMobilePreferred() {
        click(UIMapper.MOBILE_PREFER_CHECKBOX);
    }

    public boolean isMobilePreferredEnable() {
        return isElementEnabled(UIMapper.MOBILE_PREFER_CHECKBOX_CHECKED);
    }

    public void submit() throws InterruptedException {
        click(UIMapper.SUBMIT_AD);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void submitKeyWordInsertion() throws InterruptedException {
        click(UIMapper.SUBMIT_AD);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void cancel() {
        click(UIMapper.CANCEL_AD);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String dynamicKeywordInsertionHeadlineError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.AD_DYNAMIC_KEYWORD_INSERTION_HEADLINE_ERROR, LocatorTypeEnum.XPATH);
    }

    public String dynamicKeywordInsertionDescriptionOneError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.AD_DYNAMIC_KEYWORD_INSERTION_DESCRIPTION_ONE_ERROR, LocatorTypeEnum.XPATH);
    }

    public String dynamicKeywordInsertionDescriptionTwoError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.AD_DYNAMIC_KEYWORD_INSERTION_DESCRIPTION_TWO_ERROR, LocatorTypeEnum.XPATH);
    }

    public String dynamicKeywordInsertionHDisplayUrlError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.AD_DYNAMIC_KEYWORD_INSERTION_DISPLAY_URL_ERROR, LocatorTypeEnum.XPATH);
    }

    public String dynamicKeywordInsertionDestinationUrlError() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.AD_DYNAMIC_KEYWORD_INSERTION_DESTINATION_URL_ERROR, LocatorTypeEnum.XPATH);
    }

    public String getPhoneErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementVisible(UIMapper.POPUP_ALERT);
        return getTextFromElement(UIMapper.POPUP_ALERT);

    }

    public String getDuplicateAdErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.DUPLICATE_AD_ERROR_MESSAGE, LocatorTypeEnum.CSS);

    }

    public void clearDescription1Line() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        clear(UIMapper.DESCRIPTION1_TEXT_BOX);
    }

    public void clearDescription2Line() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        clear(UIMapper.DESCRIPTION2_TEXT_BOX);
    }

    public void hoverAd(String item) throws InterruptedException {
        String xpathLocator = "//li[@class='adctv-item link ng-scope']/h3/a[normalize-space(.)='" + item + "']";
        if (System.getProperty("selenium.driver").equalsIgnoreCase("safari")) {
            hoverJS(xpathLocator, LocatorTypeEnum.XPATH);
        } else {
            hover(xpathLocator, LocatorTypeEnum.XPATH);
        }
    }

    public void clickEditIcon(String item) throws InterruptedException {
        String xpathLocator = "//li[@class='adctv-item link ng-scope']/h3/a[normalize-space(.)='" + item + "']/../../../../../../div[2]";
        waitForElementNotVisible(UIMapper.LOADING);
        click(xpathLocator, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickSaveEdit() throws InterruptedException {
        click(UIMapper.SAVE_EDIT_AD_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void checkAdLoaded(String text) throws InterruptedException {
        waitForElementVisible("//tr/td/div/div[@the-ad='row.ad']/ul/li[contains(normalize-space(.),'" + text + "')]", LocatorTypeEnum.XPATH);
    }

    public void checkNoRecordsToLoad() throws InterruptedException {
        waitForElementNotVisible("//tr/td[normalize-space(.)='No records to load.']", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(1000);
    }

    public void checkButtonNotVisible(String text, LocatorTypeEnum locator) throws InterruptedException {
        waitForElementNotVisible(text, locator);
        waitForElementNotVisible(UIMapper.LOADING);
    }


    public boolean checkDuplicateErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        while (!isElementVisible(UIMapper.DUPLICATE_AD_ERROR_MESSAGE, LocatorTypeEnum.CSS) && attempt < retry) {
            attempt += 1;
            Thread.sleep(1000);
        }
        return true;
    }

    public void clickCancelEdit() throws InterruptedException {
        click(UIMapper.CANCEL_EDIT_AD_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public boolean checkAdHeader(String text) throws InterruptedException {
        return isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public void clickTableHeader(int row) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.ADEXTENSION_TABLE_HEAD + "(" + row + ")", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getAdTableText(int row, int column) throws InterruptedException {
        return getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")", LocatorTypeEnum.CSS);
    }

    public void selectAdDropdown(int i) throws Exception {
        String[] siteLinkText = {"All ads", "All enabled ads", "All but deleted ads"};
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(1000);
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.ADS_FILTER_DROPDOWN, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(1000);
        waitForElementNotVisible(UIMapper.LOADING);
        click(siteLinkText[i], LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(1000);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void updateAdDropdown(int i) throws Exception {
        String[] siteLinkText = {"Enable", "Pause", "Delete"};
        waitForElementNotVisible(UIMapper.LOADING);
        click("div[id='adEdits']>span[class='dropdown']>button[class='btn btn-xs btn-default dropdown-toggle']", LocatorTypeEnum.CSS);
        waitForElementVisible(siteLinkText[i], LocatorTypeEnum.LINK_TEXT);
        click(siteLinkText[i], LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(1000);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickFilterButton() throws InterruptedException {
        click("div[id='adsTable']>button[class='btn btn-xs btn-default']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void selectCheckBox(int row, int column) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")>input", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public double getTableSize() throws InterruptedException {
        List<WebElement> rowCollection = driver.findElements(By.cssSelector("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr"));
        return rowCollection.size();
    }

    public void searchAd(String name) throws InterruptedException {
        type(UIMapper.AD_SEARCH_TEXTBOX, name);
        click(UIMapper.AD_SEARCH_BUTTON);
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

    public void loadingStatus() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void selectFromRowDropdown(int i) throws InterruptedException {
        String[] rows = {"10", "30", "50", "100", "200", "500"};
        click(UIMapper.ROWS_DROPDOWN, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        click(rows[i], LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(1000);
    }

    public void selectFromPageDropdown(int i) throws InterruptedException {
        String[] pages = {"1", "2", "3", "4", "5"};
        click(UIMapper.PAGE_DROPDOWN, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        click(pages[i], LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    //AdPage:
    public Boolean verifyErrorMessage(String text) throws Exception {
        return isElementVisible("//div[@class='error'][normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public String getInvalidDisplayUrl() throws Exception {
        return getTextFromElement("form[class='form-horizontal ng-dirty ng-valid ng-valid-required']>div:nth-of-type(21)", LocatorTypeEnum.CSS);
    }

    public String getInvalidDestinationUrl() throws Exception {
        return getTextFromElement("form[class='form-horizontal ng-dirty ng-valid ng-valid-required']>div:nth-of-type(27)", LocatorTypeEnum.CSS);
    }

    public boolean checkTableHeader(String text) throws InterruptedException {
        return isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public void clickShowChartButton() throws InterruptedException {
        click(UIMapper.SHOW_CHART_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        Thread.sleep(2000);
    }

    public void clickFilterByDateDropdown() throws InterruptedException {
        click(UIMapper.FILTER_BY_DATE_DROPDOWN, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDownloadButton() throws InterruptedException {
        click(UIMapper.DOWNLOAD_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickAdFilterButton() throws InterruptedException {
        click(UIMapper.AD_FILTER_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickAdExtensionFilterDropdown() throws InterruptedException {
        click(UIMapper.ADEXTENSION_FILTER_DROPDOWN, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickMobilePreferredCheckBox() throws InterruptedException {
        click(UIMapper.MOBILE_PREFERRED_CHECKBOX, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickAdSearchButton() throws InterruptedException {
        click(UIMapper.AD_SEARCH_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getHeadlineRequiredErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.AD_HEADLINE_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String getDescriptionLineOneRequiredErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.AD_DESCRIPTION1_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String getDescriptionLineTwoRequiredErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.AD_DESCRIPTION2_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String getDisplayUrlRequiredErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.AD_DISPLAY_URL_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String getDestinationUrlRequiredErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.AD_DESTINATION_URL_REQUIRED_ERROR, LocatorTypeEnum.CSS);
    }

    public String getDisapprovalReason() {
        hover(UIMapper.AD_DISAPPROVED_ICON);
        return getTextFromElement(UIMapper.AD_DISAPPROVAL_REASON, LocatorTypeEnum.CSS);
    }

}