package com.yp.pm.kp.ui;

import com.yp.enums.selenium.LocatorTypeEnum;
import org.openqa.selenium.WebDriver;

public class ReportPage extends SeleniumWrapper {

    public ReportPage(WebDriver driver) throws InterruptedException {
        super(driver);
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);
    }

    public void downloadReportCancelButton() throws InterruptedException {
        click(UIMapper.DOWNLOAD_REPORT_CANCEL_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public String getReportsText() throws InterruptedException {
        return getTextFromElement("div[class='pm-container no-lnav']>div[class='pm-body-title']>h1", LocatorTypeEnum.CSS);
    }

    public String getDownloadReportsText() throws InterruptedException {
        return getTextFromElement("div[class='form-horizontal ng-pristine ng-invalid ng-invalid-required']>h4", LocatorTypeEnum.CSS);
    }

    public boolean checkTableHeader(String text) throws InterruptedException {
        return isElementPresent("//tr/th[normalize-space(.)='" + text + "']", LocatorTypeEnum.XPATH);
    }

    public void clickReportingHeader() throws InterruptedException {
        click(UIMapper.REPORTING_HEADER_TAB, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCreateReport() throws InterruptedException {
        click(UIMapper.CREATE_REPORT_BUTTON, LocatorTypeEnum.CSS);
        waitForElementNotVisible(UIMapper.LOADING);
    }

    public void clickCampaignHeaderTab() throws InterruptedException {
        click(UIMapper.AGENCY_HEADER_TAB, LocatorTypeEnum.XPATH);
        waitForElementNotVisible(UIMapper.LOADING);
    }

}