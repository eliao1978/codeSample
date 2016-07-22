package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerLogoAdExtension;
import com.yp.pm.kp.enums.model.LogoAdExtensionStatusEnum;
import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.LogoAdExtension;
import com.yp.pm.kp.service.domain.LogoAdExtensionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class KeplerLogoAdExtensionImpl extends BaseKeplerImpl implements KeplerLogoAdExtension {

    @Inject
    private LogoAdExtensionService service;

    /**
     * @param accountId long
     * @return LogoAdExtension
     * @throws Exception
     */
    public LogoAdExtension createLogoAdExtension(long accountId) throws Exception {
        LogoAdExtension logoAdExtension = new LogoAdExtension();
        logoAdExtension.setAccountId(accountId);
        logoAdExtension.setPath("d6ff13351e2d03594942a88e093fd91bf1aaaa55");
        logoAdExtension.setLabel("Test Logo");
        logoAdExtension.setStatus(LogoAdExtensionStatusEnum.ACTIVE);

        return createLogoAdExtension(logoAdExtension);
    }

    /**
     * @param extension LogoAdExtension
     * @return LogoAdExtension
     * @throws Exception
     */
    public LogoAdExtension createLogoAdExtension(LogoAdExtension extension) throws Exception {
        List<LogoAdExtension> logoAdExtensionList = new ArrayList<>();
        logoAdExtensionList.add(extension);
        return getLogoAdExtensionById(extension.getAccountId(), service.createLogoAdExtensions(logoAdExtensionList).get(0));
    }

    /**
     * @param accountId     long
     * @param adExtensionId long
     * @return LogoAdExtension
     */
    public LogoAdExtension getLogoAdExtensionById(long accountId, long adExtensionId) {
        return service.getLogoAdExtension(accountId, adExtensionId);
    }

    /**
     * @param accountId long
     * @return List<LogoAdExtension>
     */
    public List<LogoAdExtension> getLogoAdExtensionByAccountId(long accountId) {
        return service.getLogoAdExtensions(accountId);
    }

    /**
     * @param extension LogoAdExtension
     * @return LogoAdExtension
     * @throws ApiException
     */
    public LogoAdExtension deleteLogoAdExtension(LogoAdExtension extension) throws ApiException {
        List<Long> extensionIdList = new ArrayList<>();
        extensionIdList.add(extension.getAdExtensionId());
        return getLogoAdExtensionById(extension.getAccountId(), service.deleteLogoAdExtensions(extension.getAccountId(), extensionIdList).get(0));
    }

    /**
     * @param extension LogoAdExtension
     * @return LogoAdExtension
     * @throws ApiException
     */
    public LogoAdExtension updateLogoAdExtension(LogoAdExtension extension) throws ApiException {
        List<LogoAdExtension> logoAdExtensionList = new ArrayList<>();
        logoAdExtensionList.add(extension);
        return getLogoAdExtensionById(extension.getAccountId(), service.updateLogoAdExtensions(logoAdExtensionList).get(0));
    }
}