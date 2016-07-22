package com.yp.pm.kp.be;

import com.yp.pm.kp.model.domain.Account;

import java.util.List;

public interface KeplerAccount {

    Account createAccount(long agentId, String clientName, String timeZone) throws Exception;

    List<Account> getAccountByAgencyId(long agencyId) throws Exception;

    List<Long> deleteAccounts(List<Long> accountIds) throws Exception;

    List<Long> updateAccounts(List<Account> accounts) throws Exception;

    Account getAccountById(long accountId);

    Account getAccountByName(String name) throws Exception;

    Account getActiveAccountByName(String name) throws Exception;
}
