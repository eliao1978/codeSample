package com.yp.pm.kp.be;

import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.Ad;
import org.json.simple.JSONArray;

import java.util.List;

public interface KeplerAd {

    List<Ad> createAd(long accountId, long adGroupId, JSONArray data) throws Exception;

    Ad getAdById(long accountId, long adId) throws Exception;

    List<Ad> getAdByAccountId(long accountId) throws Exception;

    List<Ad> getAdByAdGroupId(long accountId, long adGroupId) throws Exception;

    Ad getAdByHeadline(long accountId, long adGroupId, String text) throws Exception;

    Ad updateAd(Ad ad) throws ApiException;

    List<Long> updateAds(List<Ad> adList) throws ApiException;

    List<Long> deleteAds(long accountId, List<Long> adIds) throws ApiException;
}
