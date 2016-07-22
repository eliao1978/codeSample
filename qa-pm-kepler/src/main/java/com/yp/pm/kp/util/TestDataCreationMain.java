package com.yp.pm.kp.util;

import com.yp.enums.other.UserEnum;
import com.yp.pm.kp.be.*;
import com.yp.pm.kp.enums.model.*;
import com.yp.pm.kp.model.domain.*;
import com.yp.pm.kp.security.AuthSecurityUser;
import com.yp.pm.kp.security.RoleEnum;
import com.yp.util.LogUtil;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class TestDataCreationMain {
    private static Logger logger = new LogUtil().getLogger();
    private static Logger file = new LogUtil().getFileLogger();

    public static void main(String args[]) throws Exception {
        setup(System.getProperty("env"));

        AutowireCapableBeanFactory beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml").getAutowireCapableBeanFactory();
        KeplerAccount accountDAO = beanFactory.getBean(KeplerAccount.class);

        JSONArray accountList = getJSONData();
        Boolean locationAdExtensionStatus = false;
        for (Object item : accountList) {
            JSONObject data = (JSONObject) item;
            JSONObject accountData = (JSONObject) data.get("account");

            Account account = accountDAO.getActiveAccountByName(accountData.get("clientName").toString());
            if (account == null) {
                logger.info("Account [".concat(accountData.get("clientName").toString()).concat("] doesn't exist. Create a new one."));
                KeplerBudget budgetDAO = beanFactory.getBean(KeplerBudget.class);
                KeplerCampaign campaignDAO = beanFactory.getBean(KeplerCampaign.class);
                KeplerPhoneAdExtension phoneAdExtensionDAO = beanFactory.getBean(KeplerPhoneAdExtension.class);
                KeplerLocationAdExtension locationAdExtensionDAO = beanFactory.getBean(KeplerLocationAdExtension.class);
                KeplerCampaignAdExtension campaignAdExtension = beanFactory.getBean(KeplerCampaignAdExtension.class);


                JSONObject budgetData = (JSONObject) data.get("budget");
                JSONArray campaignData = (JSONArray) data.get("campaign");
                JSONObject phoneAdExtData = (JSONObject) data.get("phoneAdExtensions");
                JSONArray locationAdExtData = (JSONArray) data.get("locationAdExtensions");

                if (locationAdExtData != null && locationAdExtensionStatus) {
                    continue;
                }

                Account newAccount = accountDAO.createAccount(Long.valueOf(accountData.get("agentId").toString()), accountData.get("clientName").toString(), accountData.get("timeZone").toString());
                Budget budget = budgetDAO.createBudget(newAccount.getAccountId(), Long.valueOf(budgetData.get("amount").toString()), budgetData.get("deliverMethod").toString(), null);
                Campaign newCampaign = campaignDAO.createCampaign(newAccount.getAccountId(), budget.getBudgetId(), campaignData).get(0);
                try {
                    if (phoneAdExtData != null) {
                        PhoneAdExtension phoneAdExtension = new PhoneAdExtension();
                        phoneAdExtension.setAccountId(newAccount.getAccountId());
                        phoneAdExtension.setPhoneNumber(phoneAdExtData.get("phoneNumber").toString());
                        phoneAdExtension.setAdExtensionStatus(PhoneAdExtensionStatusEnum.ACTIVE);
                        phoneAdExtension.setIsCallableOnly(true);
                        PhoneAdExtension newPhoneAdExt = phoneAdExtensionDAO.createPhoneAdExtension(phoneAdExtension);
                        logger.debug("Phone Ad Extension Id: ".concat(newPhoneAdExt.getAdExtensionId().toString()));

                        List<CampaignAdExtension> list = new ArrayList<>();
                        CampaignAdExtension adExtension = new CampaignAdExtension();
                        adExtension.setAdExtensionId(newPhoneAdExt.getAdExtensionId());
                        adExtension.setAccountId(newAccount.getAccountId());
                        adExtension.setCampaignId(newCampaign.getCampaignId());
                        adExtension.setAdExtensionStatus(CampaignAdExtensionStatusEnum.ACTIVE);
                        adExtension.setApprovalStatus(CampaignAdExtensionApprovalStatusEnum.APPROVED);
                        adExtension.setAdExtensionType(CampaignAdExtensionTypeEnum.PHONE);
                        list.add(adExtension);
                        campaignAdExtension.createCampaignAdExtensions(list);
                        logger.debug("Creating campaignAdExtension for phoneAdExtension");
                    }

                    if (locationAdExtData != null) {
                        for (Object obj : locationAdExtData) {
                            JSONObject extension = (JSONObject) obj;
                            LocationAdExtension locationAdExtension = new LocationAdExtension();
                            locationAdExtension.setAccountId(newAccount.getAccountId());
                            locationAdExtension.setAddressLine1(extension.get("addressLine1").toString());
                            if (extension.get("addressLine2") != null) {
                                locationAdExtension.setAddressLine2(extension.get("addressLine2").toString());
                            }
                            locationAdExtension.setCity(extension.get("city").toString());
                            locationAdExtension.setState(extension.get("state").toString());
                            locationAdExtension.setZip(extension.get("zip").toString());
                            if (extension.get("phone_number") != null) {
                                locationAdExtension.setPhoneNumber(extension.get("phone_number").toString());
                            }
                            locationAdExtension.setCompanyName(extension.get("companyName").toString());
                            locationAdExtension.setAdExtensionStatus(LocationAdExtensionStatusEnum.ACTIVE);
                            LocationAdExtension newLocationAdExtension = locationAdExtensionDAO.createLocationAdExtension(locationAdExtension);
                            logger.debug("Location Ad Extension Id: ".concat(newLocationAdExtension.getAdExtensionId().toString()));

                            List<CampaignAdExtension> list2 = new ArrayList<>();
                            CampaignAdExtension adExtension2 = new CampaignAdExtension();
                            adExtension2.setAdExtensionId(newLocationAdExtension.getAdExtensionId());
                            adExtension2.setAccountId(newAccount.getAccountId());
                            adExtension2.setCampaignId(newCampaign.getCampaignId());
                            adExtension2.setAdExtensionStatus(CampaignAdExtensionStatusEnum.ACTIVE);
                            adExtension2.setApprovalStatus(CampaignAdExtensionApprovalStatusEnum.APPROVED);
                            adExtension2.setAdExtensionType(CampaignAdExtensionTypeEnum.LOCATION);

                            list2.add(adExtension2);
                            campaignAdExtension.createCampaignAdExtensions(list2);
                            logger.debug("Creating campaignAdExtension for locationAdExtension");
                        }
                    }
                } catch (Exception e) {
                    logger.debug(e);
                    locationAdExtensionStatus = true;
                    List<Long> deleteAccounts = new ArrayList<>();
                    deleteAccounts.add(newAccount.getAccountId());
                    accountDAO.deleteAccounts(deleteAccounts);
                    logger.debug("Deleting account " + newAccount.getAccountId());


                }
                logger.info("Account [".concat(newAccount.getClientName()).concat("] is created"));
                logger.info("Account ID [".concat(newAccount.getAccountId().toString()).concat("]"));
                logger.info("");
            } else {
                logger.info("Account [".concat(account.getClientName()).concat("] exists. Skip data creation."));
                logger.info("Account ID [".concat(account.getAccountId().toString()).concat("]"));
                logger.info("");
            }
        }
        if (locationAdExtensionStatus) {
            logger.info("Problem creating some accounts with locationAdExtensions, will need to rerun at a later time.");
        }
    }

    private static void setup(String env) {
        setUserAuthority();
        setSystemProperty(env);
    }

    private static void setUserAuthority() {
        AuthSecurityUser user = new AuthSecurityUser();
        user.setName(UserEnum.KP_SUPER_ADMIN.getValue());
        user.setRequestId(UUID.randomUUID().toString());
        user.addAuthority(RoleEnum.ROLE_SUPER_ADMIN.name());
        PreAuthenticatedAuthenticationToken authToken = new PreAuthenticatedAuthenticationToken(user, null, user.getAuthorities());
        authToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private static void setSystemProperty(String env) {
        Properties config = new Properties(System.getProperties());
        try {
            config.load(new FileInputStream("src/main/resources/config/".concat(env).concat(".properties")));
        } catch (IOException e) {
            e.getMessage();
        }
        System.setProperties(config);
    }

    private static JSONArray getJSONData() throws Exception {
        JSONArray accountList = (JSONArray) ((JSONObject) new JSONParser().parse(new FileReader("src/main/resources/data/test_data.json"))).get("accounts");
        file.info(new JSONParser().parse(new FileReader("src/main/resources/data/test_data.json")));
        logger.info("[" + accountList.size() + "] accounts were found in JSON.");
        return accountList;
    }

}
