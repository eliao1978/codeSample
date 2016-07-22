package com.yp.pm.kp.ui.regression.keyword;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.KeywordApprovalStatusEnum;
import com.yp.pm.kp.enums.model.KeywordMatchTypeEnum;
import com.yp.pm.kp.enums.model.KeywordServingStatusEnum;
import com.yp.pm.kp.enums.model.KeywordStatusEnum;
import com.yp.pm.kp.model.domain.*;
import com.yp.pm.kp.model.other.Bid;
import com.yp.pm.kp.ui.KeywordPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EditKeywordTest extends BaseUITest {

    @Test
    public void testEditKeywordText() throws Exception {
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();
        long adGroupId = ((AdGroup) data.get("adGroup")).getAdGroupId();
        String keyword1 = "KW1_" + System.currentTimeMillis();
        String keyword2 = "KW2_" + System.currentTimeMillis();

        Keyword keyword = new Keyword();
        keyword.setAccountId(accountId);
        keyword.setAdGroupId(getAdGroupByCampaignId(accountId, campaignId).get(0).getAdGroupId());
        keyword.setKeywordStatus(KeywordStatusEnum.ACTIVE);
        keyword.setApprovalStatus(KeywordApprovalStatusEnum.APPROVED);
        keyword.setKeywordText(keyword1);
        keyword.setMatchType(KeywordMatchTypeEnum.BROAD);
        keyword.setMaxCpc(Bid.fromMicroDollar(1000000));
        keyword.setServingStatus(KeywordServingStatusEnum.ACTIVE);
        keyword.setDestinationUrl("www.test.com");
        ArrayList<Keyword> keywords = new ArrayList<>();
        keywords.add(keyword);
        long keywordId1 = createKeyword(keywords).get(0);

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.editKeyword(keyword1);
        keywordPage.SaveEditingKeyWordText(keyword2);

        if (keywordPage.isEditSuccess()) {
            assertConditionTrue(keywordPage.getTableText(2, 3).contains(keyword2));

            List<Keyword> keywordList = getKeywordByAdGroupId(accountId, adGroupId);
            assertConditionTrue(keywordList.size() == 2);

            for (Keyword kw : keywordList) {
                assertConditionTrue(kw.getAccountId() == accountId);
                assertConditionTrue(kw.getAdGroupId() == adGroupId);
                assertConditionTrue(kw.getMaxCpc().toMicroDollar() == 1000000);
                assertConditionTrue(kw.getMatchType().equals(KeywordMatchTypeEnum.BROAD));

                if (kw.getKeywordId() == keywordId1) {
                    assertConditionTrue(kw.getKeywordStatus().equals(KeywordStatusEnum.DELETED));
                    assertConditionTrue(kw.getKeywordText().equalsIgnoreCase(keyword1));
                } else {
                    assertConditionTrue(kw.getKeywordStatus().equals(KeywordStatusEnum.ACTIVE));
                    assertConditionTrue(kw.getKeywordText().equalsIgnoreCase(keyword2));
                }
            }
        }
    }

    @Test
    public void testEditKeywordDestinationHTTP() throws Exception {
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();
        long adGroupId = ((AdGroup) data.get("adGroup")).getAdGroupId();
        String keyword1 = "KW1_" + System.currentTimeMillis();
        String url = "http://www.test.com";
        String error = "Invalid characters in Destination URL";

        Keyword keyword = new Keyword();
        keyword.setAccountId(accountId);
        keyword.setAdGroupId(getAdGroupByCampaignId(accountId, campaignId).get(0).getAdGroupId());
        keyword.setKeywordStatus(KeywordStatusEnum.ACTIVE);
        keyword.setApprovalStatus(KeywordApprovalStatusEnum.APPROVED);
        keyword.setKeywordText(keyword1);
        keyword.setMatchType(KeywordMatchTypeEnum.BROAD);
        keyword.setMaxCpc(Bid.fromMicroDollar(1000000));
        keyword.setServingStatus(KeywordServingStatusEnum.ACTIVE);
        ArrayList<Keyword> keywords = new ArrayList<>();
        keywords.add(keyword);
        createKeyword(keywords).get(0);

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.waitForElementNotVisible(UIMapper.LOADING);
        keywordPage.waitForElementVisible("//td[contains(normalize-space(.),'" + keyword1 + "')]", LocatorTypeEnum.XPATH);
        keywordPage.editDestinationUrl(keyword1, url);
        assertConditionTrue(keywordPage.getError().contains(error));

    }

    @Test
    public void testEditKeywordDestinationHTTPS() throws Exception {
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();
        long adGroupId = ((AdGroup) data.get("adGroup")).getAdGroupId();
        String keyword1 = "KW1_" + System.currentTimeMillis();
        String url = "https://www.test.com";
        String error = "Invalid characters in Destination URL";

        Keyword keyword = new Keyword();
        keyword.setAccountId(accountId);
        keyword.setAdGroupId(getAdGroupByCampaignId(accountId, campaignId).get(0).getAdGroupId());
        keyword.setKeywordStatus(KeywordStatusEnum.ACTIVE);
        keyword.setApprovalStatus(KeywordApprovalStatusEnum.APPROVED);
        keyword.setKeywordText(keyword1);
        keyword.setMatchType(KeywordMatchTypeEnum.BROAD);
        keyword.setMaxCpc(Bid.fromMicroDollar(1000000));
        keyword.setServingStatus(KeywordServingStatusEnum.ACTIVE);
        ArrayList<Keyword> keywords = new ArrayList<>();
        keywords.add(keyword);
        createKeyword(keywords).get(0);

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.waitForElementNotVisible(UIMapper.LOADING);
        keywordPage.waitForElementVisible("//td[contains(normalize-space(.),'" + keyword1 + "')]", LocatorTypeEnum.XPATH);
        keywordPage.editDestinationUrl(keyword1, url);
        assertConditionTrue(keywordPage.getError().contains(error));

    }

    @Test
    public void testCancelEditKeywordText() throws Exception {
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();
        long adGroupId = ((AdGroup) data.get("adGroup")).getAdGroupId();
        String keyword1 = "KW1_" + System.currentTimeMillis();
        String keyword2 = "KW2_" + System.currentTimeMillis();

        Keyword keyword = new Keyword();
        keyword.setAccountId(accountId);
        keyword.setAdGroupId(getAdGroupByCampaignId(accountId, campaignId).get(0).getAdGroupId());
        keyword.setKeywordStatus(KeywordStatusEnum.ACTIVE);
        keyword.setApprovalStatus(KeywordApprovalStatusEnum.APPROVED);
        keyword.setKeywordText(keyword1);
        keyword.setMatchType(KeywordMatchTypeEnum.BROAD);
        keyword.setMaxCpc(Bid.fromMicroDollar(1000000));
        keyword.setServingStatus(KeywordServingStatusEnum.ACTIVE);
        keyword.setDestinationUrl("www.test.com");
        ArrayList<Keyword> keywords = new ArrayList<>();
        keywords.add(keyword);
        long keywordId1 = createKeyword(keywords).get(0);

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.editKeyword(keyword1);
        keywordPage.cancelEditingKeywordText(keyword2);

        assertConditionTrue(keywordPage.getTableText(2, 3).contains(keyword1));

        List<Keyword> keywordList = getKeywordByAdGroupId(accountId, adGroupId);
        assertConditionTrue(keywordList.size() == 1);

        for (Keyword kw : keywordList) {
            assertConditionTrue(kw.getAccountId() == accountId);
            assertConditionTrue(kw.getAdGroupId() == adGroupId);
            assertConditionTrue(kw.getKeywordId() == keywordId1);
            assertConditionTrue(kw.getMaxCpc().toMicroDollar() == 1000000);
            assertConditionTrue(kw.getMatchType().equals(KeywordMatchTypeEnum.BROAD));
            assertConditionTrue(kw.getKeywordStatus().equals(KeywordStatusEnum.ACTIVE));
            assertConditionTrue(kw.getKeywordText().equalsIgnoreCase(keyword1));
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        Map<String, Object> testData = new HashMap<>();
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
        testData.put("account", account);
        testData.put("campaign", campaign);
        testData.put("adGroup", adGroup);

        return testData;
    }
}
