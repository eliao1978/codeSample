package com.yp.pm.kp.ui;

import com.yp.enums.selenium.LocatorTypeEnum;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class AccountPage extends SeleniumWrapper {

    public AccountPage(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
        waitForElementVisible(UIMapper.CREATE_ACCOUNT_BUTTON);
    }

    /**
     * click create account button
     *
     * @throws Exception
     */
    public void clickCreateAccountButton() throws Exception {
        waitForElementNotVisible(UIMapper.LOADING);
        click(UIMapper.CREATE_ACCOUNT_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    /**
     * enter account client name
     *
     * @param name String
     */
    public void enterClientName(String name) {
        type(UIMapper.CLIENT_NAME_TEXT_BOX, name);
    }

    /**
     * enter default account timezone PST
     *
     * @throws InterruptedException
     */
    public void selectTimeZone() throws InterruptedException {
        selectTimeZone("Pacific Time");
    }

    /**
     * enter account timezone
     *
     * @param timeZone String
     * @throws InterruptedException
     */
    public void selectTimeZone(String timeZone) throws InterruptedException {
        click(UIMapper.TIMEZONE_DROPDOWN);
        click(timeZone, LocatorTypeEnum.LINK_TEXT);
    }

    /**
     * click create account button to submit form
     *
     * @return CampaignPage
     * @throws InterruptedException
     */
    public CampaignPage submitForm() throws InterruptedException {
        click(UIMapper.SUBMIT_ACCOUNT);
        waitForElementNotVisible(UIMapper.SUBMIT_ACCOUNT);
        waitForElementNotVisible(UIMapper.LOADING);
        return new CampaignPage(this.driver);
    }

    /**
     * click cancel button to cancel form submission
     *
     * @throws Exception
     */
    public void cancelFormSubmission() throws Exception {
        click(UIMapper.CANCEL_ACCOUNT);
        waitForElementNotVisible(UIMapper.CANCEL_ACCOUNT);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    /**
     * search account by client name
     *
     * @param name String
     * @throws InterruptedException
     */
    public void searchByClientName(String name) throws InterruptedException {
        type(UIMapper.SEARCH_CLIENT_NAME_TEXT_BOX, name);
        click(UIMapper.CLIENT_SEARCH_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    /**
     * click stats table header by column index
     *
     * @param column int
     * @throws InterruptedException
     */
    public void clickStatsTableHeaderByColumn(int column) throws InterruptedException {
        click("table[summary-stats='summaryStats']>thead>tr>th:nth-child(" + column + ")");
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    /**
     * get text from stats table cell
     *
     * @param row    int
     * @param column int
     * @return String
     * @throws InterruptedException
     */
    public String getTextFromStatsTable(int row, int column) throws InterruptedException {
        return getTextFromElement("table[summary-stats='summaryStats']>tbody>tr[class='ng-scope']:nth-child(" + row + "n)>td[class='ng-scope ng-binding']:nth-child(" + column + "n)");
    }

    /**
     * get text from stats table header
     *
     * @param column int
     * @return String
     * @throws InterruptedException
     */
    public String getTextFromStatsTableHeader(int column) throws InterruptedException {
        return getTextFromElement("table[summary-stats='summaryStats']>thead>tr>th:nth-child(" + column + ")");
    }

    /**
     * get collection of text from stats table by column index
     *
     * @param row    int
     * @param column int
     * @return ArrayList<String>
     * @throws InterruptedException
     */
    public ArrayList<String> getTextFromStatsTableByColumn(int row, int column) throws InterruptedException {
        ArrayList<String> textList = new ArrayList<>();

        for (int i = 2; i < row + 2; i++) {
            textList.add(getTextFromStatsTable(i, column));
        }

        return textList;
    }

    /**
     * check sorting order of array list
     *
     * @param list  ArrayList<String>
     * @param order OrderType
     * @return boolean
     */
    public boolean isStatsColumnSorted(ArrayList<String> list, OrderType order) {
        if (order.equals(OrderType.DESCENDING)) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (Double.valueOf(list.get(i).replace("%", "").replace("$", "")) < Double.valueOf(list.get(i + 1).replace("%", "").replace("$", ""))) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < list.size() - 1; i++) {
                if (Double.valueOf(list.get(i).replace("%", "").replace("$", "")) > Double.valueOf(list.get(i + 1).replace("%", "").replace("$", ""))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * sorting order enum
     */
    public enum OrderType {
        DESCENDING,
        ASCENDING
    }

    /**
     * get row count of stats table
     *
     * @return row count
     * @throws InterruptedException
     */
    public int getStatsTableRowCount() throws InterruptedException {
        return getElements("table[class='table table-bordered table-hover ng-isolate-scope']>tbody>tr:not(:first-child):not(:last-child)").size();
    }

    /**
     * show specific rows per page in stats table
     *
     * @param row int
     * @throws InterruptedException
     */
    public void showRowsPerPage(int row) throws InterruptedException {
        click(UIMapper.SHOW_ROWS_BUTTON);
        click("span[class='dropdown-select dropdown btn-rows ng-isolate-scope']>div>ul>li:nth-of-type(" + row + ")", LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    /**
     * navigate to account access page at account level
     *
     * @return AccountAccessPage
     * @throws InterruptedException
     */
    public AccountAccessPage clickAccountAccess() throws InterruptedException {
        click(UIMapper.ACCOUNT_SETTINGS_ICON);
        Thread.sleep(1000);
        click("Account Access", LocatorTypeEnum.LINK_TEXT);

        return new AccountAccessPage(this.driver);
    }
}