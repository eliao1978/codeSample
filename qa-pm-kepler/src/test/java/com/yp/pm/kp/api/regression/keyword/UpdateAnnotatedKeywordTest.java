package com.yp.pm.kp.api.regression.keyword;

import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.enums.model.KeywordApprovalStatusEnum;
import com.yp.pm.kp.enums.model.KeywordStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.Keyword;
import com.yp.pm.kp.model.other.Bid;
import com.yp.util.DBUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ar2130 on 1/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateAnnotatedKeywordTest extends BaseAPITest {
    @Test
    public void testUpdateKeywordText() throws Exception {
        Account account = getTestData();
        Campaign campaign = getCampaignByAccountId(account.getAccountId()).get(0);
        AdGroup adGroup = getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0);
        Keyword keyword = getKeywordByAdGroupId(account.getAccountId(), adGroup.getAdGroupId()).get(0);
        logger.debug("KeywordId: " + keyword.getKeywordId());
        logger.debug("Disapproval Reasons: " + keyword.getDisapprovalReasons());
        //assertConditionTrue(keyword.getDisapprovalReasons().toString().contains("keyword contains adult related word"));
        String newTerm = "pinga";
        updateApprovalStatus(account.getAccountId().toString(), keyword.getKeywordId().toString(), true);
        Keyword newKeyword = new Keyword();
        newKeyword.setAccountId(account.getAccountId());
        newKeyword.setKeywordId(keyword.getKeywordId());
        newKeyword.setAdGroupId(getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0).getAdGroupId());
        newKeyword.setKeywordText(keyword.getKeywordText() + " " + newTerm);
        ArrayList<Keyword> updateKeywordList = new ArrayList<>();
        updateKeywordList.add(newKeyword);
        Long upKeywordId = updateKeywords(updateKeywordList).get(0);
        logger.debug("Updated Keyword: " + upKeywordId);
        Keyword nuKeyword = getKeywordById(account.getAccountId(), upKeywordId);
        Thread.sleep(3000);
        logger.debug("Disapproval Reasons: " + nuKeyword.getDisapprovalReasons());
        //assertConditionTrue(nuKeyword.getDisapprovalReasons().toString().contains("keyword contains adult related word"));
        assertConditionTrue("ApprovalStatus was not UNDER REVIEW",nuKeyword.getApprovalStatus().equals(KeywordApprovalStatusEnum.UNDER_REVIEW));

    }

    @Test
    public void testUpdateKeywordTextToClean() throws Exception {
        Account account = getTestData();
        Campaign campaign = getCampaignByAccountId(account.getAccountId()).get(0);
        AdGroup adGroup = getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0);
        Keyword keyword = getKeywordByAdGroupId(account.getAccountId(), adGroup.getAdGroupId()).get(0);
        logger.debug("KeywordId: " + keyword.getKeywordId());
        String newTerm = "robot";
        updateApprovalStatus(account.getAccountId().toString(), keyword.getKeywordId().toString(), true);
        Keyword newKeyword = new Keyword();
        newKeyword.setAccountId(account.getAccountId());
        newKeyword.setKeywordId(keyword.getKeywordId());
        newKeyword.setAdGroupId(getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0).getAdGroupId());
        newKeyword.setKeywordText(newTerm);
        ArrayList<Keyword> updateKeywordList = new ArrayList<>();
        updateKeywordList.add(newKeyword);
        Long upKeywordId = updateKeywords(updateKeywordList).get(0);
        logger.debug("Updated Keyword: " + upKeywordId);
        Keyword nuKeyword = getKeywordById(account.getAccountId(), upKeywordId);
        Thread.sleep(3000);
        assertObjectNull(nuKeyword.getDisapprovalReasons());
        assertConditionTrue("ApprovalStatus was not APPROVED",nuKeyword.getApprovalStatus().equals(KeywordApprovalStatusEnum.APPROVED));

    }

    @Test
    public void testUpdateKeywordMaxCPC() throws Exception {
        Account account = getTestData();
        Campaign campaign = getCampaignByAccountId(account.getAccountId()).get(0);
        AdGroup adGroup = getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0);
        Keyword keyword = getKeywordByAdGroupId(account.getAccountId(), adGroup.getAdGroupId()).get(0);
        updateApprovalStatus(account.getAccountId().toString(), keyword.getKeywordId().toString(), true);
        logger.debug("KeywordId: " + keyword.getKeywordId());
        Keyword newKeyword = new Keyword();
        newKeyword.setAccountId(account.getAccountId());
        newKeyword.setKeywordId(keyword.getKeywordId());
        newKeyword.setAdGroupId(getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0).getAdGroupId());
        newKeyword.setMaxCpc(Bid.fromMicroDollar(700000));
        ArrayList<Keyword> updateKeywordList = new ArrayList<>();
        updateKeywordList.add(newKeyword);
        Long upKeywordId = updateKeywords(updateKeywordList).get(0);
        logger.debug("Updated Keyword: " + upKeywordId);
        Keyword nuKeyword = getKeywordById(account.getAccountId(), upKeywordId);
        Thread.sleep(3000);
        assertConditionTrue("ApprovalStatus was not APPROVED",nuKeyword.getApprovalStatus().equals(KeywordApprovalStatusEnum.APPROVED));

    }

    @Test
    public void testUpdateKeywordDestinationUrl() throws Exception {
        Account account = getTestData();
        Campaign campaign = getCampaignByAccountId(account.getAccountId()).get(0);
        AdGroup adGroup = getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0);
        Keyword keyword = getKeywordByAdGroupId(account.getAccountId(), adGroup.getAdGroupId()).get(0);
        logger.debug("KeywordId: " + keyword.getKeywordId());
        String newTerm = "pinga";
        updateApprovalStatus(account.getAccountId().toString(), keyword.getKeywordId().toString(), true);
        Keyword newKeyword = new Keyword();
        newKeyword.setAccountId(account.getAccountId());
        newKeyword.setKeywordId(keyword.getKeywordId());
        newKeyword.setAdGroupId(getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0).getAdGroupId());
        newKeyword.setDestinationUrl("www.robot" + newTerm + ".com");
        ArrayList<Keyword> updateKeywordList = new ArrayList<>();
        updateKeywordList.add(newKeyword);
        Long upKeywordId = updateKeywords(updateKeywordList).get(0);
        logger.debug("Updated Keyword: " + upKeywordId);
        Keyword nuKeyword = getKeywordById(account.getAccountId(), upKeywordId);
        Thread.sleep(3000);
        assertConditionTrue("ApprovalStatus was not Under Review",nuKeyword.getApprovalStatus().equals(KeywordApprovalStatusEnum.UNDER_REVIEW));

    }

    @Test
    public void testUpdateKeywordStatus() throws Exception {
        Account account = getTestData();
        Campaign campaign = getCampaignByAccountId(account.getAccountId()).get(0);
        AdGroup adGroup = getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0);
        Keyword keyword = getKeywordByAdGroupId(account.getAccountId(), adGroup.getAdGroupId()).get(0);
        updateApprovalStatus(account.getAccountId().toString(), keyword.getKeywordId().toString(), true);
        logger.debug("KeywordId: " + keyword.getKeywordId());
        Keyword newKeyword = new Keyword();
        newKeyword.setAccountId(account.getAccountId());
        newKeyword.setKeywordId(keyword.getKeywordId());
        newKeyword.setAdGroupId(getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0).getAdGroupId());
        newKeyword.setKeywordStatus(KeywordStatusEnum.PAUSED);
        ArrayList<Keyword> updateKeywordList = new ArrayList<>();
        updateKeywordList.add(newKeyword);
        Long upKeywordId = updateKeywords(updateKeywordList).get(0);
        logger.debug("Updated Keyword: " + upKeywordId);
        Keyword nuKeyword = getKeywordById(account.getAccountId(), upKeywordId);
        assertConditionTrue("ApprovalStatus was not APPROVED",nuKeyword.getApprovalStatus().equals(KeywordApprovalStatusEnum.APPROVED));

        Keyword activeKeyword = new Keyword();
        activeKeyword.setAccountId(account.getAccountId());
        activeKeyword.setKeywordId(nuKeyword.getKeywordId());
        activeKeyword.setAdGroupId(getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0).getAdGroupId());
        activeKeyword.setKeywordStatus(KeywordStatusEnum.ACTIVE);
        ArrayList<Keyword> activeKeywordList = new ArrayList<>();
        activeKeywordList.add(activeKeyword);
        Long activeKeywordId = updateKeywords(activeKeywordList).get(0);
        Keyword finalKeyword = getKeywordById(account.getAccountId(), activeKeywordId);
        Thread.sleep(3000);
        assertConditionTrue("ApprovalStatus was not APPROVED",finalKeyword.getApprovalStatus().equals(KeywordApprovalStatusEnum.APPROVED));

    }

    private Account getTestData() throws Exception {
        List<Map<String, Object>> AdultKeywordList = DBUtil.queryForList("SELECT * FROM (SELECT DISTINCT(ENTRY_KEY) FROM NG_WORDSET WHERE COMPONENT_TYPE = 'AD_COPY' AND DICTIONARY_NAME = 'adult' AND MATCHING_LEVEL='TOKEN' AND STATUS != 'DELETED' ORDER BY ENTRY_KEY) WHERE ROWNUM <= 50");
        Account account = createAccount(agencyId, "SERENITY_" + System.currentTimeMillis(), "America/Los_Angeles");
        Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        AdGroup adGroup = createAdGroupOnly(campaign.getAccountId(), campaign.getCampaignId());

        JSONArray kwJSONList = new JSONArray();
        String entryKey = AdultKeywordList.get(1).get("ENTRY_KEY").toString();

        JSONObject json2 = new JSONObject();
        json2.put("keywordText", entryKey);
        json2.put("matchType", "EXACT");
        json2.put("maxCPC", "1.00");
        kwJSONList.add(json2);

        createKeyword(adGroup.getAccountId(), adGroup.getAdGroupId(), kwJSONList);
        return account;

    }

    private void updateApprovalStatus(String accountId, String id, Boolean approval) {
        String status;
        if (approval) {
            status = "approved";
        }
        else {
            status = "disapproved";
        }
        String sql1 = "UPDATE PM_KEYWORD SET APPROVAL_STATUS = '" + status + "' WHERE ACCOUNT_ID=" + accountId + " AND KEYWORD_ID=" + id;
        DBUtil.update(sql1);
    }
}
