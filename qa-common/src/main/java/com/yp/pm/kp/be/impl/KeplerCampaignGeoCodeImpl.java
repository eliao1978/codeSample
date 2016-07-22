package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerCampaignGeoCode;
import com.yp.pm.kp.be.query.KeplerCampaignGeoCodeQuery;
import com.yp.pm.kp.enums.model.CampaignGeoCodeStatusEnum;
import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.CampaignGeoCode;
import com.yp.pm.kp.service.domain.CampaignGeoCodeService;
import com.yp.util.DBUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KeplerCampaignGeoCodeImpl extends BaseKeplerImpl implements KeplerCampaignGeoCode, KeplerCampaignGeoCodeQuery {

    @Inject
    private CampaignGeoCodeService campaignGeoCodeService;

    /**
     * @param accountId            long
     * @param campaignId           long
     * @param campaignGeoCodesData JSONArray
     * @return list of campaign geo code domain object
     * @throws Exception
     */
    public List<CampaignGeoCode> createCampaignGeoCode(long accountId, long campaignId, JSONArray campaignGeoCodesData) throws Exception {
        List<CampaignGeoCode> campaignGeoCodeList = new ArrayList<CampaignGeoCode>();

        if (campaignGeoCodesData != null) {
            for (Object data : campaignGeoCodesData) {
                JSONObject campaignGeoCodeData = (JSONObject) data;
                CampaignGeoCode geoCode = new CampaignGeoCode();
                geoCode.setAccountId(accountId);
                geoCode.setCampaignId(campaignId);
                geoCode.setExcluded((campaignGeoCodeData != null && campaignGeoCodeData.get("excluded") != null) ? Boolean.valueOf(campaignGeoCodeData.get("excluded").toString()) : false);
                geoCode.setGeoLibId(getGeoLibId(campaignGeoCodeData));
                geoCode.setBidModifier((campaignGeoCodeData != null && campaignGeoCodeData.get("bidModifier") != null) ? Double.valueOf(campaignGeoCodeData.get("bidModifier").toString()) : 5.0);
                geoCode.setGeoCodeStatus(CampaignGeoCodeStatusEnum.ACTIVE);
                geoCode.setCreateTimestamp(now);
                geoCode.setLastUpdateTimestamp(now);
                campaignGeoCodeList.add(geoCode);
            }
        } else {
            CampaignGeoCode geoCode = new CampaignGeoCode();
            geoCode.setAccountId(accountId);
            geoCode.setCampaignId(campaignId);
            geoCode.setExcluded(false);
            geoCode.setGeoLibId(getGeoLibId(null));
            geoCode.setBidModifier(5.0);
            geoCode.setGeoCodeStatus(CampaignGeoCodeStatusEnum.ACTIVE);
            geoCode.setCreateTimestamp(now);
            geoCode.setLastUpdateTimestamp(now);
            campaignGeoCodeList.add(geoCode);
        }

        List<Long> geoCodeIdList = campaignGeoCodeService.createCampaignGeoCodes(campaignGeoCodeList);

        List<CampaignGeoCode> campaignGeoCodeObjectList = new ArrayList<CampaignGeoCode>();
        for (long id : geoCodeIdList) {
            logger.debug("Geo Code [" + id + "] created");
            campaignGeoCodeObjectList.add(getCampaignGeoCodeById(accountId, campaignId, id));
        }

        return campaignGeoCodeObjectList;
    }

    /**
     * @param accountId  long
     * @param campaignId long
     * @param geoCodeId  long
     * @return campaign geo code domain object
     */
    public CampaignGeoCode getCampaignGeoCodeById(long accountId, long campaignId, long geoCodeId) {
        return campaignGeoCodeService.getCampaignGeoCode(accountId, campaignId, geoCodeId);
    }

    public List<Long> updateCampaignGeoCodes(List<CampaignGeoCode> geoCodes) throws ApiException {
        return campaignGeoCodeService.updateCampaignGeoCodes(geoCodes);
    }

    public List<Long> deleteCampaignGeoCodes(long accountId, long campaignId, List<Long> geoCodeIdList) throws ApiException {
        return campaignGeoCodeService.deleteCampaignGeoCodes(accountId, campaignId, geoCodeIdList);
    }

    /**
     * @param accountId  long
     * @param campaignId long
     * @return list of campaign geo code domain object
     */
    public List<CampaignGeoCode> getCampaignGeoCodeByCampaignId(long accountId, long campaignId) {
        return campaignGeoCodeService.getCampaignGeoCodes(accountId, campaignId);
    }

    /**
     * @param data JSONObject
     * @return geo lib id long
     * @throws SQLException
     */
    public long getGeoLibId(JSONObject data) throws SQLException {
        long geoLibId;

        if (data != null) {
            if (data.get("geoLibId") != null) {
                geoLibId = Long.valueOf(data.get("geoLibId").toString());
            } else {
                List<Map<String, Object>> queryList = DBUtil.queryForList(GET_GEO_LIB_ID, new Object[]{1, data.get("geoLocationType").toString(), data.get("geoLocationName").toString()}, new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR});
                geoLibId = Long.valueOf(queryList.get(0).get("GEO_LIB_ID").toString());
            }
        } else { // return default location id
            List<Map<String, Object>> queryList = DBUtil.queryForList(GET_GEO_LIB_ID, new Object[]{1, "COUNTRY", "USA"}, new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR});
            geoLibId = Long.valueOf(queryList.get(0).get("GEO_LIB_ID").toString());
        }

        return geoLibId;
    }
}