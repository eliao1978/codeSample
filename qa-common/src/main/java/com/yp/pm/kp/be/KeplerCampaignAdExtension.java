package com.yp.pm.kp.be;

import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.CampaignAdExtension;

import java.util.List;

public interface KeplerCampaignAdExtension {

    List<Long> createCampaignAdExtensions(List<CampaignAdExtension> campaignAdExtensions) throws ApiException;

    List<Long> deleteCampaignAdExtensions(long accountId, long campaignId, List<Long> adExtensionIds) throws ApiException;

    void deleteCampaignAdExtensions(long accountId, List<Long> adExtensionIds);

    CampaignAdExtension getCampaignAdExtension(long accountId, long campaignId, long adExtensionId);

    List<CampaignAdExtension> getCampaignAdExtensionsByCampaignId(long accountId, long campaignId);

    List<CampaignAdExtension> getCampaignAdExtensionsByAdExtensionId(long accountId, long adExtensionId);

    List<CampaignAdExtension> getCampaignAdExtensionsByAdExtensionIds(long accountId, List<Long> adExtensionIds);

}
