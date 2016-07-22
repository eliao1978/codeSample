package com.yp.util;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CURLUtil {
    private static Logger logger = new LogUtil().getLogger();

    /**
     *
     * @param endPoint
     * @param headers
     * @param payload
     * @return
     * @throws Exception
     */
    public static Map<String, Object> post(String endPoint, Map<String, String> headers, String payload) throws Exception {
        Map<String, Object> output = new HashMap<>();
        StringBuilder command = new StringBuilder();

        command.append("curl -X POST")
                .append(" -d '").append(payload).append("'")
                .append(getHeader(headers))
                .append(" ")
                .append(endPoint);

        output.put("request", command.toString());
        output.put("response", execute(command.toString()));

        return output;
    }

    /**
     *
     * @param endPoint
     * @param headers
     * @param payload
     * @return
     * @throws Exception
     */
    public static Map<String, Object> put(String endPoint, Map<String, String> headers, String payload) throws Exception {
        Map<String, Object> output = new HashMap<>();
        StringBuilder command = new StringBuilder();

        command.append("curl -X PUT")
                .append(" -d '").append(payload).append("'")
                .append(getHeader(headers))
                .append(" ")
                .append(endPoint);

        output.put("request", command.toString());
        output.put("response", execute(command.toString()));

        return output;
    }

    /**
     *
     * @param endPoint
     * @param headers
     * @return
     * @throws Exception
     */
    public static Map<String, Object> get(String endPoint, Map<String, String> headers) throws Exception {
        Map<String, Object> output = new HashMap<>();
        StringBuilder command = new StringBuilder();

        command.append("curl -X GET")
                .append(getHeader(headers))
                .append(" ")
                .append(endPoint);

        output.put("request", command.toString());
        output.put("response", execute(command.toString()));

        return output;
    }

    /**
     *
     * @param endPoint
     * @param headers
     * @param payload
     * @return
     * @throws Exception
     */
    public static Map<String, Object> delete(String endPoint, Map<String, String> headers, String payload) throws Exception {
        Map<String, Object> output = new HashMap<>();
        StringBuilder command = new StringBuilder();

        command.append("curl -X DELETE")
                .append(" -d '").append(payload).append("'")
                .append(getHeader(headers))
                .append(" ")
                .append(endPoint);

        output.put("request", command.toString());
        output.put("response", execute(command.toString()));

        return output;
    }

    /**
     *
     * @param headerMap
     * @return
     */
    private static StringBuilder getHeader(Map<String, String> headerMap) {
        StringBuilder headers = new StringBuilder();

        Set headerKeys = headerMap.keySet();
        for (Object item : headerKeys) {
            String key = item.toString();
            headers = headers.append(" -H ").append(key).append(":").append(headerMap.get(key));
        }

        return headers;
    }

    /**
     *
     * @param command
     * @return
     * @throws Exception
     */
    private static JSONObject execute(String command) throws Exception {
        String output;
        JSONObject response = null;

        Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
        DataInputStream inputStream = new DataInputStream(process.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while ((output = reader.readLine()) != null) {
            response = (JSONObject) new JSONParser().parse(output);
        }

        return response;
    }
}
