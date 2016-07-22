package com.yp.pm.kp.be;

import com.yp.pm.kp.model.domain.AdGroup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public interface KeplerAdGroup {

    List<AdGroup> createAdGroup(long accountId, long campaignId, JSONArray data) throws Exception;

    AdGroup createAdGroupOnly(long accountId, long campaignId, JSONObject data) throws Exception;

    AdGroup getAdGroupById(long accountId, long adGroupId) throws Exception;

    AdGroup getAdGroupByName(long accountId, String name) throws Exception;

    List<AdGroup> getAdGroupByCampaignId(long accountId, long campaignId) throws Exception;

    List<Long> deleteAdGroups(long accountId, List<Long> adGroupIds) throws Exception;

    List<Long> updateAdGroups(List<AdGroup> adGroups) throws Exception;
}
