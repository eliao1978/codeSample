package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerPniAdExtension;
import com.yp.pm.kp.enums.model.PniAdExtensionStatusEnum;
import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.PniAdExtension;
import com.yp.pm.kp.service.domain.PniAdExtensionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by am1177 on 3/4/16.
 */
@Service
public class KeplerPniAdExtensionImpl extends BaseKeplerImpl implements KeplerPniAdExtension {

    @Inject
    private PniAdExtensionService service;

    /**
     * @param accountId long
     * @return PniAdExtension
     * @throws Exception
     */
    public PniAdExtension createPniAdExtension(long accountId) throws Exception {
        PniAdExtension pniAdExtension = new PniAdExtension();
        pniAdExtension.setAccountId(accountId);
        pniAdExtension.setCountryCode("1");
        pniAdExtension.setPhoneNumber("2134471189");
        //pniAdExtension.setIsCallableOnly(true);
        pniAdExtension.setAdExtensionStatus(PniAdExtensionStatusEnum.ACTIVE);

        return createPniAdExtension(pniAdExtension);
    }

    /**
     * @param extension PniAdExtension
     * @return PniAdExtension
     * @throws Exception
     */
    public PniAdExtension createPniAdExtension(PniAdExtension extension) throws Exception {
        List<PniAdExtension> pniAdExtensionArrayList = new ArrayList<>();
        pniAdExtensionArrayList.add(extension);

        return getPniAdExtensionById(extension.getAccountId(), service.createPniAdExtensions(pniAdExtensionArrayList).get(0));
    }

    /**
     * @param accountId     long
     * @param adExtensionId long
     * @return PniAdExtension
     */
    public PniAdExtension getPniAdExtensionById(long accountId, long adExtensionId) {
        return service.getPniAdExtension(accountId, adExtensionId);
    }

    /**
     * @param accountId long
     * @return List<PniAdExtension>
     */
    public List<PniAdExtension> getPniAdExtensionByAccountId(long accountId) {
        return service.getPniAdExtensions(accountId);
    }


    /**
     * @param extension PniAdExtension
     * @return PniAdExtension
     * @throws ApiException
     */
    public PniAdExtension updatePniAdExtension(PniAdExtension extension) throws ApiException {
        List<PniAdExtension> pniAdExtensionList = new ArrayList<>();
        pniAdExtensionList.add(extension);
        return getPniAdExtensionById(extension.getAccountId(), service.updatePniAdExtension(pniAdExtensionList).get(0));
    }

    /**
     * @param extension PniAdExtension
     * @return PniAdExtension
     * @throws ApiException
     */
    public PniAdExtension deletePniAdExtension(PniAdExtension extension) throws ApiException {
        List<Long> extensionIdList = new ArrayList<>();
        extensionIdList.add(extension.getAdExtensionId());
        return getPniAdExtensionById(extension.getAccountId(), service.deletePniAdExtensions(extension.getAccountId(), extensionIdList).get(0));
    }
}