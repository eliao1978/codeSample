package com.yp.pm.kp.ui;

import com.yp.enums.selenium.LocatorTypeEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class KeywordPage extends SeleniumWrapper {

    public KeywordPage(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    public void selectAdGroup(String text) throws Exception {
        List<WebElement> list = driver.findElements(By.xpath("//ul[@class='pm-select-list']/li[normalize-space(.)='" + text + "']"));
        for (WebElement element : list) {
            if (element.isDisplayed()) {
                element.click();
                waitForElementNotVisible(UIMapper.LOADING);
            }
        }
    }

    public void selectAdGroupNegativeKeyword(String text) throws InterruptedException {
        List<WebElement> list = driver.findElements(By.xpath("//ul[@class='pm-select-list']/li[normalize-space(.)='" + text + "']"));
        for (WebElement element : list) {
            if (element.isDisplayed()) {
                element.click();
                waitForElementNotVisible(UIMapper.LOADING);
            }
        }
    }

    public void clickCreateKeyword() throws Exception {
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementVisible(UIMapper.CREATE_KEY_WORD_BUTTON);
        click(UIMapper.CREATE_KEY_WORD_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickDkiHelpLink() throws Exception {
        waitForElementVisible(UIMapper.KEYWORD_DKI_LINK, LocatorTypeEnum.XPATH);
        click(UIMapper.KEYWORD_DKI_LINK, LocatorTypeEnum.XPATH);
    }

    public void clickDliHelpLink() throws Exception {
        waitForElementVisible(UIMapper.KEYWORD_DLI_LINK, LocatorTypeEnum.XPATH);
        click(UIMapper.KEYWORD_DLI_LINK, LocatorTypeEnum.XPATH);
    }

    public void enterKeywordText(String text) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        type(UIMapper.KEYWORD_TEXT_BOX, text);
    }

    public void submit() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.SUBMIT_KEY_WORD);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void cancel() throws Exception {
        click(UIMapper.CANCEL_KEY_WORD);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCreateNegativeKeyword() throws InterruptedException {
        click(UIMapper.NEGATIVE_KEYWORD_LINK);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void adGroupAdButton() throws InterruptedException {
        click(UIMapper.NEGATIVE_KEYWORD_GROUP_ADD_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void campaignLevelAdButton() {
        click(UIMapper.NEGATIVE_KEYWORD_CAMPAIGN_ADD_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickNegativeKeyWordCampaignSaveButton() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        executeScript("document.forms[7].elements[1].setAttribute('id','neg_campaign_save')");
        click("neg_campaign_save", LocatorTypeEnum.ID);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickNegativeKeyWordCampaignCancelButton() throws InterruptedException {
        if (!isElementVisible("document.forms[7].elements[2]", LocatorTypeEnum.JS)) {
            waitForElementNotVisible(UIMapper.LOADING);
        }
        executeScript("document.forms[7].elements[2].setAttribute('id','neg_campaign_cancel')");
        click("neg_campaign_cancel", LocatorTypeEnum.ID);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickNegativeKeyWordAdGroupSaveButton() throws InterruptedException {
        if (!isElementVisible("document.forms[6].elements[1]", LocatorTypeEnum.JS)) {
            waitForElementNotVisible(UIMapper.LOADING);
        }
        executeScript("document.forms[6].elements[1].setAttribute('id','neg_adgroup_save')");
        click("neg_adgroup_save", LocatorTypeEnum.ID);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickNegativeKeyWordAdGroupCancelButton() throws InterruptedException {
        if (!isElementVisible("document.forms[6].elements[2]", LocatorTypeEnum.JS)) {
            waitForElementNotVisible(UIMapper.LOADING);
        }
        executeScript("document.forms[6].elements[2].setAttribute('id','neg_adgroup_cancel')");
        click("neg_adgroup_cancel", LocatorTypeEnum.ID);
        waitForElementNotVisible(UIMapper.LOADING);

    }

    public void NegativeKeyWordInputText(String value) throws InterruptedException {
        if (!isElementVisible("return document.forms[6].elements[0]", LocatorTypeEnum.JS)) {
            waitForElementNotVisible(UIMapper.LOADING);
        }
        clear("return document.forms[6].elements[0]", LocatorTypeEnum.JS);
        type("return document.forms[6].elements[0]", value, false, LocatorTypeEnum.JS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void NegativeKeyWordInputText2(String value) throws InterruptedException {
        if (!isElementVisible("return document.forms[7].elements[0]", LocatorTypeEnum.JS)) {
            waitForElementNotVisible(UIMapper.LOADING);
        }
        clear("return document.forms[7].elements[0]", LocatorTypeEnum.JS);
        type("return document.forms[7].elements[0]", value, false, LocatorTypeEnum.JS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void editKeyword(String text) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        String xpathLocator = "//td/span[normalize-space(.)='" + text + "']";

        click(xpathLocator, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);

        click(xpathLocator + "/../div", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);

        click(UIMapper.ACCEPT_POPUP_WARNING_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void SaveEditingKeyWordText(String text) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);

        type("div[ng-show='showEditKeyword']>input[name='kwdText'][ng-model='keywordText']", text, true, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);

        click("div[class='table-cell-editor-actions']>button[class='btn btn-xs btn-primary'][ng-click='save()']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void checkButtonNotVisible(String text, LocatorTypeEnum locator) throws InterruptedException {
        waitForElementNotVisible(text, locator);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void checkButtonNotVisible(String text) throws InterruptedException {
        checkButtonNotVisible(text, LocatorTypeEnum.CSS);
    }

    public boolean isEditSuccess() throws InterruptedException {
        int attempt = 0;
        int maxIteration = 4;

        while (isElementVisible("div[class='table-cell-editor-actions']>button[class='btn btn-xs btn-primary'][ng-click='save()']", LocatorTypeEnum.CSS) && attempt < maxIteration) {
            waitForElementNotVisible(UIMapper.LOADING);
            attempt += 1;
            sleep();
        }

        if (attempt > maxIteration) {
            throw new TimeoutException();
        } else {
            return true;
        }
    }

    public void cancelEditingKeywordText(String text) throws InterruptedException {
        type("div[ng-show='showEditKeyword']>input[name='kwdText'][ng-model='keywordText']", text, true, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        click("div[class='table-cell-editor-actions']>button[class='btn btn-xs btn-default'][ng-click='stCtrl.closeEditor()']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickEditNegativeKeyword(String text) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        String xpathLocator = "//td/span[normalize-space(.)='" + text + "']";
        String xpathLocator2 = "//td/span[normalize-space(.)='" + text + "']/../div";
        if (!isElementVisible(xpathLocator, LocatorTypeEnum.XPATH)) {
            waitForElementNotVisible(UIMapper.LOADING);
        }
        hoverJS(xpathLocator, LocatorTypeEnum.XPATH);
        click(xpathLocator2, LocatorTypeEnum.XPATH);
    }

    public void showNegativeKeywords() {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.SHOW_NEGATIVE_KEYWORDS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void editNegativeKeywordEditTextAdGroupLevel(String text) throws InterruptedException {
        type("form[name='editNegativeKeywordTextMatchTypeForm']>div>input[name='negativeText']", text, true, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public boolean isSubmitSuccess() throws InterruptedException {
        int attempt = 0;
        int maxIteration = 4;

        while (!isElementVisible("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + 2 + ")>td:nth-of-type(" + 2 + ")", LocatorTypeEnum.CSS) && attempt < maxIteration) {
            waitForElementNotVisible(UIMapper.LOADING);
            attempt += 1;
            sleep();
        }

        if (attempt > maxIteration) {
            throw new TimeoutException();
        } else {
            return true;
        }
    }

    public boolean checkDuplicateKeywordError() throws InterruptedException {
        int attempt = 0;
        int maxIteration = 4;

        while (!isElementVisible(UIMapper.DUPLICATE_KEYWORD_ERROR_MESSAGE, LocatorTypeEnum.CSS) && attempt < maxIteration) {
            waitForElementNotVisible(UIMapper.LOADING);
            attempt += 1;
            sleep();
        }

        if (attempt > maxIteration) {
            throw new TimeoutException();
        } else {
            return true;
        }
    }

    public void saveNegativeKeywordEditTextAdGroupLevel() throws InterruptedException {
        click("div[class='table-cell-editor-actions']>button[ng-click='save()'][ng-disabled='editNegativeKeywordTextMatchTypeForm.$invalid']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void negativeKeywordCancelTextAdGroupLevel() throws InterruptedException {
        click("div[class='table-cell-editor-actions']>button[ng-click='stCtrl.closeEditor()']", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void editNegativeKeywordCampaignLevel(String text) throws InterruptedException {
        String xpathLocator = "//td/span[normalize-space(.)='" + text + "']";
        click(xpathLocator, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        click(xpathLocator + "/../div", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);

    }

    public void saveEditedNegativeKeywordTextCampaignLevel(String text) throws InterruptedException {
        type("div[class='table-cell-editor ng-scope']>input[name='negativeText']", text, true, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        click("div[class='table-cell-editor-actions']>button[class='btn btn-xs btn-primary'][ng-click='save()']");
        waitForElementNotVisible(UIMapper.LOADING);

    }

    public void cancelEditedNegativeKeywordTextCampaignLevel(String text) throws InterruptedException {
        type("div[class='table-cell-editor ng-scope']>input[name='negativeText']", text, true, LocatorTypeEnum.CSS);
        click("div[class='table-cell-editor-actions']>button[class='btn btn-xs btn-default'][ng-click='stCtrl.closeEditor()']");
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickKeywordHeader(int column) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.KEYWORD_TABLE_Header + "th[" + column + "]", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(1000);
    }

    public void clickKeywordHeader(String text) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        clickJS("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public boolean checkKeywordHeader(String text) throws InterruptedException {
        if (isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH)) {
            return (isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH));
        } else {
            return false;
        }
    }

    public String getTableText(int row, int column) throws InterruptedException {
        return getTextFromElement(UIMapper.KEYWORD_TABLE_Body + "[" + row + "]/td[" + column + "]", LocatorTypeEnum.XPATH);
    }

    public void selectKeywordCheckBox(int row, int column) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.KEYWORD_TABLE_Body + "[" + row + "]/td[" + column + "]/input", LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterSearchText(String text) throws InterruptedException {
        type(UIMapper.KEYWORD_SEARCH_TEXTBOX, text);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void submitFilterText() throws InterruptedException {
        click(UIMapper.FILTER_SUBMIT_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public ArrayList<String> getTableColumn() throws InterruptedException {
        List<WebElement> column = driver.findElements(By.xpath(UIMapper.KEYWORD_TABLE_COLUMN));
        ArrayList<String> columnText = new ArrayList<>();
        for (WebElement element : column) {
            if (!element.getText().equals("") && !element.getText().toLowerCase().contains("total -")) {
                columnText.add(element.getText());
            }
        }
        return columnText;
    }

    public void clickFilterButton() throws InterruptedException {
        click(UIMapper.KEYWORD_FILTER_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void allKeyWordsSelectFromDropdown(String keywordText) throws InterruptedException {
        click(UIMapper.KEYWORD_ELEMENT_DROPDOWN);
        sleep();
        click(keywordText, LocatorTypeEnum.LINK_TEXT);
        sleep();
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void allKeyWordsSelectFromDropdown() throws InterruptedException {
        allKeyWordsSelectFromDropdown("All keywords");
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void allEnableKeyWordsSelectFromDropdown(String keywordText) throws InterruptedException {
        click(UIMapper.KEYWORD_ELEMENT_DROPDOWN);
        sleep();
        click(keywordText, LocatorTypeEnum.LINK_TEXT);
        sleep();
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void allEnableKeyWordsSelectFromDropdown() throws InterruptedException {
        allEnableKeyWordsSelectFromDropdown("All enabled keywords");
    }

    public void allButDeletedKeywordsSelectFromDropdown(String keywordText) throws InterruptedException {
        click(UIMapper.KEYWORD_ELEMENT_DROPDOWN);
        sleep();
        click(keywordText, LocatorTypeEnum.LINK_TEXT);
        sleep();
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void allButDeletedKeywordsSelectFromDropdown() throws InterruptedException {
        allButDeletedKeywordsSelectFromDropdown("All but deleted keywords");
    }

    public void pauseKeywordSelect(String keywordText) throws InterruptedException {
        click(UIMapper.KEYWORD_UPDATE_DROPDOWN);
        sleep();
        click(keywordText, LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(2000);
    }

    public void pauseKeywordSelect() throws InterruptedException {
        pauseKeywordSelect("Pause");
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void deleteKeywordSelect(String keywordText) throws InterruptedException {
        click(UIMapper.KEYWORD_UPDATE_DROPDOWN);
        sleep();
        click(keywordText, LocatorTypeEnum.LINK_TEXT);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep(2000);
    }

    public void deleteKeywordSelect() throws InterruptedException {
        deleteKeywordSelect("Delete");
        waitForElementNotVisible(UIMapper.LOADING);
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

    public void clickKeywordSearchTextbox() throws InterruptedException {
        click(UIMapper.KEYWORD_SEARCH_TEXTBOX, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickFilterSubmitButton() throws InterruptedException {
        click(UIMapper.FILTER_SUBMIT_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickKeywordElementDropdown() throws InterruptedException {
        click(UIMapper.KEYWORD_ELEMENT_DROPDOWN, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getKeywordRequiredErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.KEYWORD_CREATE_ERROR, LocatorTypeEnum.XPATH);
    }

    public String getAdGroupLevelText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("div[ui-view='adgroupNegativesPane']>table>tbody>tr:nth-of-type(2)>td:nth-of-type(3)", LocatorTypeEnum.CSS);
    }

    public String getNegativeKeywordText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("div[ui-view='adgroupNegativesPane']>table>tbody>tr:nth-of-type(2)>td:nth-of-type(2)>span", LocatorTypeEnum.CSS);
    }

    public String getCampaignLevelText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("div[ui-view='campaignNegativesPane']>table>tbody>tr:nth-of-type(2)>td:nth-of-type(2)>span", LocatorTypeEnum.CSS);
    }

    public String getEmptyAdGroupTableText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("div[ui-view='adgroupNegativesPane']>table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr>td[class='text-center']", LocatorTypeEnum.CSS);
    }

    public String getEmptyCampaignTableText() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement("/html/body/div[2]/div[2]/div[2]/div[2]/div/div/div[8]/div[2]/table/tbody/tr[1]/td", LocatorTypeEnum.XPATH);
    }

    public String getKeywordErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.KEYWORD_CHARACTER_LENGTH_ERROR, LocatorTypeEnum.CSS);
    }

    public String getDuplicateKeywordErrorMessage() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.DUPLICATE_KEYWORD_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getNegativeKeywordMaxCharacter() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADGROUP_LEVEL_NEGATIVE_KEYWORD_MAX_CHARACTER_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getNegativeKeywordMaxWords() throws Exception {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.NEGATIVE_KEYWORD_MAX_WORD_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getCampaignNegativeKeywordInvalidCharacters() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.CAMPAIGN_LEVEL_NEGATIVE_KEYWORD_INVALID_CHARACTER_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getCampaignNegativeKeywordMaxCharacters() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.CAMPAIGN_LEVEL_NEGATIVE_KEYWORD_MAX_CHARACTER_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getCampaignDuplicateNegativeKeyword() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.CAMPAIGN_LEVEL_NEGATIVE_KEYWORD_DUPLICATE_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

    public String getNegativeKeywordInvalidCharacter() throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        return getTextFromElement(UIMapper.ADGROUP_LEVEL_NEGATIVE_KEYWORD_INVALID_CHARACTERS_ERROR_MESSAGE, LocatorTypeEnum.CSS);
    }

//    public String getDuplicateNegativeKeywordErrorMessage() throws InterruptedException {
//        waitForElementNotVisible(UIMapper.LOADING);
//        return getTextFromElement(UIMapper.ADGROUP_LEVEL_NEGATIVE_KEYWORD_DUPLICATE_ERROR_MESSAGE, LocatorTypeEnum.CSS);
//    }

    public void clickDuplicateModalOkButton() throws InterruptedException {
        click(UIMapper.MODAL_DUPLICATE_ERROR_OK_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public int getTableSize() throws InterruptedException {
        List<WebElement> columnElements = driver.findElements(By.cssSelector("table[class='table table-bordered table-hover ng-isolate-scope']>tbody>tr:not(:first-child):not(:last-child)"));
        return columnElements.size();
    }

    public String getDisapprovalReason() {
        hover(UIMapper.KEYWORD_DISAPPROVED_ICON);
        return getTextFromElement(UIMapper.KEYWORD_DISAPPROVAL_REASON, LocatorTypeEnum.CSS);
    }

    public void editDestinationUrl(String keyword, String url) throws InterruptedException {
        waitForElementNotVisible(UIMapper.LOADING);
        clickIntoView("//td[contains(normalize-space(.),'" + keyword + "')]/../td[16]/span", LocatorTypeEnum.XPATH);
        hoverJS("//td[contains(normalize-space(.),'" + keyword + "')]/../td[16]/span",LocatorTypeEnum.XPATH);
        clickJS("//td[contains(normalize-space(.),'" + keyword + "')]/../td[16]/div[@class='table-cell-editor-toggle ng-scope']/span", LocatorTypeEnum.XPATH);
        waitForElementVisible("input[name='keywordDestinationUrl']");
        type("input[name='keywordDestinationUrl']",url);
        clickJS("div[class='table-cell-editor-actions']>button[ng-click='save()']", LocatorTypeEnum.CSS);
    }

    public String getError() throws InterruptedException {
        String path = "form[name='editKeywordDestinationUrlForm']>div>div[class='error ng-binding']";
        waitForElementVisible(path);
        return getTextFromElement(path);
    }
}