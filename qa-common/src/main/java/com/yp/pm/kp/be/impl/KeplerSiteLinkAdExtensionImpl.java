package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerSiteLinkAdExtension;
import com.yp.pm.kp.enums.model.SiteLinkAdExtensionStatusEnum;
import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.SiteLinkAdExtension;
import com.yp.pm.kp.service.domain.SiteLinkAdExtensionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class KeplerSiteLinkAdExtensionImpl extends BaseKeplerImpl implements KeplerSiteLinkAdExtension {

    @Inject
    private SiteLinkAdExtensionService service;

    /**
     * @param accountId long
     * @return SiteLinkAdExtension
     * @throws Exception
     */
    public SiteLinkAdExtension createSiteLinkAdExtension(long accountId) throws Exception {
        SiteLinkAdExtension siteLinkAdExtension = new SiteLinkAdExtension();
        siteLinkAdExtension.setAccountId(accountId);
        siteLinkAdExtension.setAdExtensionStatus(SiteLinkAdExtensionStatusEnum.ACTIVE);
        siteLinkAdExtension.setDisplayText("Contact Us");
        siteLinkAdExtension.setDestinationUrl("http://yp_" + System.currentTimeMillis() + ".com");
        return createSiteLinkAdExtension(siteLinkAdExtension);
    }

    /**
     * @param extension SiteLinkAdExtension
     * @return SiteLinkAdExtension
     * @throws Exception
     */
    public SiteLinkAdExtension createSiteLinkAdExtension(SiteLinkAdExtension extension) throws Exception {
        List<SiteLinkAdExtension> siteLinkAdExtensionArrayList = new ArrayList<>();
        siteLinkAdExtensionArrayList.add(extension);
        return getSiteLinkAdExtensionById(extension.getAccountId(), service.createSiteLinkAdExtensions(siteLinkAdExtensionArrayList).get(0));
    }

    /**
     * @param accountId     long
     * @param adExtensionId long
     * @return SiteLinkAdExtension
     */
    public SiteLinkAdExtension getSiteLinkAdExtensionById(long accountId, long adExtensionId) {
        return service.getSiteLinkAdExtension(accountId, adExtensionId);
    }

    /**
     * @param accountId long
     * @return SiteLinkAdExtension
     */
    public List<SiteLinkAdExtension> getSiteLinkAdExtensionByAccountId(long accountId) {
        return service.getSiteLinkAdExtensions(accountId);
    }

    /**
     * @param extension SiteLinkAdExtension
     * @return SiteLinkAdExtension
     * @throws ApiException
     */
    public SiteLinkAdExtension updateSiteLinkAdExtension(SiteLinkAdExtension extension) throws ApiException {
        List<SiteLinkAdExtension> siteLinkAdExtensionArrayList = new ArrayList<>();
        siteLinkAdExtensionArrayList.add(extension);
        return getSiteLinkAdExtensionById(extension.getAccountId(), service.updateSiteLinkAdExtension(siteLinkAdExtensionArrayList).get(0));
    }

    /**
     * @param extension SiteLinkAdExtension
     * @return SiteLinkAdExtension
     * @throws ApiException
     */
    public SiteLinkAdExtension deleteSiteLinkAdExtension(SiteLinkAdExtension extension) throws ApiException {
        List<Long> extensionIdList = new ArrayList<>();
        extensionIdList.add(extension.getAdExtensionId());
        return getSiteLinkAdExtensionById(extension.getAccountId(), service.deleteSiteLinkAdExtensions(extension.getAccountId(), extensionIdList).get(0));
    }
}
