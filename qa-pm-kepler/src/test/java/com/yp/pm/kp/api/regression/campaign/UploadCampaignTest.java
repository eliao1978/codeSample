package com.yp.pm.kp.api.regression.campaign;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.model.domain.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UploadCampaignTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();

    //TODO: sample test for 107 release
    @Test
    public void testAdd1Campaign() throws Exception {
        Account account = createAccount(31298, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        String file = System.getProperty("user.dir") + "/src/test/resources/data/1campaign.xls";

        String command = "curl -F file=\"@" + file + "\" " +
                "-H 'username:'kepler@yp.com'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + account.getAccountId() + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/upload";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.UPLOAD, getAPIResponse(command));
        assertResponseError(responseDTO);
        //TODO: sample response
        //{"objectTypeCountMap":{"CAMPAIGN_NEGATIVE":333,"AD_GROUP":34,"CAMPAIGN":4,"BUDGET":4,"AD":201,"LOCATION":4,"AD_SCHEDULE":24,"KEYWORD":1974},"errors":[],"errorFilePath":null}

        //TODO: validate all kepler objects
        JSONObject data = ((JSONObject) new JSONParser().parse(new FileReader(System.getProperty("user.dir") + "/src/test/resources/data/add1campaign.json")));
        JSONObject budgetData = (JSONObject) data.get("budget");
        Budget newBudget = getBudgetByAccountId(account.getAccountId()).get(0);
        assertConditionTrue(budgetData.get("name").toString().equalsIgnoreCase(newBudget.getName()));
        assertConditionTrue(budgetData.get("amount").equals(newBudget.getAmount().toDollar()));
        JSONArray campaignData = (JSONArray) data.get("campaign");
        List<Campaign> newCampaigns = getCampaignByAccountId(account.getAccountId());
        ArrayList<String> campaignNames = new ArrayList<>();
        for (Campaign newCampaign : newCampaigns) {
            campaignNames.add(newCampaign.getCampaignName());
        }
        assertConditionTrue(campaignData.size() == newCampaigns.size());
        for (int i = 0; i < campaignData.size(); i++) {
            JSONObject tempCampaign = (JSONObject) campaignData.get(i);
            assertConditionTrue(campaignNames.contains(tempCampaign.get("campaignName").toString()));
            for (Campaign newCampaign : newCampaigns) {
                if (tempCampaign.get("campaignName").toString().equalsIgnoreCase(newCampaign.getCampaignName())) {
                    List<AdGroup> newAdGroups = getAdGroupByCampaignId(account.getAccountId(), newCampaign.getCampaignId());
                    ArrayList<String> adGroupNames = new ArrayList<>();
                    for (AdGroup newAdGroup : newAdGroups) {
                        adGroupNames.add(newAdGroup.getAdGroupName());
                    }
                    JSONArray adGroupData = (JSONArray) tempCampaign.get("adGroup");
                    assertConditionTrue(adGroupData.size() == newAdGroups.size());
                    JSONObject tempCampaignSchedule = (JSONObject) tempCampaign.get("campaignAdSchedule");
                    JSONArray campaignScheduleDays = (JSONArray) tempCampaignSchedule.get("days");
                    List<CampaignAdSchedule> newCampaignSchedules = getCampaignAdScheduleByCampaignId(account.getAccountId(), newCampaign.getCampaignId());
                    assertConditionTrue(campaignScheduleDays.size() == newCampaignSchedules.size());

                    for (int k = 0; k < adGroupData.size(); k++) {
                        JSONObject tempAdGroup = (JSONObject) adGroupData.get(k);
                        assertConditionTrue(adGroupNames.contains(tempAdGroup.get("adGroupName").toString()));
                        for (AdGroup newAdGroup : newAdGroups) {
                            if (tempAdGroup.get("adGroupName").toString().equalsIgnoreCase(newAdGroup.getAdGroupName())) {
                                assertConditionTrue(tempAdGroup.get("maxCPC").equals(newAdGroup.getMaxCpc().toDollar()));
                                List<Ad> newAds = getAdByAdGroupId(account.getAccountId(), newAdGroup.getAdGroupId());
                                ArrayList<String> adDesc1 = new ArrayList<>();
                                for (Ad newAd : newAds) {
                                    adDesc1.add(newAd.getDescription1());
                                }
                                JSONArray adData = (JSONArray) tempAdGroup.get("ad");
                                assertConditionTrue(adData.size() == newAds.size());
                                ArrayList<String> keywordTexts = new ArrayList<>();
                                List<Keyword> newKeywords = getKeywordByAdGroupId(account.getAccountId(), newAdGroup.getAdGroupId());
                                for (Keyword newKeyword : newKeywords) {
                                    keywordTexts.add(newKeyword.getKeywordText());
                                }
                                JSONArray keywordData = (JSONArray) tempAdGroup.get("keyword");
                                assertConditionTrue(keywordData.size() == newKeywords.size());
                                for (int l = 0; l < adData.size(); l++) {
                                    JSONObject tempAd = (JSONObject) adData.get(l);
                                    assertConditionTrue(adDesc1.contains(tempAd.get("description1").toString()));
                                    for (Ad newAd : newAds) {
                                        if (tempAd.get("headLine").toString().equalsIgnoreCase(newAd.getHeadline()) && tempAd.get("description1").toString().equalsIgnoreCase(newAd.getDescription1())) {
                                            assertConditionTrue(tempAd.get("description2").toString().equalsIgnoreCase(newAd.getDescription2()));
                                            assertConditionTrue(tempAd.get("displayUrl").toString().equalsIgnoreCase(newAd.getDisplayUrl()));
                                            assertConditionTrue(tempAd.get("destinationUrl").toString().equalsIgnoreCase(newAd.getDestinationUrl()));
                                            break;
                                        }
                                    }
                                }
                                for (int m = 0; m < keywordData.size(); m++) {
                                    JSONObject tempKeyword = (JSONObject) keywordData.get(m);
                                    assertConditionTrue(keywordTexts.contains(tempKeyword.get("keywordText").toString()));
                                    for (Keyword newKeyword : newKeywords) {
                                        if (tempKeyword.get("keywordText").toString().equalsIgnoreCase(newKeyword.getKeywordText())) {
                                            assertConditionTrue(tempKeyword.get("matchType").toString().equalsIgnoreCase(newKeyword.getMatchType().toString()));
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                        }

                    }
                    break;
                }
            }

        }
    }
}