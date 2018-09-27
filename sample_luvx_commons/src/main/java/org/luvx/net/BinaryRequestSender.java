package org.luvx.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class BinaryRequestSender {
    private static Logger logger = Logger.getLogger(BinaryRequestSender.class);
    private Integer timeout;

    public byte[] sendRequest(Request request) throws Exception {
        logger.debug("发送请求:" + request);
        request.setHeader("Connection", "close");


        String method = request.getProperty("method");
        if (method == null) {
            method = "get";
        }
        method = method.toLowerCase();
        String url = request.getProperty("url");
        String encoding = request.getProperty("encoding");
        Map<String, String> headers = request.getHeaders();
        Map<String, String> params = request.getParams();

        List<NameValuePair> formParams = new ArrayList();
        if ((params != null) && (params.size() != 0)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formParams.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
            }
        }
        return get(url, formParams, headers, encoding);
    }


    public byte[] get(String url, List<NameValuePair> params, Map<String, String> headers, String encoding) {
        HttpClient httpClient = new DefaultHttpClient();
        if (this.timeout == null) {
            this.timeout = Integer.valueOf(30000);
        }
        httpClient.getParams().setIntParameter("http.socket.timeout", this.timeout.intValue());
        try {
            HttpGet httpGet = new HttpGet(url);

            if ((headers != null) && (headers.size() != 0)) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader((String) entry.getKey(), (String) entry.getValue());
                }
            }

            if (httpGet.getURI().toString().indexOf("?") == -1) {
                String str = EntityUtils.toString(new UrlEncodedFormEntity(params, encoding));
                httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + str));
            }


            HttpResponse httpresponse = httpClient.execute(httpGet);

            HttpEntity entity = httpresponse.getEntity();

            return getData(entity);
        } catch (ParseException e) {
            logger.error("出错啦", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("出错啦", e);
        } catch (IOException e) {
            logger.error("出错啦", e);
        } catch (URISyntaxException e) {
            logger.error("出错啦", e);
        }
        return null;
    }


    public Integer getTimeout() {
        return this.timeout;
    }


    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }


    private byte[] getData(HttpEntity entity)
            throws IOException {
        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        InputStream instream = entity.getContent();
        if (instream == null) {
            return null;
        }
        try {
            if (entity.getContentLength() > 2147483647L) {
                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            }
            int i = (int) entity.getContentLength();
            if (i < 0) {
                i = 4096;
            }

            ByteArrayBuffer buffer = new ByteArrayBuffer(i);

            int l;
            while ((l = instream.read()) != -1) {
                buffer.append(l);
            }
            return buffer.toByteArray();
        } finally {
            instream.close();
        }
    }
}
