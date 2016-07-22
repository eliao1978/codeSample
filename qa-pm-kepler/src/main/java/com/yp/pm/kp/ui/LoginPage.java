package com.yp.pm.kp.ui;

import org.openqa.selenium.WebDriver;

public class LoginPage extends SeleniumWrapper {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public MyAgencyPage authenticate() throws Exception {
        return authenticate(System.getProperty("ui.username"));
    }

    public MyAgencyPage authenticateReadOnly() throws Exception {
        return authenticate(System.getProperty("ui.username.root.readonly"));
    }

    public MyAgencyPage authenticate(String user) throws Exception {
        openBaseUrl();
        if (!isElementVisible(UIMapper.HEADER_USER_NAME)) {
            enterUserName(user);
            enterPassword();
            clickSubmit();
        }
        return new MyAgencyPage(this.driver);
    }

    public MyAgencyPage authenticateNode(String server) throws Exception {
        openBaseNodeUrl(server);
        enterUserName(System.getProperty("ui.username"));
        enterPassword();
        clickSubmit();
        return new MyAgencyPage(this.driver);
    }

    public void openBaseUrl() throws InterruptedException {
        if (System.getProperty("env").equalsIgnoreCase("sbx")) {
            open(System.getProperty("ui.server").concat("/cm"), true);
        } else {
            open(System.getProperty("ui.server").concat("/cm"));
        }
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void openStyleGuide() throws InterruptedException {
        open(System.getProperty("ui.server").concat("/cm/styleguide.html"));
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void openBaseNodeUrl(String server) throws InterruptedException {
        open(server.concat(":7548/cm"));
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterUserName(String user) {
        while (!isElementPresent(UIMapper.USER_INPUT_TEXTBOX)) {
            driver.navigate().refresh();
            waitForElementNotVisible(UIMapper.LOADING);
        }
        type(UIMapper.USER_INPUT_TEXTBOX, user);
    }

    public void enterPassword() {
        type(UIMapper.PASSWORD_INPUT_TEXTBOX, System.getProperty("ui.password"));
    }

    public void clickSubmit() {
        click(UIMapper.LOGIN_FORM_SUBMIT_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
        if (isElementVisible(UIMapper.AGREE_TO_TERMS)) {
            click(UIMapper.AGREE_TO_TERMS);
        }
    }

    public void clickForgotPasswordLink() throws Exception {
        click(UIMapper.FORGOT_PASSWORD_LINK);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void enterEmail(String text) throws Exception {
        type(UIMapper.FORGOT_PASSWORD_EMAIL_INPUT_TEXTBOX, text);
    }

    public void clickForgotPasswordSubmitButton() throws Exception {
        click(UIMapper.FORGOT_PASSWORD_SUBMIT_BUTTON);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public YPSearch openSearch(String url) throws Exception {
        open(url);
        return new YPSearch(this.driver);
    }
}