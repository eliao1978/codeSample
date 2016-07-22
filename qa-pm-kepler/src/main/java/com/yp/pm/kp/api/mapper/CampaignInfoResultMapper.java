package com.yp.pm.kp.api.mapper;

import com.yp.pm.kp.api.dto.CampaignInfoResultDTO;
import org.json.simple.JSONObject;

public class CampaignInfoResultMapper {

    public CampaignInfoResultDTO getCampaignInfoResult(JSONObject json) {
        CampaignInfoResultDTO campaignInfoDTO = new CampaignInfoResultDTO();
        campaignInfoDTO.setAccountId(json.get("accountId") != null ? Long.valueOf(json.get("accountId").toString()) : null);
        campaignInfoDTO.setCampaignId(json.get("campaignId") != null ? Long.valueOf(json.get("campaignId").toString()) : null);
        if(json.get("ypId") != null) {
            campaignInfoDTO.setYpId(json.get("ypId") != null ? Long.valueOf(json.get("ypId").toString()) : null);
        }
        return campaignInfoDTO;
    }
}
