package com.yp.pm.kp.be;

import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.SiteLinkAdExtension;

import java.util.List;

public interface KeplerSiteLinkAdExtension {

    SiteLinkAdExtension createSiteLinkAdExtension(long accountId) throws Exception;

    SiteLinkAdExtension createSiteLinkAdExtension(SiteLinkAdExtension extension) throws Exception;

    SiteLinkAdExtension getSiteLinkAdExtensionById(long accountId, long adExtensionId) throws Exception;

    List<SiteLinkAdExtension> getSiteLinkAdExtensionByAccountId(long accountId) throws Exception;

    SiteLinkAdExtension updateSiteLinkAdExtension(SiteLinkAdExtension extension) throws ApiException;

    SiteLinkAdExtension deleteSiteLinkAdExtension(SiteLinkAdExtension extension) throws ApiException;
}