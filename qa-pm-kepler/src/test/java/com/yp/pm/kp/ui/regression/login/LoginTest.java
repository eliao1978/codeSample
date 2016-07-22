package com.yp.pm.kp.ui.regression.login;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.ui.LoginPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTest extends BaseUITest {

    @Test
    public void assertLoginWithInvalidEmail() throws Exception {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.openBaseUrl();
        loginPage.enterUserName("ats.com");
        loginPage.enterPassword();
        loginPage.clickSubmit();
        assertConditionTrue(loginPage.getTextFromElement(UIMapper.INVALID_EMAIL_FORMAT_MSG).contains("Invalid email format"));
    }

    @Test
    public void assertLoginWithUnknownUser() throws Exception {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.openBaseUrl();
        loginPage.enterUserName("unknown_user@yp.com");
        loginPage.enterPassword();
        loginPage.clickSubmit();
        String error = loginPage.getTextFromElement(UIMapper.LOGIN_ERROR);
        logger.info("Message: " + error);
        assertConditionTrue(error.contains("User does not exist"));
    }


    @Test
    public void assertMissingLoginCredential() throws Exception {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.openBaseUrl();
        loginPage.clickSubmit();
        assertConditionTrue(loginPage.getTextFromElement(UIMapper.MISSING_EMAIL_MSG).contains("Email address is required"));
        assertConditionTrue(loginPage.getTextFromElement(UIMapper.MISSING_PASSWORD_MSG).contains("Password is required"));
    }

    @Test
    public void assertForgetPasswordWithInvalidEmail() throws Exception {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.openBaseUrl();
        loginPage.clickForgotPasswordLink();
        loginPage.enterEmail("invalidEmail");
        loginPage.clickForgotPasswordSubmitButton();
        assertConditionTrue(loginPage.getTextFromElement(UIMapper.FORGOT_PASSWORD_INVALID_EMAIL_MSG).contains("Invalid email format"));
    }

    @Test
    public void assertForgetPasswordWithMissingEmail() throws Exception {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.openBaseUrl();
        loginPage.clickForgotPasswordLink();
        loginPage.clickForgotPasswordSubmitButton();
        assertConditionTrue(loginPage.getTextFromElement(UIMapper.FORGOT_PASSWORD_MISSING_EMAIL_MSG).contains("Email address is required"));
    }
}