package org.luvx.net;

import java.util.HashMap;
import java.util.Map;

public class Request implements Cloneable {
    private Map<String, String> properties;
    private Map<String, String> params;
    private Map<String, String> headers;
    private boolean useRawPost;
    private byte[] postData;
    private boolean sync = false;

    public String toString() {
        return "Request [properties=" + this.properties + ", params=" + this.params + ", headers=" + this.headers + "]";
    }

    public String getProperty(String property) {
        if (this.properties == null) {
            return null;
        }
        return (String) this.properties.get(property);
    }

    public String getParam(String param) {
        if (this.params == null) {
            return null;
        }
        return (String) this.params.get(param);
    }

    public String getHeader(String header) {
        if (this.headers == null) {
            return null;
        }
        return (String) this.headers.get(header);
    }

    public void setProperty(String property, String value) {
        if (this.properties == null) {
            this.properties = new HashMap();
        }
        this.properties.put(property, value);
    }

    public void setParam(String param, String value) {
        if (this.params == null) {
            this.params = new HashMap();
        }
        this.params.put(param, value);
    }

    public void setHeader(String header, String value) {
        if (this.headers == null) {
            this.headers = new HashMap();
        }
        this.headers.put(header, value);
    }


    public Request clone() {
        Request request = new Request();

        if (this.properties != null) {
            Map<String, String> newProperties = new HashMap();
            newProperties.putAll(this.properties);
            request.setProperties(newProperties);
        }

        if (this.params != null) {
            Map<String, String> newParams = new HashMap();
            newParams.putAll(this.params);
            request.setParams(newParams);
        }

        if (this.headers != null) {
            Map<String, String> newHeaders = new HashMap();
            newHeaders.putAll(this.headers);
            request.setHeaders(newHeaders);
        }
        request.setUseRawPost(this.useRawPost);
        if (this.postData != null) {
            request.setPostData((byte[]) this.postData.clone());
        }
        request.setSync(this.sync);
        return request;
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean isUseRawPost() {
        return this.useRawPost;
    }

    public void setUseRawPost(boolean useRawPost) {
        this.useRawPost = useRawPost;
    }

    public Request merge(Request dynaRequest) {
        if (getProperties() == null) {
            setProperties(new HashMap());
        }
        if (getParams() == null) {
            setParams(new HashMap());
        }
        if (getHeaders() == null) {
            setHeaders(new HashMap());
        }

        if (dynaRequest.getProperties() == null) {
            dynaRequest.setProperties(new HashMap());
        }
        if (dynaRequest.getParams() == null) {
            dynaRequest.setParams(new HashMap());
        }
        if (dynaRequest.getHeaders() == null) {
            dynaRequest.setHeaders(new HashMap());
        }

        getProperties().putAll(dynaRequest.getProperties());
        getParams().putAll(dynaRequest.getParams());
        getHeaders().putAll(dynaRequest.getHeaders());
        return this;
    }

    public byte[] getPostData() {
        return this.postData;
    }

    public void setPostData(byte[] postData) {
        this.postData = postData;
    }

    public boolean isSync() {
        return this.sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }
}