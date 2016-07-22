package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerAccount;
import com.yp.pm.kp.be.query.KeplerAccountQuery;
import com.yp.pm.kp.enums.model.AccountBillingTypeEnum;
import com.yp.pm.kp.enums.model.AccountStatusEnum;
import com.yp.pm.kp.enums.model.TimeZoneEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.service.domain.AccountService;
import com.yp.util.DBUtil;
import com.yp.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KeplerAccountImpl extends BaseKeplerImpl implements KeplerAccount, KeplerAccountQuery {

    @Inject
    private AccountService accountService;

    /**
     * @param agentId    long
     * @param clientName string
     * @param timeZone   string
     * @return Account domain object
     * @throws Exception
     */
    public Account createAccount(long agentId, String clientName, String timeZone) throws Exception {
        List<Account> accountList = new ArrayList<>();
        Account account = new Account();
        account.setAgencyId(agentId);
        account.setAccountStatus(AccountStatusEnum.ACTIVE);
        account.setClientName(clientName);
        account.setCreateDate(DateUtil.getStartDate(now));
        account.setBillingType(AccountBillingTypeEnum.STANDARD);
        account.setTimezone(TimeZoneEnum.fromValue(timeZone));
        account.setCreateTimestamp(now);
        account.setLastUpdateTimestamp(now);
        accountList.add(account);
        List<Long> accountIdList = accountService.createAccounts(accountList);

        logger.debug("Account [" + accountIdList.get(0) + "] created");
        return getAccountById(accountIdList.get(0));
    }

    /**
     * @param agencyId long
     * @return list of account objects
     * @throws Exception
     */
    public List<Account> getAccountByAgencyId(long agencyId) throws Exception {
        return accountService.getAccounts(agencyId);
    }

    public List<Long> deleteAccounts(List<Long> accountIds) throws Exception {
        return accountService.deleteAccounts(accountIds);
    }

    public List<Long> updateAccounts(List<Account> accounts) throws Exception {
        return accountService.updateAccounts(accounts);
    }

    /**
     * @param clientName string
     * @return Account domain object
     * @throws Exception
     */
    public Account getAccountByName(String clientName) throws Exception {
        List<Map<String, Object>> queryList = DBUtil.queryForList(GET_ACCOUNT_ID_BY_NAME, new Object[]{clientName}, new int[]{Types.VARCHAR});

        if (queryList.size() > 1) {
            throw new Exception("Multiple accounts were found");
        } else if (queryList.size() == 1) {
            return getAccountById(Long.valueOf(queryList.get(0).get("ACCOUNT_ID").toString()));
        } else {
            return null;
        }
    }

    /**
     * @param clientName string
     * @return Account domain object
     * @throws Exception
     */
    public Account getActiveAccountByName(String clientName) throws Exception {
        List<Map<String, Object>> queryList = DBUtil.queryForList(GET_ACTIVE_ACCOUNT_ID_BY_NAME, new Object[]{"active", clientName}, new int[]{Types.VARCHAR, Types.VARCHAR});

        if (queryList.size() > 1) {
            logger.debug("Multiple active accounts were found");
            return getAccountById(Long.valueOf(queryList.get(0).get("ACCOUNT_ID").toString()));
        } else if (queryList.size() == 1) {
            if(queryList.get(0).get("FINANCIAL_STATUS").toString().toLowerCase().equals("paused")) {
                logger.info("FINANCIAL_STATUS has been set to PAUSED");
            }
            return getAccountById(Long.valueOf(queryList.get(0).get("ACCOUNT_ID").toString()));
        } else {
            return null;
        }
    }

    /**
     * @param accountId long
     * @return Account domain object
     */
    public Account getAccountById(long accountId) {
        return accountService.getAccount(accountId);
    }
}
