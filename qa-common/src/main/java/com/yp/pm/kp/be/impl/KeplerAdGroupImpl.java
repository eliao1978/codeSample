package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerAdGroup;
import com.yp.pm.kp.be.query.KeplerAdGroupQuery;
import com.yp.pm.kp.enums.model.AdGroupEffectiveStatusEnum;
import com.yp.pm.kp.enums.model.AdGroupStatusEnum;
import com.yp.pm.kp.model.domain.Ad;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Keyword;
import com.yp.pm.kp.model.domain.NegativeKeyword;
import com.yp.pm.kp.model.other.Bid;
import com.yp.pm.kp.service.domain.AdGroupService;
import com.yp.util.DBUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KeplerAdGroupImpl extends BaseKeplerImpl implements KeplerAdGroup, KeplerAdGroupQuery {

    @Inject
    private AdGroupService adGroupService;
    @Inject
    private KeplerAdImpl adDao;
    @Inject
    private KeplerKeywordImpl keywordDao;
    @Inject
    private KeplerNegativeKeywordImpl negativeKeywordDao;


    /**
     * method to create ad group along with ad and keyword
     *
     * @param accountId    long
     * @param campaignId   long
     * @param adGroupsData JSONArray
     * @return List<AdGroup>
     * @throws Exception
     */
    public List<AdGroup> createAdGroup(long accountId, long campaignId, JSONArray adGroupsData) throws Exception {
        List<AdGroup> adGroupList = new ArrayList<>();
        if (adGroupsData != null) {
            for (Object data : adGroupsData) {
                JSONObject adGroupData = (JSONObject) data;
                AdGroup adGroup = createAdGroupOnly(accountId, campaignId, adGroupData);
                adGroupList.add(adGroup);
                createAd(accountId, adGroup.getAdGroupId(), (adGroupData.get("ad") != null) ? (JSONArray) (adGroupData.get("ad")) : null);
                createKeyword(accountId, adGroup.getAdGroupId(), (adGroupData.get("keyword") != null) ? (JSONArray) (adGroupData.get("keyword")) : null);
                createNegativeKeyword(accountId, campaignId, adGroup.getAdGroupId());
            }
        } else {
            AdGroup adGroup = createAdGroupOnly(accountId, campaignId, null);
            adGroupList.add(adGroup);
            createAd(accountId, adGroup.getAdGroupId(), null);
            createKeyword(accountId, adGroup.getAdGroupId(), null);
            createNegativeKeyword(accountId, campaignId, adGroup.getAdGroupId());
        }

        return adGroupList;
    }

    /**
     * method to create only ad group
     *
     * @param accountId   long
     * @param campaignId  long
     * @param adGroupData JSONObject
     * @return AdGroup
     * @throws Exception
     */
    public AdGroup createAdGroupOnly(long accountId, long campaignId, JSONObject adGroupData) throws Exception {
        List<AdGroup> adGroupList = new ArrayList<>();
        AdGroup group = new AdGroup();
        group.setAccountId(accountId);
        group.setCampaignId(campaignId);
        group.setEffectiveStatus(AdGroupEffectiveStatusEnum.ACTIVE);
        group.setCreateTimestamp(now);
        group.setLastUpdateTimestamp(now);
        group.setAdGroupStatus(AdGroupStatusEnum.ACTIVE);
        group.setAdGroupName((adGroupData != null && adGroupData.get("adGroupName") != null) ? adGroupData.get("adGroupName").toString() : "ADGROUP_" + System.currentTimeMillis());
        group.setMaxCpc((adGroupData != null && adGroupData.get("maxCPC") != null) ? Bid.fromDollar(Long.valueOf(adGroupData.get("maxCPC").toString())) : Bid.fromDollar(1));
        if(adGroupData != null && adGroupData.get("mobileBidModifier") != null) {
            group.setMobileBidModifier(Double.valueOf(adGroupData.get("mobileBidModifier").toString()));
        }
        adGroupList.add(group);
        List<Long> adGroupIdList = adGroupService.createAdGroups(adGroupList);

        logger.debug("Ad Group [" + adGroupIdList.get(0) + "] created");
        return getAdGroupById(accountId, adGroupIdList.get(0));
    }

    /**
     * @param accountId long
     * @param adGroupId long
     * @return AdGroup
     * @throws Exception
     */
    public AdGroup getAdGroupById(long accountId, long adGroupId) throws Exception {
        return adGroupService.getAdGroup(accountId, adGroupId);
    }

    /**
     * @param accountId long
     * @param name      String
     * @return AdGroup
     * @throws Exception
     */
    public AdGroup getAdGroupByName(long accountId, String name) throws Exception {
        List<Map<String, Object>> queryList = DBUtil.queryForList(GET_AD_GROUP_ID_BY_NAME, new Object[]{accountId, name}, new int[]{Types.NUMERIC, Types.VARCHAR});

        if (queryList.size() > 1) {
            throw new Exception("Multiple ad groups were found");
        } else if (queryList.size() == 1) {
            return getAdGroupById(accountId, Long.valueOf(queryList.get(0).get("ADGROUP_ID").toString()));
        } else {
            return null;
        }
    }

    /**
     * @param accountId  long
     * @param campaignId long
     * @return List<AdGroup>
     * @throws Exception
     */
    public List<AdGroup> getAdGroupByCampaignId(long accountId, long campaignId) throws Exception {
        return adGroupService.getAdGroups(accountId, campaignId);
    }

    public List<Long> deleteAdGroups(long accountId, List<Long> adGroupIds) throws Exception {
        return adGroupService.deleteAdGroups(accountId, adGroupIds);
    }

    public List<Long> updateAdGroups(List<AdGroup> adGroups) throws Exception {
        return adGroupService.updateAdGroups(adGroups);
    }

    /**
     * @param accountId long
     * @param adGroupId long
     * @param adData    JSONArray
     * @return List<Ad>
     * @throws Exception
     */
    private List<Ad> createAd(long accountId, long adGroupId, JSONArray adData) throws Exception {
        return adDao.createAd(accountId, adGroupId, adData);
    }

    /**
     * @param accountId   long
     * @param adGroupId   long
     * @param keywordData JSONArray
     * @return List<Keyword>
     * @throws Exception
     */
    private List<Keyword> createKeyword(long accountId, long adGroupId, JSONArray keywordData) throws Exception {
        return keywordDao.createKeyword(accountId, adGroupId, keywordData);
    }

    /**
     * @param accountId  long
     * @param campaignId long
     * @param adGroupId  long
     * @return NegativeKeyword
     * @throws Exception
     */
    private NegativeKeyword createNegativeKeyword(long accountId, long campaignId, long adGroupId) throws Exception {
        return negativeKeywordDao.createNegativeKeyword(accountId, campaignId, adGroupId, null);
    }
}
