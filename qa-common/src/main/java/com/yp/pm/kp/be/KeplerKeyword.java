package com.yp.pm.kp.be;

import com.yp.pm.kp.model.domain.Keyword;
import org.json.simple.JSONArray;

import java.util.List;

public interface KeplerKeyword {

    List<Keyword> createKeyword(long accountId, long adGroupId, JSONArray data) throws Exception;

    List<Long> createKeyword(List<Keyword> keywords) throws Exception;

    Keyword getKeywordById(long accountId, long keywordId) throws Exception;

    List<Keyword> getKeywordByAccountId(long accountId) throws Exception;

    List<Keyword> getKeywordByAdGroupId(long accountId, long adGroupId) throws Exception;

    List<Long> deleteKeywords(long accountId, List<Long> keywordIds) throws Exception;

    List<Long> updateKeywords(List<Keyword> keywords) throws Exception;
}
