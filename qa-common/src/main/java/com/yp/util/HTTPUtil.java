package com.yp.util;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.io.InputStream;

public class HTTPUtil extends LogUtil {

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String getResponse(String url) throws IOException {
        int ch;
        String content = "";
        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        GenericUrl genericUrl = new GenericUrl(url);
        HttpResponse response = null;

        try {
            HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(genericUrl);
            response = request.execute();
            InputStream is = response.getContent();

            while ((ch = is.read()) != -1) {
                content = content + (char) ch;
            }
        } catch (IOException e) {
            logger.error("IOException", e.getCause());
        } finally {
            if (response != null) {
                response.disconnect();
            }
        }

        return content;
    }

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public int getResponseCode(String url) throws IOException {
        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        GenericUrl genericUrl = new GenericUrl(url);
        int statusCode = 0;

        try {
            HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(genericUrl);

            for (int i = 0; i < 5; i++) {
                statusCode = request.execute().getStatusCode();
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            logger.error("IOException", e.getCause());
        } catch (InterruptedException e) {
            logger.error("InterruptedException", e.getCause());
        }

        return statusCode;
    }
}
