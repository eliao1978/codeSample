package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerLocationAdExtension;
import com.yp.pm.kp.enums.model.LocationAdExtensionStatusEnum;
import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.LocationAdExtension;
import com.yp.pm.kp.service.domain.LocationAdExtensionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class KeplerLocationAdExtensionImpl extends BaseKeplerImpl implements KeplerLocationAdExtension {

    @Inject
    private LocationAdExtensionService service;

    /**
     * @param accountId long
     * @return LocationAdExtension
     * @throws Exception
     */
    public LocationAdExtension createLocationAdExtension(long accountId) throws Exception {
        LocationAdExtension locationAdExtension = new LocationAdExtension();
        locationAdExtension.setAccountId(accountId);
        locationAdExtension.setLabel("611 N Brand Blvd, Glendale, CA 91203");
        locationAdExtension.setLatitude(34.15532);
        locationAdExtension.setLongitude(-118.25622);
        locationAdExtension.setAddressLine1("611 N Brand Blvd");
        locationAdExtension.setAddressLine2("3rd Floor");
        locationAdExtension.setCity("Glendale");
        locationAdExtension.setState("CA");
        locationAdExtension.setZip("91203");
        locationAdExtension.setCompanyName("YP.com");
        locationAdExtension.setAdExtensionStatus(LocationAdExtensionStatusEnum.ACTIVE);

        return createLocationAdExtension(locationAdExtension);
    }

    /**
     * @param extension LocationAdExtension
     * @return LocationAdExtension
     * @throws Exception
     */
    public LocationAdExtension createLocationAdExtension(LocationAdExtension extension) throws Exception {
        List<LocationAdExtension> locationAdExtensionArrayList = new ArrayList<>();
        locationAdExtensionArrayList.add(extension);
        return getLocationAdExtensionById(extension.getAccountId(), service.createLocationAdExtensions(locationAdExtensionArrayList).get(0));
    }

    /**
     * @param accountId     long
     * @param adExtensionId long
     * @return LocationAdExtension
     */
    public LocationAdExtension getLocationAdExtensionById(long accountId, long adExtensionId) {
        return service.getLocationAdExtension(accountId, adExtensionId);
    }

    /**
     * @param accountId long
     * @return List<LocationAdExtension>
     */
    public List<LocationAdExtension> getLocationAdExtensionByAccountId(long accountId) {
        return service.getLocationAdExtensions(accountId);
    }

    /**
     * @param extension LocationAdExtension
     * @return LocationAdExtension
     * @throws ApiException
     */
    public LocationAdExtension updateLocationAdExtension(LocationAdExtension extension) throws ApiException {
        List<LocationAdExtension> locationAdExtensionList = new ArrayList<>();
        locationAdExtensionList.add(extension);
        return getLocationAdExtensionById(extension.getAccountId(), service.updateLocationAdExtension(locationAdExtensionList).get(0));
    }

    /**
     * @param extension LocationAdExtension
     * @return LocationAdExtension
     * @throws ApiException
     */
    public LocationAdExtension deleteLocationAdExtension(LocationAdExtension extension) throws ApiException {
        List<Long> extensionIdList = new ArrayList<>();
        extensionIdList.add(extension.getAdExtensionId());
        return getLocationAdExtensionById(extension.getAccountId(), service.deleteLocationAdExtensions(extension.getAccountId(), extensionIdList).get(0));
    }
}
