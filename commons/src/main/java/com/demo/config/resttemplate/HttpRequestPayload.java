package com.demo.config.resttemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpRequestPayload {
    private String httpRequestUrl;
    private String httpRequestMethod;
    private String httpRequestParams;
    private String httpRequestBody;

    public static HttpRequestPayloadBuilder builder() {
        return new HttpRequestPayloadBuilder();
    }

    public String getHttpRequestUrl() {
        return this.httpRequestUrl;
    }

    public String getHttpRequestMethod() {
        return this.httpRequestMethod;
    }

    public String getHttpRequestParams() {
        return this.httpRequestParams;
    }

    public String getHttpRequestBody() {
        return this.httpRequestBody;
    }

    public void setHttpRequestUrl(final String httpRequestUrl) {
        this.httpRequestUrl = httpRequestUrl;
    }

    public void setHttpRequestMethod(final String httpRequestMethod) {
        this.httpRequestMethod = httpRequestMethod;
    }

    public void setHttpRequestParams(final String httpRequestParams) {
        this.httpRequestParams = httpRequestParams;
    }

    public void setHttpRequestBody(final String httpRequestBody) {
        this.httpRequestBody = httpRequestBody;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof HttpRequestPayload)) {
            return false;
        } else {
            HttpRequestPayload other = (HttpRequestPayload)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$httpRequestUrl = this.getHttpRequestUrl();
                    Object other$httpRequestUrl = other.getHttpRequestUrl();
                    if (this$httpRequestUrl == null) {
                        if (other$httpRequestUrl == null) {
                            break label59;
                        }
                    } else if (this$httpRequestUrl.equals(other$httpRequestUrl)) {
                        break label59;
                    }

                    return false;
                }

                Object this$httpRequestMethod = this.getHttpRequestMethod();
                Object other$httpRequestMethod = other.getHttpRequestMethod();
                if (this$httpRequestMethod == null) {
                    if (other$httpRequestMethod != null) {
                        return false;
                    }
                } else if (!this$httpRequestMethod.equals(other$httpRequestMethod)) {
                    return false;
                }

                Object this$httpRequestParams = this.getHttpRequestParams();
                Object other$httpRequestParams = other.getHttpRequestParams();
                if (this$httpRequestParams == null) {
                    if (other$httpRequestParams != null) {
                        return false;
                    }
                } else if (!this$httpRequestParams.equals(other$httpRequestParams)) {
                    return false;
                }

                Object this$httpRequestBody = this.getHttpRequestBody();
                Object other$httpRequestBody = other.getHttpRequestBody();
                if (this$httpRequestBody == null) {
                    if (other$httpRequestBody != null) {
                        return false;
                    }
                } else if (!this$httpRequestBody.equals(other$httpRequestBody)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof HttpRequestPayload;
    }

    public int hashCode() {
        int result = 1;
        Object $httpRequestUrl = this.getHttpRequestUrl();
        result = result * 59 + ($httpRequestUrl == null ? 43 : $httpRequestUrl.hashCode());
        Object $httpRequestMethod = this.getHttpRequestMethod();
        result = result * 59 + ($httpRequestMethod == null ? 43 : $httpRequestMethod.hashCode());
        Object $httpRequestParams = this.getHttpRequestParams();
        result = result * 59 + ($httpRequestParams == null ? 43 : $httpRequestParams.hashCode());
        Object $httpRequestBody = this.getHttpRequestBody();
        result = result * 59 + ($httpRequestBody == null ? 43 : $httpRequestBody.hashCode());
        return result;
    }

    public String toString() {
        String var10000 = this.getHttpRequestUrl();
        return "HttpRequestPayload(httpRequestUrl=" + var10000 + ", httpRequestMethod=" + this.getHttpRequestMethod() + ", httpRequestParams=" + this.getHttpRequestParams() + ", httpRequestBody=" + this.getHttpRequestBody() + ")";
    }

    public HttpRequestPayload() {
    }

    public HttpRequestPayload(final String httpRequestUrl, final String httpRequestMethod, final String httpRequestParams, final String httpRequestBody) {
        this.httpRequestUrl = httpRequestUrl;
        this.httpRequestMethod = httpRequestMethod;
        this.httpRequestParams = httpRequestParams;
        this.httpRequestBody = httpRequestBody;
    }

    public static class HttpRequestPayloadBuilder {
        private String httpRequestUrl;
        private String httpRequestMethod;
        private String httpRequestParams;
        private String httpRequestBody;

        HttpRequestPayloadBuilder() {
        }

        public HttpRequestPayloadBuilder httpRequestUrl(final String httpRequestUrl) {
            this.httpRequestUrl = httpRequestUrl;
            return this;
        }

        public HttpRequestPayloadBuilder httpRequestMethod(final String httpRequestMethod) {
            this.httpRequestMethod = httpRequestMethod;
            return this;
        }

        public HttpRequestPayloadBuilder httpRequestParams(final String httpRequestParams) {
            this.httpRequestParams = httpRequestParams;
            return this;
        }

        public HttpRequestPayloadBuilder httpRequestBody(final String httpRequestBody) {
            this.httpRequestBody = httpRequestBody;
            return this;
        }

        public HttpRequestPayload build() {
            return new HttpRequestPayload(this.httpRequestUrl, this.httpRequestMethod, this.httpRequestParams, this.httpRequestBody);
        }

        public String toString() {
            return "HttpRequestPayload.HttpRequestPayloadBuilder(httpRequestUrl=" + this.httpRequestUrl + ", httpRequestMethod=" + this.httpRequestMethod + ", httpRequestParams=" + this.httpRequestParams + ", httpRequestBody=" + this.httpRequestBody + ")";
        }
    }
}

