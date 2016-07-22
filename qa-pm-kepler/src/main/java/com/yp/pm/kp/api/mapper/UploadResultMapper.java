package com.yp.pm.kp.api.mapper;

import com.yp.pm.kp.api.dto.UploadResultDTO;
import org.json.simple.JSONObject;

public class UploadResultMapper {

    public UploadResultDTO getUploadResult(JSONObject json) {
        UploadResultDTO uploadResultDTO = new UploadResultDTO();
        JSONObject result = (JSONObject) json.get("objectTypeCountMap");
        uploadResultDTO.setCampaignNegativeCount(result.get("CAMPAIGN_NEGATIVE") != null ? Long.valueOf(result.get("CAMPAIGN_NEGATIVE").toString()) : null);
        uploadResultDTO.setAdGroupCount(result.get("AD_GROUP") != null ? Long.valueOf(result.get("AD_GROUP").toString()) : null);
        uploadResultDTO.setCampaignCount(result.get("CAMPAIGN") != null ? Long.valueOf(result.get("CAMPAIGN").toString()) : null);
        uploadResultDTO.setBudgetCount(result.get("BUDGET") != null ? Long.valueOf(result.get("BUDGET").toString()) : null);
        uploadResultDTO.setAdCount(result.get("AD") != null ? Long.valueOf(result.get("AD").toString()) : null);
        uploadResultDTO.setLocationCount(result.get("LOCATION") != null ? Long.valueOf(result.get("LOCATION").toString()) : null);
        uploadResultDTO.setAdScheduleCount(result.get("AD_SCHEDULE") != null ? Long.valueOf(result.get("AD_SCHEDULE").toString()) : null);
        uploadResultDTO.setKeywordCount(result.get("KEYWORD") != null ? Long.valueOf(result.get("KEYWORD").toString()) : null);
        org.json.simple.JSONArray errorArray = (org.json.simple.JSONArray) json.get("errors");
        if(errorArray.size() != 0) {
            JSONObject error = (JSONObject) errorArray.get(0);
            uploadResultDTO.setError(error.get("errorMessage") != null ? error.get("errorMessage").toString() : null);
            uploadResultDTO.setErrorRow(error.get("rowNumber") != null ? Long.valueOf(error.get("rowNumber").toString()) : null);
        }
        return uploadResultDTO;
    }
}
