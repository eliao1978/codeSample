package com.yp.pm.kp.api.dto;

import java.util.List;

public class ResponseDTO extends BaseDTO {

    private ResultStatusDTO resultStatus;
    private List<ResultsDTO> results;
    private ReportResultDTO reportResult;
    private UploadResultDTO uploadResult;
    private CampaignInfoResultDTO campaignInfoResult;
    private boolean authorized;
    private long totalCount;

    public ResultStatusDTO getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatusDTO resultStatus) {
        this.resultStatus = resultStatus;
    }

    public List<ResultsDTO> getResults() {
        return results;
    }

    public void setResults(List<ResultsDTO> results) {
        this.results = results;
    }

    public ReportResultDTO getReportResult() {
        return reportResult;
    }

    public void setReportResult(ReportResultDTO reportResult) {
        this.reportResult = reportResult;
    }

    public UploadResultDTO getUploadResult() {
        return uploadResult;
    }

    public void setUploadResult(UploadResultDTO uploadResult) {
        this.uploadResult = uploadResult;
    }

    public CampaignInfoResultDTO getCampaignInfoResult() {
        return campaignInfoResult;
    }

    public void setCampaignInfoResult(CampaignInfoResultDTO campaignInfo) {
        this.campaignInfoResult = campaignInfo;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long count) {
        this.totalCount = count;
    }
}