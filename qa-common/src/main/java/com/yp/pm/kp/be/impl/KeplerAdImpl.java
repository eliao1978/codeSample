package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerAd;
import com.yp.pm.kp.be.query.KeplerAdQuery;
import com.yp.pm.kp.enums.model.AdApprovalStatusEnum;
import com.yp.pm.kp.enums.model.AdEffectiveStatusEnum;
import com.yp.pm.kp.enums.model.AdStatusEnum;
import com.yp.pm.kp.enums.model.AdTypeEnum;
import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.Ad;
import com.yp.pm.kp.service.domain.AdService;
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
public class KeplerAdImpl extends BaseKeplerImpl implements KeplerAd, KeplerAdQuery {

    @Inject
    private AdService adService;

    /**
     * @param accountId long
     * @param adGroupId long
     * @param adsData   JSONArray
     * @return list of ad domain object
     * @throws Exception
     */
    public List<Ad> createAd(long accountId, long adGroupId, JSONArray adsData) throws Exception {
        List<Ad> adList = new ArrayList<>();

        if (adsData != null) {
            for (Object data : adsData) {
                JSONObject adData = (JSONObject) data;
                Ad ad = new Ad();
                ad.setAccountId(accountId);
                ad.setAdGroupId(adGroupId);
                ad.setAdStatus(AdStatusEnum.ACTIVE);
                ad.setAdType(AdTypeEnum.fromValue(adData.get("adType").toString().toLowerCase()));
                ad.setApprovalStatus(AdApprovalStatusEnum.APPROVED);
                ad.setDescription1((adData.get("description1") != null) ? adData.get("description1").toString() : "test description 1");
                ad.setDescription2((adData.get("description2") != null) ? adData.get("description2").toString() : "test description 2");
                ad.setMobilePreferred(true);

                List<String> disapprovalReasonList = new ArrayList<>();
                if (adData.get("disapprovalReason") != null) {
                    disapprovalReasonList.add(adData.get("disapprovalReason").toString());
                } else {
                    disapprovalReasonList.add("test");
                }
                ad.setDisapprovalReasons(disapprovalReasonList);

                ad.setDisplayUrl((adData.get("displayUrl") != null) ? adData.get("displayUrl").toString() : "www.display.com");
                ad.setDestinationUrl((adData.get("destinationUrl") != null) ? adData.get("destinationUrl").toString() : "www.destination.com");
                ad.setHeadline((adData.get("headLine") != null) ? adData.get("headLine").toString() : "test ad headline");
                ad.setEffectiveStatus(AdEffectiveStatusEnum.ACTIVE);
                ad.setCreateTimestamp(now);
                ad.setLastUpdateTimestamp(now);
                adList.add(ad);
            }
        } else {
            Ad ad = new Ad();
            ad.setAccountId(accountId);
            ad.setAdGroupId(adGroupId);
            ad.setAdStatus(AdStatusEnum.ACTIVE);
            ad.setAdType(AdTypeEnum.TEXT);
            ad.setApprovalStatus(AdApprovalStatusEnum.APPROVED);
            ad.setDescription1("test description 1");
            ad.setDescription2("test description 2");
            ad.setMobilePreferred(true);

            List<String> disapprovalReasonList = new ArrayList<>();
            disapprovalReasonList.add("test");
            ad.setDisapprovalReasons(disapprovalReasonList);

            ad.setDisplayUrl("www.display.com");
            ad.setDestinationUrl("www.destination.com");
            ad.setHeadline("test ad headline");
            ad.setEffectiveStatus(AdEffectiveStatusEnum.ACTIVE);
            ad.setCreateTimestamp(now);
            ad.setLastUpdateTimestamp(now);
            adList.add(ad);
        }
        List<Long> adIdList = adService.createAds(adList);

        List<Ad> newAdList = new ArrayList<>();
        for (long id : adIdList) {
            logger.debug("Ad [" + id + "] created");
            newAdList.add(getAdById(accountId, id));
        }
        return newAdList;
    }

    /**
     * @param accountId long
     * @param adId      long
     * @return ad domain object
     */
    public Ad getAdById(long accountId, long adId) {
        return adService.getAd(accountId, adId);
    }


    /**
     * @param accountId long
     * @return list of ad domain object
     */
    public List<Ad> getAdByAccountId(long accountId) {
        return adService.getAds(accountId);
    }

    /**
     * @param accountId long
     * @param adGroupId long
     * @return list of ad domain object
     */
    public List<Ad> getAdByAdGroupId(long accountId, long adGroupId) {
        return adService.getAdsByAdGroupId(accountId, adGroupId);
    }

    public List<Long> deleteAds(long accountId, List<Long> adIds) throws ApiException {
        return adService.deleteAds(accountId, adIds);
    }

    /**
     * @param accountId long
     * @param adGroupId long
     * @param text      String
     * @return Ad
     * @throws Exception
     */
    public Ad getAdByHeadline(long accountId, long adGroupId, String text) throws Exception {
        List<Map<String, Object>> queryList = DBUtil.queryForList(GET_AD_BY_HEADLINE, new Object[]{accountId, adGroupId, text}, new int[]{Types.NUMERIC, Types.NUMERIC, Types.VARCHAR});

        if (queryList.size() > 1) {
            throw new Exception("Multiple ads were found");
        } else if (queryList.size() == 1) {
            return getAdById(accountId, Long.valueOf(queryList.get(0).get("AD_ID").toString()));
        } else {
            return null;
        }
    }

    /**
     * @param ad ad domain object
     * @return updated ad domain object
     * @throws ApiException
     */
    public Ad updateAd(Ad ad) throws ApiException {
        List<Ad> adList = new ArrayList<>();
        adList.add(ad);

        List<Long> ads = adService.updateAds(adList);
        return getAdById(ad.getAccountId(), ads.get(0));
    }

    public List<Long> updateAds(List<Ad> adList) throws ApiException {
        return adService.updateAds(adList);
    }
}
