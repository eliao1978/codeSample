package com.yp.pm.kp.api.dto;

public class UploadResultDTO {

    private Long campaignNegativeCount;
    private Long adGroupCount;
    private Long campaignCount;
    private Long budgetCount;
    private Long adCount;
    private Long locationCount;
    private Long adScheduleCount;
    private Long keywordCount;
    private String error;
    private Long errorRow;

    public Long getCampaignNegativeCount() {
        return campaignNegativeCount;
    }

    public void setCampaignNegativeCount(Long campaignNegativeCount) {
        this.campaignNegativeCount = campaignNegativeCount;
    }

    public Long getAdGroupCount() {
        return adGroupCount;
    }

    public void setAdGroupCount(Long adGroupCount) {
        this.adGroupCount = adGroupCount;
    }

    public Long getCampaignCount() {
        return campaignCount;
    }

    public void setCampaignCount(Long campaignCount) {
        this.campaignCount = campaignCount;
    }

    public Long getBudgetCount() {
        return budgetCount;
    }

    public void setBudgetCount(Long budgetCount) {
        this.budgetCount = budgetCount;
    }

    public Long getAdCount() {
        return adCount;
    }

    public void setAdCount(Long adCount) {
        this.adCount = adCount;
    }

    public Long getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(Long locationCount) {
        this.locationCount = locationCount;
    }

    public Long getAdScheduleCount() {
        return adScheduleCount;
    }

    public void setAdScheduleCount(Long adScheduleCount) {
        this.adScheduleCount = adScheduleCount;
    }

    public Long getKeywordCount() {
        return keywordCount;
    }

    public void setKeywordCount(Long adScheduleCount) {
        this.keywordCount = keywordCount;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getErrorRow() {
        return errorRow;
    }

    public void setErrorRow(Long errorRow) {
        this.errorRow = errorRow;
    }
}