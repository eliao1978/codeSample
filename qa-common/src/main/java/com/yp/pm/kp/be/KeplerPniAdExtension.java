package com.yp.pm.kp.be;

/**
 * Created by am1177 on 3/4/16.
 */
import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.PniAdExtension;

import java.util.List;

public interface KeplerPniAdExtension {

    PniAdExtension createPniAdExtension(long accountId) throws Exception;

    PniAdExtension createPniAdExtension(PniAdExtension extension) throws Exception;

    PniAdExtension getPniAdExtensionById(long accountId, long adExtensionId) throws Exception;

    List<PniAdExtension> getPniAdExtensionByAccountId(long accountId) throws Exception;

    PniAdExtension updatePniAdExtension(PniAdExtension extension) throws ApiException;

    PniAdExtension deletePniAdExtension(PniAdExtension extension) throws ApiException;

}
