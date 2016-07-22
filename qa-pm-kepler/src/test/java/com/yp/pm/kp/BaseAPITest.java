package com.yp.pm.kp;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.other.UserEnum;
import com.yp.pm.kp.api.dto.*;
import com.yp.pm.kp.api.mapper.*;
import com.yp.util.CURLUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;

public class BaseAPITest extends BaseBeTest {

    protected final String userName = UserEnum.KP_SUPER_ADMIN.getValue();
    protected final String contentType = "Content-Type:application/json";
    protected final String deliveryMethod = "STANDARD";
    protected final String requestId = "ERU13DDP09P";


    protected ResponseDTO getResponseDTO(ResponseDTO dto) {
        return getResponseDTO(null, dto);
    }

    protected ResponseDTO getResponseDTO(KeplerDomainTypeEnum type, ResponseDTO dto) {
        logger.debug("KPL request => ".concat(dto.getRequest()));

        if (dto.getResponse() != null) {
            logger.debug("KPL response => ".concat(dto.getResponse().toJSONString()));

            if (type != null) {
                // for result status node
                if (type.equals(KeplerDomainTypeEnum.UPLOAD)) {
                    UploadResultMapper uploadResultMapper = new UploadResultMapper();
                    UploadResultDTO uploadResultDTO = uploadResultMapper.getUploadResult(dto.getResponse());
                    dto.setUploadResult(uploadResultDTO);
                }
                else if (type.equals(KeplerDomainTypeEnum.CAMPAIGN_INFO) ){
                    CampaignInfoResultMapper campaignInfoResultMapper = new CampaignInfoResultMapper();
                    CampaignInfoResultDTO campaignInfoResultDTO = campaignInfoResultMapper.getCampaignInfoResult(dto.getResponse());
                    dto.setCampaignInfoResult(campaignInfoResultDTO);
                }
                else {
                    ResultStatusMapper resultStatusMapper = new ResultStatusMapper();
                    ResultStatusDTO resultStatusDTO = resultStatusMapper.getResultStatus(dto.getResponse());
                    dto.setResultStatus(resultStatusDTO);

                    // for results node
                    if (!type.equals(KeplerDomainTypeEnum.AUTHORIZATION)) {
                        ResultsMapper resultsMapper = new ResultsMapper();
                        List<ResultsDTO> results = resultsMapper.getResults(type, (JSONArray) dto.getResponse().get("results"));
                        dto.setResults(results);

                        // for report result node
                        if (type.equals(KeplerDomainTypeEnum.REPORTING)) {
                            ReportResultMapper reportResultMapper = new ReportResultMapper();
                            ReportResultDTO reportResultDTO = reportResultMapper.getReportResult(dto.getResponse());
                            dto.setReportResult(reportResultDTO);
                        }
                    }

                    // for other nodes
                    dto.setAuthorized((dto.getResponse().get("authorized") == null) ? false : (Boolean) (dto.getResponse().get("authorized")));
                    dto.setTotalCount((Long) dto.getResponse().get("totalCount"));
                }
            } else {
                ResultStatusMapper resultStatusMapper = new ResultStatusMapper();
                ResultStatusDTO resultStatusDTO = resultStatusMapper.getResultStatus(dto.getResponse());
                dto.setResultStatus(resultStatusDTO);
            }
        } else {
            logger.debug("KPL response => null");
        }

        return dto;
    }

    protected ResponseDTO getAPIResponse(String command) throws IOException, InterruptedException, ParseException {
        ResponseDTO responseDTO = new ResponseDTO();
        String output;
        JSONObject response = null;

        responseDTO.setRequest(command);
        Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
        DataInputStream inputStream = new DataInputStream(process.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        while ((output = reader.readLine()) != null) {
            response = (JSONObject) new JSONParser().parse(output);
        }
        responseDTO.setResponse(response);

        return responseDTO;
    }

    protected ResponseDTO getAPIResponse(MethodEnum method, String endPoint, Map<String, String> headerMap, String payload) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        String endPointPath = "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/" + endPoint;

        Map<String, Object> output;
        switch (method) {
            case POST:
                output = CURLUtil.post(endPointPath, headerMap, payload);
                break;
            case PUT:
                output = CURLUtil.put(endPointPath, headerMap, payload);
                break;
            case DELETE:
                output = CURLUtil.delete(endPointPath, headerMap, payload);
                break;
            default:
                output = CURLUtil.get(endPointPath, headerMap);
        }

        responseDTO.setRequest(output.get("request").toString());
        responseDTO.setResponse((JSONObject) output.get("response"));

        return responseDTO;
    }

    protected Map<String, String> getRequestHeader() {
        return getRequestHeader(null);
    }

    protected Map<String, String> getRequestHeaderNoUser() {
        return getRequestHeader("noUser");
    }

    protected Map<String, String> getRequestHeader(String userName) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("requestid", "ERU13DDP09P");


        if (userName != null) {
            if(!userName.equalsIgnoreCase("noUser")) {
                headers.put("username", userName);
            }
        } else {
            if (!System.getProperty("env").contains("qa")) {
                headers.put("username", System.getProperty("smoke.user"));
            } else {
                headers.put("username", UserEnum.KP_SUPER_ADMIN.getValue());
            }
        }

        return headers;
    }

    protected void assertResponseError(ResponseDTO responseDTO) {
        boolean isError = false;
        String message = "";

        if (responseDTO.getUploadResult() != null && responseDTO.getUploadResult().getError() != null) {
            isError = true;

            if (responseDTO.getUploadResult().getErrorRow() != null) {
                message = responseDTO.getResultStatus().getError().getType() + " - " + responseDTO.getUploadResult().getError() + " Row - " + responseDTO.getUploadResult().getErrorRow();
            } else {
                message = responseDTO.getResultStatus().getError().getType() + " - " + responseDTO.getUploadResult().getError();
            }
        } else {
            if (responseDTO.getResultStatus() != null && responseDTO.getResultStatus().getStatus().equalsIgnoreCase("FAILURE")) {
                isError = true;
                message = responseDTO.getResultStatus().getError().getType() + " - " + responseDTO.getResultStatus().getError().getMessage();
            } else if (responseDTO.getResults() != null) {
                long errorCount = 0;
                List<ResultsDTO> resultsDTOList = responseDTO.getResults();

                for (ResultsDTO result : resultsDTOList) {
                    if (result.getError().getCode() != 0) {
                        errorCount += 1;
                        message = result.getError().getType() + " - " + result.getError().getMessage();
                    }
                }

                if (errorCount > 0) {
                    isError = true;
                }
            }
        }
        assertFalse(message, isError);
    }
}