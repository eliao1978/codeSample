package com.yp.pm.kp.ui;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class BudgetPage extends SeleniumWrapper {

    public BudgetPage(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
        waitForElementVisible(UIMapper.CREATE_BUDGET_BUTTON);
    }

    /**
     * click create budget button
     *
     * @throws InterruptedException
     */
    public void clickCreateBudgetButton() throws InterruptedException {
        click(UIMapper.CREATE_BUDGET_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    /**
     * input budget name
     *
     * @param name String
     * @throws InterruptedException
     */
    public void enterBudgetName(String name) throws InterruptedException {
        type(UIMapper.BUDGET_NAME_TEXT_BOX, name);
    }

    /**
     * input budget amount
     *
     * @param amount Double
     */
    public void enterBudgetAmount(Double amount) {
        type(UIMapper.BUDGET_AMOUNT_TEXT_BOX, String.valueOf(amount));
    }

    /**
     * submit budget form
     *
     * @throws InterruptedException
     */
    public void submitForm() throws InterruptedException {
        click(UIMapper.SUBMIT_BUDGET);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    /**
     * cancel form submission
     */
    public void cancelFormSubmission() throws InterruptedException {
        click(UIMapper.CANCEL_BUDGET);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    /**
     * get text from stats table cell
     *
     * @param row    int
     * @param column int
     * @return String
     * @throws InterruptedException
     */
    public String getTextFromStatsTable(int row, int column)  {
        return getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope'][summary-stats='summaryStats']>tbody>tr[class='ng-scope']:nth-child(" + row + ")>td[class='ng-scope ng-binding']:nth-child(" + column + ")");
    }

    /**
     * get text from stats table header
     *
     * @param column int
     * @return String
     * @throws InterruptedException
     */
    public String getTextFromStatsTableHeader(int column) throws InterruptedException {
        return getTextFromElement("table[class='table table-bordered table-hover ng-scope ng-isolate-scope'][summary-stats='summaryStats']>thead>tr>th:nth-child(" + column + ")");
    }

    /**
     * click stats table header by column index
     *
     * @param column int
     * @throws InterruptedException
     */
    public void clickStatsTableHeaderByColumn(int column) throws InterruptedException {
        click("table[class='table table-bordered table-hover ng-scope ng-isolate-scope'][summary-stats='summaryStats']>thead>tr>th:nth-child(" + column + ")");
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
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
     * click delivery method link
     *
     * @throws InterruptedException
     */
    public void clickDeliveryMethodLink() throws InterruptedException {
        click(UIMapper.BUDGET_DELIVERY_METHOD_LINK);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    /**
     * click standard delivery method radio button
     *
     * @throws InterruptedException
     */
    public void clickStandardDeliveryMethodRadioButton() throws InterruptedException {
        click(UIMapper.BUDGET_DELIVERY_METHOD_STANDARD);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    /**
     * click accelerated delivery method radio button
     *
     * @throws InterruptedException
     */
    public void clickAcceleratedDeliveryMethodRadioButton() throws InterruptedException {
        click(UIMapper.BUDGET_DELIVERY_METHOD_ACCELERATED);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    /**
     * click checkbox at budget stats table
     *
     * @param row    int
     * @param column int
     * @throws InterruptedException
     */
    public void clickBudgetStatsTableCheckbox(int row, int column) throws InterruptedException {
        click("table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr:nth-of-type(" + row + ")>td:nth-of-type(" + column + ")>input");
    }

    /**
     * click delete budget button
     *
     * @throws InterruptedException
     */
    public void clickDeleteBudgetButton() throws InterruptedException {
        click(UIMapper.DELETE_BUDGET_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        sleep();
    }

    /**
     * click filter budget button
     *
     * @throws InterruptedException
     */
    public void clickBudgetFilterButton() throws InterruptedException {
        click(UIMapper.FILTER_BUDGET_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    /**
     * select all budget option from the filter dropdown
     *
     * @throws InterruptedException
     */
    public void selectAllBudgets() throws InterruptedException {
        click("span[class='dropdown-select ng-isolate-scope']>div>ul>li:nth-of-type(1)>a");
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    /**
     * select all but deleted budget option from the filter dropdown
     *
     * @throws InterruptedException
     */
    public void selectAllButDeletedBudgets() throws InterruptedException {
        click("span[class='dropdown-select ng-isolate-scope']>div>ul>li:nth-of-type(2)>a");
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }
}