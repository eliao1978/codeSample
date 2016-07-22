package com.yp.pm.kp.be;

import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.PhoneAdExtension;

import java.util.List;

public interface KeplerPhoneAdExtension {

    PhoneAdExtension createPhoneAdExtension(long accountId) throws Exception;

    PhoneAdExtension createPhoneAdExtension(PhoneAdExtension extension) throws Exception;

    PhoneAdExtension getPhoneAdExtensionById(long accountId, long adExtensionId) throws Exception;

    List<PhoneAdExtension> getPhoneAdExtensionByAccountId(long accountId) throws Exception;

    PhoneAdExtension updatePhoneAdExtension(PhoneAdExtension extension) throws ApiException;

    PhoneAdExtension deletePhoneAdExtension(PhoneAdExtension extension) throws ApiException;
}