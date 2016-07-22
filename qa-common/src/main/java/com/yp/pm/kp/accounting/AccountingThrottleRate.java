package com.yp.pm.kp.accounting;

import com.yp.pm.kp.model.domain.ThrottleRate;

import java.util.List;

public interface AccountingThrottleRate {

    boolean createThrottleRate(List<ThrottleRate> throttleRates) throws Exception;

    int deleteThrottleRate(long accountId, long campaignId);

    ThrottleRate getThrottleRate(long accountId, long campaignId);

    int updateThrottleRate(List<ThrottleRate> throttleRates);
}