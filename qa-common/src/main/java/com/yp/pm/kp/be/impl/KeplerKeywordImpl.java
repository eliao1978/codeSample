package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerKeyword;
import com.yp.pm.kp.enums.model.KeywordMatchTypeEnum;
import com.yp.pm.kp.enums.model.KeywordServingStatusEnum;
import com.yp.pm.kp.enums.model.KeywordStatusEnum;
import com.yp.pm.kp.model.domain.Keyword;
import com.yp.pm.kp.model.other.Bid;
import com.yp.pm.kp.service.domain.KeywordService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class KeplerKeywordImpl extends BaseKeplerImpl implements KeplerKeyword {

    @Inject
    private KeywordService keywordService;

    /**
     * @param accountId   long
     * @param adGroupId   long
     * @param keywordData JSONArray
     * @return List<Keyword>
     * @throws Exception
     */
    public List<Keyword> createKeyword(long accountId, long adGroupId, JSONArray keywordData) throws Exception {
        List<Keyword> keywordList = new ArrayList<>();

        if (keywordData == null) {
            Keyword kw = new Keyword();
            kw.setAccountId(accountId);
            kw.setAdGroupId(adGroupId);
            kw.setKeywordStatus(KeywordStatusEnum.ACTIVE);
            kw.setKeywordText("keyword_" + System.currentTimeMillis());
            kw.setMatchType(KeywordMatchTypeEnum.EXACT);
            kw.setMaxCpc(Bid.fromDollar(5));
            kw.setServingStatus(KeywordServingStatusEnum.ACTIVE);
            kw.setDestinationUrl("www.yp.com");
            keywordList.add(kw);
        } else {
            for (Object item : keywordData) {
                JSONObject keyword = (JSONObject) item;
                Keyword kw = new Keyword();
                kw.setAccountId(accountId);
                kw.setAdGroupId(adGroupId);
                kw.setKeywordStatus(KeywordStatusEnum.ACTIVE);
                kw.setKeywordText(keyword.get("keywordText").toString());
                kw.setMatchType(KeywordMatchTypeEnum.fromValue(keyword.get("matchType").toString().toLowerCase()));
                kw.setMaxCpc(Bid.fromDollar(Double.valueOf(keyword.get("maxCPC").toString())));
                kw.setDestinationUrl((keyword.get("destinationURL") != null) ? keyword.get("destinationURL").toString() : null);
                kw.setServingStatus(KeywordServingStatusEnum.ACTIVE);
                keywordList.add(kw);
            }
        }
        List<Long> keywordIdList = createKeyword(keywordList);

        List<Keyword> keywords = new ArrayList<>();
        for (long id : keywordIdList) {
            logger.debug("Keyword [" + id + "] created");
            keywords.add(getKeywordById(accountId, id));
        }

        return keywords;
    }

    /**
     * @param keywords List<Keyword>
     * @return List<Long>
     * @throws Exception
     */
    public List<Long> createKeyword(List<Keyword> keywords) throws Exception {
        return keywordService.createKeywords(keywords);
    }

    public List<Long> deleteKeywords(long accountId, List<Long> keywordIds) throws Exception {
        return keywordService.deleteKeywords(accountId, keywordIds);
    }

    /**
     * @param accountId long
     * @param keywordId long
     * @return keyword
     */
    public Keyword getKeywordById(long accountId, long keywordId) throws Exception {
        return keywordService.getKeyword(accountId, keywordId);
    }

    /**
     * @param accountId long
     * @param adGroupId long
     * @return List<Keyword>
     * @throws Exception
     */
    public List<Keyword> getKeywordByAdGroupId(long accountId, long adGroupId) throws Exception {
        return keywordService.getKeywords(accountId, adGroupId);
    }

    /**
     * @param accountId long
     * @return List<Keyword>
     */
    public List<Keyword> getKeywordByAccountId(long accountId) throws Exception {
        return keywordService.getKeywords(accountId);
    }

    /**
     * update keywords
     *
     * @param keywords List<Keyword>
     * @return List<Long>
     * @throws Exception
     */
    public List<Long> updateKeywords(List<Keyword> keywords) throws Exception {
        return keywordService.updateKeywords(keywords);
    }
}
