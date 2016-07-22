package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerCampaignAdExtension;
import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.CampaignAdExtension;
import com.yp.pm.kp.service.domain.CampaignAdExtensionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class KeplerCampaignAdExtensionImpl extends BaseKeplerImpl implements KeplerCampaignAdExtension {

    @Inject
    CampaignAdExtensionService service;

    public List<Long> createCampaignAdExtensions(List<CampaignAdExtension> campaignAdExtensions) throws ApiException {
        return service.createCampaignAdExtensions(campaignAdExtensions);
    }

    public List<Long> deleteCampaignAdExtensions(long accountId, long campaignId, List<Long> adExtensionIds) throws ApiException {
        return service.deleteCampaignAdExtensions(accountId, campaignId, adExtensionIds);
    }

    public void deleteCampaignAdExtensions(long accountId, List<Long> adExtensionIds) {
        service.deleteCampaignAdExtensions(accountId, adExtensionIds);
    }

    public CampaignAdExtension getCampaignAdExtension(long accountId, long campaignId, long adExtensionId) {
        return service.getCampaignAdExtension(accountId, campaignId, adExtensionId);
    }

    public List<CampaignAdExtension> getCampaignAdExtensionsByCampaignId(long accountId, long campaignId) {
        return service.getCampaignAdExtensionsByCampaignId(accountId, campaignId);
    }

    public List<CampaignAdExtension> getCampaignAdExtensionsByAdExtensionId(long accountId, long adExtensionId) {
        return service.getCampaignAdExtensionsByAdExtensionId(accountId, adExtensionId);
    }

    public List<CampaignAdExtension> getCampaignAdExtensionsByAdExtensionIds(long accountId, List<Long> adExtensionIds) {
        return service.getCampaignAdExtensionsByAdExtensionIds(accountId, adExtensionIds);
    }
}
