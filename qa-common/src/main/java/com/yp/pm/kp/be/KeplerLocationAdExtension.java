package com.yp.pm.kp.be;

import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.LocationAdExtension;

import java.util.List;

public interface KeplerLocationAdExtension {

    LocationAdExtension createLocationAdExtension(long accountId) throws Exception;

    LocationAdExtension createLocationAdExtension(LocationAdExtension extension) throws Exception;

    LocationAdExtension getLocationAdExtensionById(long accountId, long adExtensionId) throws Exception;

    List<LocationAdExtension> getLocationAdExtensionByAccountId(long accountId) throws Exception;

    LocationAdExtension updateLocationAdExtension(LocationAdExtension extension) throws ApiException;

    LocationAdExtension deleteLocationAdExtension(LocationAdExtension extension) throws ApiException;
}