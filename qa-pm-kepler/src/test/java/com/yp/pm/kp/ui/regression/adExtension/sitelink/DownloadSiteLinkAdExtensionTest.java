package com.yp.pm.kp.ui.regression.adExtension.sitelink;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.CampaignAdExtensionApprovalStatusEnum;
import com.yp.pm.kp.enums.model.CampaignAdExtensionStatusEnum;
import com.yp.pm.kp.enums.model.CampaignAdExtensionTypeEnum;
import com.yp.pm.kp.model.domain.*;
import com.yp.pm.kp.ui.AdExtensionSiteLinkExtension;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.yp.pm.kp.model.domain.SiteLinkAdExtension;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class DownloadSiteLinkAdExtensionTest extends BaseUITest {

    @Test
    public void testDownloadSiteLinkExcelCsv() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long accountId = account.getAccountId();
        long campaignId = campaign.getCampaignId();
        List<SiteLinkAdExtension> siteLinkList = getSiteLinkAdExtensionByAccountId(accountId);
        SiteLinkAdExtension siteLinkAdExtension;
        int count;
        for (count = 0; count < 5; count++) {
            if (siteLinkList.size() == 0) {
            siteLinkAdExtension = createSiteLinkAdExtension(accountId);
            List<CampaignAdExtension> list = new ArrayList<>();
            CampaignAdExtension adExtension = new CampaignAdExtension();
            adExtension.setAdExtensionId(siteLinkAdExtension.getAdExtensionId());
            adExtension.setAccountId(accountId);
            adExtension.setCampaignId(campaign.getCampaignId());
            adExtension.setAdExtensionStatus(CampaignAdExtensionStatusEnum.ACTIVE);
            adExtension.setApprovalStatus(CampaignAdExtensionApprovalStatusEnum.APPROVED);
            adExtension.setAdExtensionType(CampaignAdExtensionTypeEnum.SITE_LINKS);
            list.add(adExtension);
            createCampaignAdExtensions(list);
            logger.debug("Creating new SitelinkAdExtension");
            count ++;
            }
        }
        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId,account.getAccountId(),campaignId);
        siteLinkExtension.click(UIMapper.AD_EXTENSIONS_CHECKALL, LocatorTypeEnum.CSS);
        siteLinkExtension.clickDownloadButton();
        siteLinkExtension.clickDownloadButtonListOptions("excelCsv");
    }

    @Test
    public void testDownloadSiteLinkCsv() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long accountId = account.getAccountId();
        long campaignId = campaign.getCampaignId();
        List<SiteLinkAdExtension> siteLinkList = getSiteLinkAdExtensionByAccountId(accountId);
        SiteLinkAdExtension siteLinkAdExtension;
        int count;
        for (count = 0; count < 5; count++) {
            if (siteLinkList.size() == 0) {
                siteLinkAdExtension = createSiteLinkAdExtension(accountId);
                List<CampaignAdExtension> list = new ArrayList<>();
                CampaignAdExtension adExtension = new CampaignAdExtension();
                adExtension.setAdExtensionId(siteLinkAdExtension.getAdExtensionId());
                adExtension.setAccountId(accountId);
                adExtension.setCampaignId(campaign.getCampaignId());
                adExtension.setAdExtensionStatus(CampaignAdExtensionStatusEnum.ACTIVE);
                adExtension.setApprovalStatus(CampaignAdExtensionApprovalStatusEnum.APPROVED);
                adExtension.setAdExtensionType(CampaignAdExtensionTypeEnum.SITE_LINKS);
                list.add(adExtension);
                createCampaignAdExtensions(list);
                logger.debug("Creating new SitelinkAdExtension");
                count ++;
            }
        }
        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId,account.getAccountId(),campaignId);
        siteLinkExtension.click(UIMapper.AD_EXTENSIONS_CHECKALL, LocatorTypeEnum.CSS);
        siteLinkExtension.clickDownloadButton();
        siteLinkExtension.clickDownloadButtonListOptions("csv");
    }

    @Test
    public void testDownloadSiteLinkExcelCsvGz() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long accountId = account.getAccountId();
        long campaignId = campaign.getCampaignId();
        List<SiteLinkAdExtension> siteLinkList = getSiteLinkAdExtensionByAccountId(accountId);
        SiteLinkAdExtension siteLinkAdExtension;
        int count;
        for (count = 0; count < 5; count++) {
            if (siteLinkList.size() == 0) {
                siteLinkAdExtension = createSiteLinkAdExtension(accountId);
                List<CampaignAdExtension> list = new ArrayList<>();
                CampaignAdExtension adExtension = new CampaignAdExtension();
                adExtension.setAdExtensionId(siteLinkAdExtension.getAdExtensionId());
                adExtension.setAccountId(accountId);
                adExtension.setCampaignId(campaign.getCampaignId());
                adExtension.setAdExtensionStatus(CampaignAdExtensionStatusEnum.ACTIVE);
                adExtension.setApprovalStatus(CampaignAdExtensionApprovalStatusEnum.APPROVED);
                adExtension.setAdExtensionType(CampaignAdExtensionTypeEnum.SITE_LINKS);
                list.add(adExtension);
                createCampaignAdExtensions(list);
                logger.debug("Creating new SitelinkAdExtension");
                count ++;
            }
        }
        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId,account.getAccountId(),campaignId);
        siteLinkExtension.click(UIMapper.AD_EXTENSIONS_CHECKALL, LocatorTypeEnum.CSS);
        siteLinkExtension.clickDownloadButton();
        siteLinkExtension.clickDownloadButtonListOptions("excelCsvGz");
    }

    @Test
    public void testDownloadSiteLinkCsvGz() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long accountId = account.getAccountId();
        long campaignId = campaign.getCampaignId();
        List<SiteLinkAdExtension> siteLinkList = getSiteLinkAdExtensionByAccountId(accountId);
        SiteLinkAdExtension siteLinkAdExtension;
        int count;
        for (count = 0; count < 5; count++) {
            if (siteLinkList.size() == 0) {
                siteLinkAdExtension = createSiteLinkAdExtension(accountId);
                List<CampaignAdExtension> list = new ArrayList<>();
                CampaignAdExtension adExtension = new CampaignAdExtension();
                adExtension.setAdExtensionId(siteLinkAdExtension.getAdExtensionId());
                adExtension.setAccountId(accountId);
                adExtension.setCampaignId(campaign.getCampaignId());
                adExtension.setAdExtensionStatus(CampaignAdExtensionStatusEnum.ACTIVE);
                adExtension.setApprovalStatus(CampaignAdExtensionApprovalStatusEnum.APPROVED);
                adExtension.setAdExtensionType(CampaignAdExtensionTypeEnum.SITE_LINKS);
                list.add(adExtension);
                createCampaignAdExtensions(list);
                logger.debug("Creating new SitelinkAdExtension");
                count ++;
            }
        }
        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId,account.getAccountId(),campaignId);
        siteLinkExtension.click(UIMapper.AD_EXTENSIONS_CHECKALL, LocatorTypeEnum.CSS);
        siteLinkExtension.clickDownloadButton();
        siteLinkExtension.clickDownloadButtonListOptions("csvGz");
    }
}