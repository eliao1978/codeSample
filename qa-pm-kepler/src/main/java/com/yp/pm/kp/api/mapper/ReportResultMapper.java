package com.yp.pm.kp.api.mapper;

import com.yp.pm.kp.api.dto.ReportResultDTO;
import org.json.simple.JSONObject;

public class ReportResultMapper {

    public ReportResultDTO getReportResult(JSONObject json) {
        ReportResultDTO reportResultDTO = new ReportResultDTO();

        JSONObject result = (JSONObject) json.get("reportResult");
        reportResultDTO.setReportData((String) result.get("reportData"));

        return reportResultDTO;
    }
}
