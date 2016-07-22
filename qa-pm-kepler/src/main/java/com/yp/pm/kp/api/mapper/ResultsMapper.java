package com.yp.pm.kp.api.mapper;

import com.yp.pm.kp.api.dto.ChangeObjectDTO;
import com.yp.pm.kp.api.dto.ErrorDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.*;
import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.error.ErrorTypeEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultsMapper {

    public List<ResultsDTO> getResults(KeplerDomainTypeEnum apiServiceType, JSONArray json) {
        List<ResultsDTO> resultsDTOList = new ArrayList<>();

        for (Object item : json) {
            ResultsDTO result = new ResultsDTO();
            JSONObject object = (JSONObject) item;
            result.setIndex(Long.valueOf(object.get("index").toString()));
            result.setErrors(getError((JSONArray) object.get("errors")));
            result.setObject(getObject(apiServiceType, object));
            resultsDTOList.add(result);
        }

        return resultsDTOList;
    }

    private ErrorDTO getError(JSONArray errors) {
        ErrorDTO errorDTO = new ErrorDTO();

        if (errors.size() > 0) {
            JSONObject error = (JSONObject) errors.get(0);
            errorDTO.setMessage((String) error.get("message"));
            errorDTO.setType(ErrorTypeEnum.valueOf((String) error.get("type")));
            errorDTO.setCode(Long.valueOf(error.get("code").toString()));
        } else {
            errorDTO.setMessage(null);
            errorDTO.setType(null);
        }

        return errorDTO;
    }

    private Object getObject(KeplerDomainTypeEnum type, JSONObject json) {
        Object object = new Object();

        switch (type) {
            case ACCOUNT:
                AccountDTO accountDTO = new AccountDTO();

                if (json.get("object") instanceof Long) {
                    accountDTO.setAccountId((Long) json.get("object"));
                } else {
                    JSONObject jsonObject = (JSONObject) json.get("object");
                    accountDTO.setAccountId((Long) jsonObject.get("accountId"));
                    accountDTO.setAgencyId((Long) jsonObject.get("agencyId"));
                    accountDTO.setStatus((String) jsonObject.get("accountStatus"));
                    accountDTO.setClientName((String) jsonObject.get("clientName"));
                    accountDTO.setTimeZone((String) jsonObject.get("timezone"));
                }

                object = accountDTO;
                break;
            case BUDGET:
                BudgetDTO budgetDTO = new BudgetDTO();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        budgetDTO.setId((Long) json.get("object"));
                    } else {
                        JSONObject jsonObject = (JSONObject) json.get("object");
                        budgetDTO.setId((Long) jsonObject.get("budgetId"));
                        budgetDTO.setAccountId((Long) jsonObject.get("accountId"));
                        budgetDTO.setName((String) jsonObject.get("name"));
                        budgetDTO.setStatus((String) jsonObject.get("status"));
                        budgetDTO.setAmount((Long) jsonObject.get("amount"));
                        budgetDTO.setDeliveryMethod((String) jsonObject.get("deliveryMethod"));
                        budgetDTO.setPeriodType((String) jsonObject.get("periodType"));
                    }
                }

                object = budgetDTO;
                break;
            case CAMPAIGN:
                CampaignDTO campaignDTO = new CampaignDTO();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        campaignDTO.setId((Long) json.get("object"));
                    } else {
                        JSONObject jsonObject = (JSONObject) json.get("object");
                        campaignDTO.setAccountId((Long) jsonObject.get("accountId"));
                        campaignDTO.setId((Long) jsonObject.get("campaignId"));
                        campaignDTO.setBudgetId((Long) jsonObject.get("budgetId"));
                        campaignDTO.setName((String) jsonObject.get("campaignName"));
                        campaignDTO.setType((String) jsonObject.get("campaignType"));
                        campaignDTO.setNetworkType((String) jsonObject.get("networkType"));
                        campaignDTO.setDeviceType((String) jsonObject.get("deviceType"));
                        campaignDTO.setMobileBidModifier((Double) jsonObject.get("mobileBidModifier"));
                        campaignDTO.setDesktopBidModifier((Double) jsonObject.get("desktopBidModifier"));
                        campaignDTO.setStatus((String) jsonObject.get("campaignStatus"));
                        campaignDTO.setServingStatus((String) jsonObject.get("servingStatus"));
                        campaignDTO.setStartDate((String) jsonObject.get("startDate"));
                        campaignDTO.setEndDate((String) jsonObject.get("endDate"));
                        campaignDTO.setBiddingStrategyType((String) jsonObject.get("biddingStrategyType"));
                        campaignDTO.setAdRotation((String) jsonObject.get("adRotation"));
                        campaignDTO.setDynamicURL((String) jsonObject.get("dynamicUrl"));
                        campaignDTO.setIncludeKeywordCloseVariant((String) jsonObject.get("includeKeywordCloseVariant"));
                        campaignDTO.setEffectiveStatus((String) jsonObject.get("effectiveStatus"));
                    }
                }

                object = campaignDTO;
                break;
            case AD_GROUP:
                AdGroupDTO adGroupDTO = new AdGroupDTO();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        adGroupDTO.setId((Long) json.get("object"));
                    } else {
                        JSONObject jsonObject = (JSONObject) json.get("object");
                        adGroupDTO.setId((Long) jsonObject.get("adGroupId"));
                        adGroupDTO.setAccountId((Long) jsonObject.get("accountId"));
                        adGroupDTO.setCampaignId((Long) jsonObject.get("campaignId"));
                        adGroupDTO.setName((String) jsonObject.get("adGroupName"));
                        adGroupDTO.setStatus((String) jsonObject.get("adGroupStatus"));
                        adGroupDTO.setEffectiveStatus((String) jsonObject.get("effectiveStatus"));
                        adGroupDTO.setMaxCpc((Long) ((JSONObject) jsonObject.get("maxCpc")).get("microAmount"));
                    }
                }

                object = adGroupDTO;
                break;
            case AD:
                AdDTO adDTO = new AdDTO();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        adDTO.setId((Long) json.get("object"));
                    } else {
                        JSONObject jsonObject = (JSONObject) json.get("object");
                        adDTO.setId((Long) jsonObject.get("adId"));
                        adDTO.setAccountId((Long) jsonObject.get("accountId"));
                        adDTO.setAdGroupId((Long) jsonObject.get("adGroupId"));
                        adDTO.setStatus((String) jsonObject.get("adStatus"));
                        adDTO.setType((String) jsonObject.get("adType"));
                        adDTO.setApproveStatus((String) jsonObject.get("approvalStatus"));
                        adDTO.setHeadline((String) jsonObject.get("headline"));
                        adDTO.setDescription1((String) jsonObject.get("description1"));
                        adDTO.setDescription2((String) jsonObject.get("description2"));
                        adDTO.setDisplayURL((String) jsonObject.get("displayUrl"));
                        adDTO.setDestinationURL((String) jsonObject.get("destinationUrl"));
                        JSONArray disapprovalReasons = (JSONArray) json.get("disapprovalReasons");
                        if (disapprovalReasons != null) {
                            List<String> disapprovalReasonsList = new ArrayList<>();
                            for (Object item : disapprovalReasons) {
                                disapprovalReasonsList.add(item.toString());
                            }
                            adDTO.setDisapprovalReasons(disapprovalReasonsList);
                        }
                        adDTO.setEffectiveStatus((String) jsonObject.get("effectiveStatus"));
                        adDTO.setMobilePreferred((jsonObject.get("mobilePreferred").toString().equalsIgnoreCase("true")));
                    }
                }

                object = adDTO;
                break;
            case KEYWORD:
                KeywordDTO keywordDTO = new KeywordDTO();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        keywordDTO.setId((Long) json.get("object"));
                    } else {
                        JSONObject jsonObject = (JSONObject) json.get("object");
                        keywordDTO.setId((Long) jsonObject.get("keywordId"));
                        keywordDTO.setAccountId((Long) jsonObject.get("accountId"));
                        keywordDTO.setAdGroupId((Long) jsonObject.get("adGroupId"));
                        keywordDTO.setStatus((String) jsonObject.get("keywordStatus"));
                        keywordDTO.setText((String) jsonObject.get("keywordText"));
                        keywordDTO.setMatchType((String) jsonObject.get("matchType"));
                        keywordDTO.setMaxCpc(((JSONObject) jsonObject.get("maxCpc")).get("microAmount") == null ? 0 : (Long) ((JSONObject) jsonObject.get("maxCpc")).get("microAmount"));
                        keywordDTO.setServingStatus((String) jsonObject.get("servingStatus"));
                        keywordDTO.setApprovalStatus((String) jsonObject.get("approvalStatus"));
                        keywordDTO.setEffectiveStatus((String) jsonObject.get("effectiveStatus"));
                        keywordDTO.setDestinationURL((String) jsonObject.get("destinationUrl"));
                    }
                }

                object = keywordDTO;
                break;
            case CAMPAIGN_AD_SCHEDULE:
                CampaignAdScheduleDTO campaignAdScheduleDTO = new CampaignAdScheduleDTO();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        campaignAdScheduleDTO.setId((Long) json.get("object"));
                    } else {
                        JSONObject jsonObject = (JSONObject) json.get("object");
                        campaignAdScheduleDTO.setId((Long) jsonObject.get("adScheduleId"));
                        campaignAdScheduleDTO.setAccountId((Long) jsonObject.get("accountId"));
                        campaignAdScheduleDTO.setCampaignId((Long) jsonObject.get("campaignId"));
                        campaignAdScheduleDTO.setBidModifier((Double) jsonObject.get("bidModifier"));
                        campaignAdScheduleDTO.setDayOfWeek((String) jsonObject.get("dayOfWeek"));
                        campaignAdScheduleDTO.setStartTime((String) jsonObject.get("startTime"));
                        campaignAdScheduleDTO.setEndTime((String) jsonObject.get("endTime"));
                        campaignAdScheduleDTO.setStatus((String) jsonObject.get("adScheduleStatus"));
                    }
                }

                object = campaignAdScheduleDTO;
                break;
            case CAMPAIGN_GEO_CODE:
                CampaignGeoCodeDTO campaignGeoCodeDTO = new CampaignGeoCodeDTO();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        campaignGeoCodeDTO.setId((Long) json.get("object"));
                    } else {
                        JSONObject jsonObject = (JSONObject) json.get("object");
                        campaignGeoCodeDTO.setId((Long) jsonObject.get("geocodeId"));
                        campaignGeoCodeDTO.setAccountId((Long) jsonObject.get("accountId"));
                        campaignGeoCodeDTO.setCampaignId((Long) jsonObject.get("campaignId"));
                        campaignGeoCodeDTO.setBidModifier((Double) jsonObject.get("bidModifier"));
                        campaignGeoCodeDTO.setStatus((String) jsonObject.get("geoCodeStatus"));
                        campaignGeoCodeDTO.setExcluded((jsonObject.get("excluded").toString().equalsIgnoreCase("true")));
                        campaignGeoCodeDTO.setGeoLibId((Long) jsonObject.get("geoLibId"));
                        campaignGeoCodeDTO.setLatitude((String) jsonObject.get("latitude"));
                        campaignGeoCodeDTO.setLongitude((String) jsonObject.get("longitude"));
                        campaignGeoCodeDTO.setPointName((String) jsonObject.get("pointName"));

                        if (jsonObject.get("radiusInMiles") != null) {
                            campaignGeoCodeDTO.setRadiusInMiles((Long) jsonObject.get("radiusInMiles"));
                        }
                    }
                }

                object = campaignGeoCodeDTO;
                break;
            case NEGATIVE_KEYWORD:
                NegativeKeywordDTO negativeKeywordDTO = new NegativeKeywordDTO();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        negativeKeywordDTO.setId((Long) json.get("object"));
                    } else {
                        JSONObject jsonObject = (JSONObject) json.get("object");
                        negativeKeywordDTO.setId((Long) jsonObject.get("negKeywordId"));
                        negativeKeywordDTO.setAccountId((Long) jsonObject.get("accountId"));
                        negativeKeywordDTO.setCampaignId((Long) jsonObject.get("campaignId"));
                        negativeKeywordDTO.setAdGroupId((Long) jsonObject.get("adGroupId"));
                        negativeKeywordDTO.setMatchType((String) jsonObject.get("matchType"));
                        negativeKeywordDTO.setStatus((String) jsonObject.get("status"));
                        negativeKeywordDTO.setText((String) jsonObject.get("negativeText"));
                    }
                }

                object = negativeKeywordDTO;
                break;
            case SITE_LINK_AD_EXTENSION:
                SiteLinkAdExtensionDTO siteLinkAdExtensionDTO = new SiteLinkAdExtensionDTO();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        siteLinkAdExtensionDTO.setId((Long) json.get("object"));
                    } else {
                        JSONObject jsonObject = (JSONObject) json.get("object");
                        siteLinkAdExtensionDTO.setId((Long) jsonObject.get("adExtensionId"));
                        siteLinkAdExtensionDTO.setAccountId((Long) jsonObject.get("accountId"));
                        siteLinkAdExtensionDTO.setDestinationUrl((String) jsonObject.get("destinationUrl"));
                        siteLinkAdExtensionDTO.setStatus((String) jsonObject.get("adExtensionStatus"));
                        siteLinkAdExtensionDTO.setDisplayText((String) jsonObject.get("displayText"));
                    }
                }

                object = siteLinkAdExtensionDTO;
                break;
            case LOCATION_AD_EXTENSION:
                LocationAdExtensionDTO locationAdExtensionDTO = new LocationAdExtensionDTO();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        locationAdExtensionDTO.setId((Long) json.get("object"));
                    } else {
                        JSONObject jsonObject = (JSONObject) json.get("object");
                        locationAdExtensionDTO.setId((Long) jsonObject.get("adExtensionId"));
                        locationAdExtensionDTO.setAccountId((Long) jsonObject.get("accountId"));
                        locationAdExtensionDTO.setStatus((String) jsonObject.get("adExtensionStatus"));
                        locationAdExtensionDTO.setLabel((String) jsonObject.get("label"));
                        locationAdExtensionDTO.setAddressLine1((String) jsonObject.get("addressLine1"));
                        locationAdExtensionDTO.setAddressLine2((String) jsonObject.get("addressLine2"));
                        locationAdExtensionDTO.setCity((String) jsonObject.get("city"));
                        locationAdExtensionDTO.setState((String) jsonObject.get("state"));
                        locationAdExtensionDTO.setZip((String) jsonObject.get("zip"));
                        locationAdExtensionDTO.setCompanyName((String) jsonObject.get("companyName"));
                        locationAdExtensionDTO.setLatitude((Double) jsonObject.get("latitude"));
                        locationAdExtensionDTO.setLongitude((Double) jsonObject.get("longitude"));
                    }
                }

                object = locationAdExtensionDTO;
                break;
            case PHONE_AD_EXTENSION:
                PhoneAdExtensionDTO phoneAdExtensionDTO = new PhoneAdExtensionDTO();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        phoneAdExtensionDTO.setId((Long) json.get("object"));
                    } else {
                        JSONObject jsonObject = (JSONObject) json.get("object");
                        phoneAdExtensionDTO.setId((Long) jsonObject.get("adExtensionId"));
                        phoneAdExtensionDTO.setAccountId((Long) jsonObject.get("accountId"));
                        phoneAdExtensionDTO.setStatus((String) jsonObject.get("adExtensionStatus"));
                        phoneAdExtensionDTO.setCountryCode((String) jsonObject.get("countryCode"));
                        phoneAdExtensionDTO.setPhoneNumber((String) jsonObject.get("phoneNumber"));
                        phoneAdExtensionDTO.setCallableOnly(jsonObject.get("isCallableOnly").toString().equalsIgnoreCase("true"));
                    }
                }

                object = phoneAdExtensionDTO;
                break;
            case LOGO_AD_EXTENSION:
                LogoAdExtensionDTO logoAdExtensionDTO = new LogoAdExtensionDTO();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        logoAdExtensionDTO.setId((Long) json.get("object"));
                    } else {
                        JSONObject jsonObject = (JSONObject) json.get("object");
                        logoAdExtensionDTO.setId((Long) jsonObject.get("adExtensionId"));
                        logoAdExtensionDTO.setAccountId((Long) jsonObject.get("accountId"));
                        logoAdExtensionDTO.setStatus((String) jsonObject.get("status"));
                        logoAdExtensionDTO.setLabel((String) jsonObject.get("label"));
                        logoAdExtensionDTO.setFullPath((String) jsonObject.get("fullPath"));
                        logoAdExtensionDTO.setPath((String) jsonObject.get("path"));
                    }
                }

                object = logoAdExtensionDTO;
                break;
            case SYNC:
                List<ChangeObjectDTO> changedObjectDTOList = new ArrayList<>();

                if (json.get("object") != null) {
                    if (json.get("object") instanceof Long) {
                        ChangeObjectDTO changedObjectDTO = new ChangeObjectDTO();
                        changedObjectDTO.setId((Long) json.get("object"));
                        changedObjectDTOList.add(changedObjectDTO);
                    } else {
                        JSONArray objects = (JSONArray) ((JSONObject) json.get("object")).get("changedObjects");
                        for (Object item : objects) {
                            ChangeObjectDTO changedObjectDTO = new ChangeObjectDTO();
                            changedObjectDTO.setParentId((Long) ((JSONObject) item).get("parentId"));
                            changedObjectDTO.setParentType((String) ((JSONObject) item).get("parentType"));
                            changedObjectDTO.setObjectId((Long) ((JSONObject) item).get("objectId"));
                            changedObjectDTO.setObjectType((String) ((JSONObject) item).get("objectType"));
                            changedObjectDTOList.add(changedObjectDTO);
                        }
                    }
                }

                object = changedObjectDTOList;
                break;
            case GEO_LOCATION:
                List<GeoLocationDTO> geoLocationDTOList = new ArrayList<>();
                JSONArray objects = (JSONArray) json.get("object");

                for (Object item : objects) {
                    GeoLocationDTO geoLocationDTO = new GeoLocationDTO();
                    geoLocationDTO.setGeoLibId((Long) ((JSONObject) item).get("geoLibId"));
                    geoLocationDTO.setType((String) ((JSONObject) item).get("type"));
                    geoLocationDTO.setName((String) ((JSONObject) item).get("name"));
                    geoLocationDTO.setDescription((String) ((JSONObject) item).get("description"));
                    geoLocationDTO.setLatitude((String) ((JSONObject) item).get("latitude"));
                    geoLocationDTO.setLongitude((String) ((JSONObject) item).get("longitude"));
                    geoLocationDTO.setStatus((boolean) ((JSONObject) item).get("status"));
                    geoLocationDTO.setState((String) ((JSONObject) item).get("state"));
                    geoLocationDTOList.add(geoLocationDTO);
                }

                object = geoLocationDTOList;
                break;
        }

        return object;
    }
}
