package com.yp.pm.kp.be;

import com.yp.pm.kp.model.domain.NegativeKeyword;

import java.util.List;

public interface KeplerNegativeKeyword {


    NegativeKeyword createNegativeKeyword(long accountId, long campaignId, long adGroupId, String keywordData) throws Exception;

    NegativeKeyword createNegativeKeyword(long accountId, long campaignId, long adGroupId) throws Exception;

    NegativeKeyword createCampaignNegativeKeyword(long accountId, long campaignId, String keywordData) throws Exception;

    NegativeKeyword getNegativeKeywordById(long accountId, long negKeywordId) throws Exception;

    List<NegativeKeyword> getNegativeKeywordByCampaignId(long accountId, long campaignId) throws Exception;

    List<NegativeKeyword> getKeywordByCampaignId(long accountId, long campaignId) throws Exception;

    List<Long> updateNegativeKeywords(List<NegativeKeyword> negativeKeywords) throws Exception;

    List<Long> deleteNegativeKeywords(long accountId, List<Long> negativeKeywords) throws Exception;
}
