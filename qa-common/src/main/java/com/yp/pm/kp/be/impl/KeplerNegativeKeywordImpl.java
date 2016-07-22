package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerNegativeKeyword;
import com.yp.pm.kp.enums.model.NegativeKeywordMatchTypeEnum;
import com.yp.pm.kp.enums.model.NegativeKeywordStatusEnum;
import com.yp.pm.kp.model.domain.NegativeKeyword;
import com.yp.pm.kp.service.domain.NegativeKeywordService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class KeplerNegativeKeywordImpl extends BaseKeplerImpl implements KeplerNegativeKeyword {

    @Inject
    private NegativeKeywordService service;

    public NegativeKeyword createNegativeKeyword(long accountId, long campaignId, long adGroupId) throws Exception {
        return createNegativeKeyword(accountId, campaignId, adGroupId, null);
    }

    public NegativeKeyword createNegativeKeyword(long accountId, long campaignId, long adGroupId, String keywordData) throws Exception {
        List<NegativeKeyword> keywordList = new ArrayList<>();

        NegativeKeyword kw = new NegativeKeyword();
        kw.setAccountId(accountId);
        kw.setAdGroupId(adGroupId);
        kw.setCampaignId(campaignId);
        kw.setMatchType(NegativeKeywordMatchTypeEnum.PHRASE);

        if (keywordData == null) {
            kw.setNegativeText("Negative_Keyword");
        } else {
            kw.setNegativeText(keywordData);
        }
        kw.setStatus(NegativeKeywordStatusEnum.ACTIVE);
        kw.setCreateTimestamp(now);
        kw.setLastUpdateTimestamp(now);
        keywordList.add(kw);

        return getNegativeKeywordById(accountId, service.createNegativeKeywords(keywordList).get(0));
    }

    public NegativeKeyword createCampaignNegativeKeyword(long accountId, long campaignId, String keywordData) throws Exception {
        List<NegativeKeyword> keywordList = new ArrayList<>();

        NegativeKeyword kw = new NegativeKeyword();
        kw.setAccountId(accountId);
        kw.setCampaignId(campaignId);
        kw.setMatchType(NegativeKeywordMatchTypeEnum.PHRASE);
        if (keywordData == null) {
            kw.setNegativeText("Negative_Keyword");
        } else {
            kw.setNegativeText(keywordData);
        }
        kw.setStatus(NegativeKeywordStatusEnum.ACTIVE);
        kw.setCreateTimestamp(now);
        kw.setLastUpdateTimestamp(now);
        keywordList.add(kw);

        return getNegativeKeywordById(accountId, service.createNegativeKeywords(keywordList).get(0));
    }

    public NegativeKeyword getNegativeKeywordById(long accountId, long negKeywordId) {
        return service.getNegativeKeyword(accountId, negKeywordId);
    }

    public List<NegativeKeyword> getNegativeKeywordByCampaignId(long accountId, long campaignId) {
        return service.getNegativeKeywords(accountId, campaignId);
    }

    public List<Long> updateNegativeKeywords(List<NegativeKeyword> negativeKeywords) throws Exception {
        return service.updateNegativeKeywords(negativeKeywords);
    }

    public List<Long> deleteNegativeKeywords(long accountId, List<Long> negativeKeywords) throws Exception {
        return service.deleteNegativeKeywords(accountId, negativeKeywords);
    }

    public List<NegativeKeyword> getKeywordByCampaignId(long accountId, long campaignId) {
        return service.getNegativeKeywords(accountId, campaignId);
    }
}
