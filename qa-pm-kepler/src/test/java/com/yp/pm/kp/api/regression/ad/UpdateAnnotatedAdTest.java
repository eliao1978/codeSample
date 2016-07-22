package com.yp.pm.kp.api.regression.ad;

import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.enums.model.AdApprovalStatusEnum;
import com.yp.pm.kp.enums.model.AdStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Ad;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.util.DBUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by ar2130 on 1/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateAnnotatedAdTest extends BaseAPITest {
    @Test
    public void testUpdateAd() throws Exception {
        Account account = getTestData();
        Campaign campaign = getCampaignByAccountId(account.getAccountId()).get(0);
        AdGroup adGroup = getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0);
        Ad ad = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId()).get(0);
        logger.debug("AdId: " + ad.getAdId());
        logger.debug("Disapproval Reasons: " + ad.getDisapprovalReasons());
        updateApprovalStatus(account.getAccountId().toString(), ad.getAdId().toString(), false);
        //assertConditionTrue(ad.getDisapprovalReasons().toString().contains("headline contains adult related word"));
        //assertConditionTrue(ad.getDisapprovalReasons().toString().contains("description1 contains adult related word"));
        //assertConditionTrue(ad.getDisapprovalReasons().toString().contains("description2 contains adult related word"));
        Ad upAd = new Ad();
        upAd.setAccountId(account.getAccountId());
        upAd.setAdId(ad.getAdId());
        upAd.setAdGroupId(adGroup.getAdGroupId());
        String newTerm = " pinga";
        upAd.setHeadline(ad.getHeadline() + newTerm);
        upAd.setDescription1(ad.getDescription1() + newTerm);
        upAd.setDescription2(ad.getDescription2() + newTerm);
        Ad newAd = updateAd(upAd);
        logger.debug("Update Ad: " + newAd.getAdId());
        Thread.sleep(3000);
        logger.debug("Disapproval Reasons: " + newAd.getDisapprovalReasons());
        //assertConditionTrue(newAd.getDisapprovalReasons().toString().contains("headline contains adult related word"));
        //assertConditionTrue(newAd.getDisapprovalReasons().toString().contains("description1 contains adult related word"));
        //assertConditionTrue(newAd.getDisapprovalReasons().toString().contains("description2 contains adult related word"));
        assertConditionTrue("ApprovalStatus was not DISAPPROVED", newAd.getApprovalStatus().equals(AdApprovalStatusEnum.UNDER_REVIEW));

    }

    @Test
    public void testUpdateAdToClean() throws Exception {
        Account account = getTestData();
        Campaign campaign = getCampaignByAccountId(account.getAccountId()).get(0);
        AdGroup adGroup = getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0);
        Ad ad = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId()).get(0);
        logger.debug("AdId: " + ad.getAdId());
        Ad upAd = new Ad();
        upAd.setAccountId(account.getAccountId());
        upAd.setAdId(ad.getAdId());
        upAd.setAdGroupId(adGroup.getAdGroupId());
        String newTerm = "robot";
        upAd.setHeadline(newTerm);
        upAd.setDescription1(newTerm);
        upAd.setDescription2(newTerm);
        Ad newAd = updateAd(upAd);
        logger.debug("Update Ad: " + newAd.getAdId());
        Thread.sleep(3000);
        assertObjectNull(newAd.getDisapprovalReasons());
        assertConditionTrue("ApprovalStatus was not APPROVED",newAd.getApprovalStatus().equals(AdApprovalStatusEnum.APPROVED));

    }

    @Test
    public void testUpdateAdStatus() throws Exception {
        Account account = getTestData();
        Campaign campaign = getCampaignByAccountId(account.getAccountId()).get(0);
        AdGroup adGroup = getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0);
        Ad ad = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId()).get(0);
        logger.debug("AdId: " + ad.getAdId());
        updateApprovalStatus(account.getAccountId().toString(), ad.getAdId().toString(), true);
        Ad upAd = new Ad();
        upAd.setAccountId(account.getAccountId());
        upAd.setAdId(ad.getAdId());
        upAd.setAdGroupId(adGroup.getAdGroupId());
        upAd.setAdStatus(AdStatusEnum.PAUSED);
        Ad newAd = updateAd(upAd);
        assertConditionTrue("ApprovalStatus was not APPROVED",newAd.getApprovalStatus().equals(AdApprovalStatusEnum.APPROVED));

        logger.debug("Update Ad: " + newAd.getAdId());
        Ad activeAd = new Ad();
        activeAd.setAccountId(account.getAccountId());
        activeAd.setAdId(newAd.getAdId());
        activeAd.setAdGroupId(adGroup.getAdGroupId());
        activeAd.setAdStatus(AdStatusEnum.ACTIVE);
        Ad finalAd = updateAd(activeAd);
        Thread.sleep(3000);
        assertConditionTrue("ApprovalStatus was not APPROVED",finalAd.getApprovalStatus().equals(AdApprovalStatusEnum.APPROVED));

    }

    private Account getTestData() throws Exception {
        List<Map<String, Object>> AdultKeywordList = DBUtil.queryForList("SELECT * FROM (SELECT DISTINCT(ENTRY_KEY) FROM NG_WORDSET WHERE COMPONENT_TYPE = 'AD_COPY' AND DICTIONARY_NAME = 'adult' AND MATCHING_LEVEL='TOKEN' AND STATUS != 'DELETED' ORDER BY ENTRY_KEY) WHERE ROWNUM <= 50");
        Account account = createAccount(agencyId, "SERENITY_" + System.currentTimeMillis(), "America/Los_Angeles");
        Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        AdGroup adGroup = createAdGroupOnly(campaign.getAccountId(), campaign.getCampaignId());

        JSONArray adJSONList = new JSONArray();
        String entryKey = AdultKeywordList.get(1).get("ENTRY_KEY").toString();


        JSONObject json = new JSONObject();
        if (entryKey.length() > 35) {
            json.put("description1", entryKey.substring(0, 34));
            json.put("description2", entryKey.substring(0, 34));
        } else {
            json.put("description1", entryKey);
            json.put("description2", entryKey);
        }
        if (entryKey.length() > 25) {
            json.put("headLine", entryKey.substring(0, 24));
        } else {
            json.put("headLine", entryKey);
        }
        json.put("displayUrl", "www.robot.com");
        json.put("destinationUrl", "http://www.robot.com");
        json.put("adType", "text");
        adJSONList.add(json);

        createAd(adGroup.getAccountId(), adGroup.getAdGroupId(), adJSONList);
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
        String sql1 = "UPDATE PM_AD SET APPROVAL_STATUS = '" + status + "' WHERE ACCOUNT_ID=" + accountId + " AND AD_ID=" + id;
        DBUtil.update(sql1);
    }
}
