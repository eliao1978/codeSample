package com.yp.pm.kp.accounting.impl;

import com.yp.pm.accounting.service.ThrottleRateService;
import com.yp.pm.kp.accounting.AccountingThrottleRate;
import com.yp.pm.kp.model.domain.ThrottleRate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class AccountingThrottleRateImpl extends BaseAccountingImpl implements AccountingThrottleRate {

    @Inject
    private ThrottleRateService throttleRateService;

    public boolean createThrottleRate(List<ThrottleRate> throttleRates) throws Exception {
        return throttleRateService.insertThrottleRate(throttleRates);
    }

    public int deleteThrottleRate(long accountId, long campaignId) {
        return throttleRateService.deleteThrottleRate(accountId, campaignId);
    }

    public ThrottleRate getThrottleRate(long accountId, long campaignId) {
        return throttleRateService.getThrottleRate(accountId, campaignId);
    }

    public int updateThrottleRate(List<ThrottleRate> throttleRates) {
        return throttleRateService.updateThrottleRate(throttleRates);
    }
}
