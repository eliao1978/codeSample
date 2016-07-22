package com.yp.pm.kp.api.mapper;

import com.yp.pm.kp.api.dto.ErrorDTO;
import com.yp.pm.kp.api.dto.ResultStatusDTO;
import com.yp.enums.error.ErrorTypeEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ResultStatusMapper {

    public ResultStatusDTO getResultStatus(JSONObject json) {
        ResultStatusDTO resultStatusDTO = new ResultStatusDTO();

        if (json != null) {
            JSONObject result = (JSONObject) json.get("resultStatus");
            resultStatusDTO.setStatus((String) result.get("status"));
            resultStatusDTO.setError(getError(result));
        } else {
            resultStatusDTO.setStatus("FAILURE");

            ErrorDTO error = new ErrorDTO();
            error.setType(ErrorTypeEnum.NO_RESPONSE);
            error.setMessage(ErrorTypeEnum.NO_RESPONSE.getValue() + " - " + ErrorTypeEnum.NO_RESPONSE.getLabel());
            resultStatusDTO.setError(error);
        }

        return resultStatusDTO;
    }


    private ErrorDTO getError(JSONObject json) {
        ErrorDTO errorDTO = new ErrorDTO();

        if (((JSONArray) json.get("errors")).size() > 0) {
            JSONObject error = (JSONObject) ((JSONArray) json.get("errors")).get(0);
            errorDTO.setMessage((String) error.get("message"));
            errorDTO.setType(ErrorTypeEnum.valueOf((String) error.get("type")));
            errorDTO.setCode(Long.valueOf(error.get("code").toString()));
        } else {
            errorDTO.setMessage(null);
            errorDTO.setType(null);

        }

        return errorDTO;
    }
}
