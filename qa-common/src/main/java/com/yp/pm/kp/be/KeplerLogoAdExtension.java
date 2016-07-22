package com.yp.pm.kp.be;

import com.yp.pm.kp.model.domain.LogoAdExtension;

import java.util.List;

public interface KeplerLogoAdExtension {

    LogoAdExtension createLogoAdExtension(long accountId) throws Exception;

    LogoAdExtension createLogoAdExtension(LogoAdExtension extension) throws Exception;

    LogoAdExtension getLogoAdExtensionById(long accountId, long adExtensionId) throws Exception;

    List<LogoAdExtension> getLogoAdExtensionByAccountId(long accountId) throws Exception;

    LogoAdExtension deleteLogoAdExtension(LogoAdExtension extension) throws Exception;

    LogoAdExtension updateLogoAdExtension(LogoAdExtension extension) throws Exception;
}