package org.luvx.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class DirectRequestSender implements RequestSender {
    private static Logger logger = Logger.getLogger(DirectRequestSender.class);
    private Integer timeout;

    public String sendRequest(Request request) throws Exception {
        logger.debug("发送请求:" + request);
        if (request.getHeader("Connection") == null) {
            request.setHeader("Connection", "keep-alive");
        }

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
        String content = null;
        if ("post".equals(method)) {
            content = post(url, formParams, headers, encoding);
        } else if ("get".equals(method)) {
            content = get(url, formParams, headers, encoding);
        }
        return content;
    }


    public String get(String url, List<NameValuePair> params, Map<String, String> headers, String encoding) {
        HttpClient httpClient = new DefaultHttpClient();

        httpClient.getConnectionManager().closeExpiredConnections();
        httpClient.getConnectionManager().closeIdleConnections(60000L, TimeUnit.MILLISECONDS);

        if (this.timeout == null) {
            this.timeout = Integer.valueOf(10000);
        }
        httpClient.getParams().setIntParameter("http.socket.timeout", this.timeout.intValue());
        String body = null;
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

            if (encoding != null) {
                body = getHtmlWithEncoding(entity, encoding);
            } else {
                body = EntityUtils.toString(entity);
            }
            if (entity != null) {
                EntityUtils.consume(entity);
            }
        } catch (ParseException e) {
            logger.error("出错啦", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("出错啦", e);
        } catch (IOException e) {
            logger.error("出错啦", e);
        } catch (URISyntaxException e) {
            logger.error("出错啦", e);
        }
        return body;
    }


    public String post(String url, List<NameValuePair> params, Map<String, String> headers, String encoding) {
        HttpClient httpClient = new DefaultHttpClient();

        httpClient.getConnectionManager().closeExpiredConnections();
        httpClient.getConnectionManager().closeIdleConnections(60000L, TimeUnit.MILLISECONDS);

        if (this.timeout == null) {
            this.timeout = Integer.valueOf(10000);
        }
        httpClient.getParams().setIntParameter("http.socket.timeout", this.timeout.intValue());
        String body = null;
        try {
            HttpPost httpPost = new HttpPost(url);

            if ((headers != null) && (headers.size() != 0)) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader((String) entry.getKey(), (String) entry.getValue());
                }
            }

            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpresponse = httpClient.execute(httpPost);

            HttpEntity entity = httpresponse.getEntity();

            if (encoding != null) {
                body = getHtmlWithEncoding(entity, encoding);
            } else {
                body = EntityUtils.toString(entity);
            }
            if (entity != null) {
                EntityUtils.consume(entity);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("出错啦", e);
        } catch (ClientProtocolException e) {
            logger.error("出错啦", e);
        } catch (ParseException e) {
            logger.error("出错啦", e);
        } catch (IOException e) {
            logger.error("出错啦", e);
        }
        return body;
    }


    public Integer getTimeout() {
        return this.timeout;
    }


    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }


    private String getHtmlWithEncoding(HttpEntity entity, String charset)
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
            Reader reader = new InputStreamReader(instream, charset);
            CharArrayBuffer buffer = new CharArrayBuffer(i);
            char[] tmp = new char['Ѐ'];
            int l;
            while ((l = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
            return buffer.toString();
        } finally {
            instream.close();
        }
    }
}
