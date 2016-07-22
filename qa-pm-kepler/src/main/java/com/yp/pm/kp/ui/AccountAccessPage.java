package com.yp.pm.kp.ui;

import com.yp.enums.selenium.LocatorTypeEnum;
import org.openqa.selenium.WebDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AccountAccessPage extends SeleniumWrapper {

    public AccountAccessPage(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
        waitForElementVisible(UIMapper.ADD_USERS_BUTTON);
    }

    /**
     * click add user button
     */
    public void clickAddUserButton() {
        click(UIMapper.ADD_USERS_BUTTON);
    }

    /**
     * enter email address
     *
     * @param email String
     */
    public void enterUserEmail(String email) {
        type(UIMapper.USER_EMAIL_TEXT_BOX, email);
    }

    /**
     * enter user name
     *
     * @param name String
     */
    public void enterUserName(String name) {
        type(UIMapper.USER_NAME_TEXT_BOX, name);
    }

    /**
     * submit new user form to invite users
     */
    public void submitForm() {
        click(UIMapper.SEND_INVITATION_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    /**
     * cancel form submission
     */
    public void cancelFormSubmission() {
        click(UIMapper.USER_CANCEL_BUTTON);
    }

    /**
     * get header text of User Invited table
     *
     * @param col column index
     * @return String
     */
    public String getTextFromUserInvitedTableHeader(int col) {
        return getTextFromElement("//div/table[1]/thead/tr/th[" + col + "]", LocatorTypeEnum.XPATH);
    }

    /**
     * get cell text from User Invited table
     *
     * @param col column index
     * @return text String
     */
    public String getTextFromUserInvitedTable(int row, int col) {
        return getTextFromElement("//div/table[1]/tbody/tr[" + row + "]/td[" + col + "]", LocatorTypeEnum.XPATH);
    }

    /**
     * get header text of User Invited table
     *
     * @param col column index
     * @return String
     */
    public String getTextFromUserAccountAccessHeader(int col) {
        return getTextFromElement("//div/table[2]/thead/tr/th[" + col + "]", LocatorTypeEnum.XPATH);
    }

    /**
     * get cell text from User Account Access table
     *
     * @param col column index
     * @return text String
     */
    public String getTextFromUserAccountAccessTable(int row, int col) {
        return getTextFromElement("//div/table[2]/tbody/tr[" + row + "]/td[" + col + "]", LocatorTypeEnum.XPATH);
    }

    /**
     * search invited user by name
     *
     * @param text user name
     * @throws InterruptedException
     */
    public void searchInvitedUserByName(String text) throws InterruptedException {
        type(UIMapper.USER_INVITED_ACCESS_TEXTBOX, text);
        waitForElementVisible("//tr/td[contains(normalize-space(.),'" + text + "')]", LocatorTypeEnum.XPATH);
        sleep();
    }

    /**
     * search user with account access by name
     *
     * @param text user name
     * @throws InterruptedException
     */
    public void searchUserWithAccountAccessByName(String text) throws InterruptedException {
        type(UIMapper.USER_ACCOUNT_ACCESS_TEXTBOX, text, LocatorTypeEnum.XPATH);
        sleep();
    }

    /**
     * get table row count from Invited User table
     *
     * @return int
     * @throws InterruptedException
     */
    public int getInvitedUserTableRowCount() throws InterruptedException {
        return getElements(UIMapper.USERS_TABLE_ONE, LocatorTypeEnum.XPATH).size();
    }

    /**
     * get table row count from User Account Access table
     *
     * @return int
     * @throws InterruptedException
     */
    public int getUserWithAccountAccessTableRowCount() throws InterruptedException {
        return getElements(UIMapper.USERS_TABLE_TWO, LocatorTypeEnum.XPATH).size();
    }

    /**
     * get current formated date at Account Access page
     *
     * @return String
     * @throws InterruptedException
     */
    public String getCurrentDate() throws InterruptedException {
        String str = "MMM";
        if (str.length() > 3) {
            str = str.substring(3);
        }
        DateFormat dateFormat = new SimpleDateFormat(str + " dd, YYYY");
        Calendar cal = Calendar.getInstance();
        return (dateFormat.format(cal.getTime()));
    }
}