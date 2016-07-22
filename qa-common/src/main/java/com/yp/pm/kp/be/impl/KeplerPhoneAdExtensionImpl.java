package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerPhoneAdExtension;
import com.yp.pm.kp.enums.model.PhoneAdExtensionStatusEnum;
import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.PhoneAdExtension;
import com.yp.pm.kp.service.domain.PhoneAdExtensionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class KeplerPhoneAdExtensionImpl extends BaseKeplerImpl implements KeplerPhoneAdExtension {

    @Inject
    private PhoneAdExtensionService service;

    /**
     * @param accountId long
     * @return PhoneAdExtension
     * @throws Exception
     */
    public PhoneAdExtension createPhoneAdExtension(long accountId) throws Exception {
        PhoneAdExtension phoneAdExtension = new PhoneAdExtension();
        phoneAdExtension.setAccountId(accountId);
        phoneAdExtension.setCountryCode("1");
        phoneAdExtension.setPhoneNumber("8182758445");
        phoneAdExtension.setIsCallableOnly(true);
        phoneAdExtension.setAdExtensionStatus(PhoneAdExtensionStatusEnum.ACTIVE);

        return createPhoneAdExtension(phoneAdExtension);
    }

    /**
     * @param extension PhoneAdExtension
     * @return PhoneAdExtension
     * @throws Exception
     */
    public PhoneAdExtension createPhoneAdExtension(PhoneAdExtension extension) throws Exception {
        List<PhoneAdExtension> phoneAdExtensionArrayList = new ArrayList<>();
        phoneAdExtensionArrayList.add(extension);

        return getPhoneAdExtensionById(extension.getAccountId(), service.createPhoneAdExtensions(phoneAdExtensionArrayList).get(0));
    }

    /**
     * @param accountId     long
     * @param adExtensionId long
     * @return PhoneAdExtension
     */
    public PhoneAdExtension getPhoneAdExtensionById(long accountId, long adExtensionId) {
        return service.getPhoneAdExtension(accountId, adExtensionId);
    }

    /**
     * @param accountId long
     * @return List<PhoneAdExtension>
     */
    public List<PhoneAdExtension> getPhoneAdExtensionByAccountId(long accountId) {
        return service.getPhoneAdExtensions(accountId);
    }


    /**
     * @param extension PhoneAdExtension
     * @return PhoneAdExtension
     * @throws ApiException
     */
    public PhoneAdExtension updatePhoneAdExtension(PhoneAdExtension extension) throws ApiException {
        List<PhoneAdExtension> phoneAdExtensionList = new ArrayList<>();
        phoneAdExtensionList.add(extension);
        return getPhoneAdExtensionById(extension.getAccountId(), service.updatePhoneAdExtension(phoneAdExtensionList).get(0));
    }

    /**
     * @param extension PhoneAdExtension
     * @return PhoneAdExtension
     * @throws ApiException
     */
    public PhoneAdExtension deletePhoneAdExtension(PhoneAdExtension extension) throws ApiException {
        List<Long> extensionIdList = new ArrayList<>();
        extensionIdList.add(extension.getAdExtensionId());
        return getPhoneAdExtensionById(extension.getAccountId(), service.deletePhoneAdExtensions(extension.getAccountId(), extensionIdList).get(0));
    }
}