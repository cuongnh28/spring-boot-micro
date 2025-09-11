package com.demo.config.resttemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpResponsePayload {
    private String httpResponseBody;
    private Integer httpResponseStatus;

    public static HttpResponsePayloadBuilder builder() {
        return new HttpResponsePayloadBuilder();
    }

    public String getHttpResponseBody() {
        return this.httpResponseBody;
    }

    public Integer getHttpResponseStatus() {
        return this.httpResponseStatus;
    }

    public void setHttpResponseBody(final String httpResponseBody) {
        this.httpResponseBody = httpResponseBody;
    }

    public void setHttpResponseStatus(final Integer httpResponseStatus) {
        this.httpResponseStatus = httpResponseStatus;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof HttpResponsePayload)) {
            return false;
        } else {
            HttpResponsePayload other = (HttpResponsePayload)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$httpResponseStatus = this.getHttpResponseStatus();
                Object other$httpResponseStatus = other.getHttpResponseStatus();
                if (this$httpResponseStatus == null) {
                    if (other$httpResponseStatus != null) {
                        return false;
                    }
                } else if (!this$httpResponseStatus.equals(other$httpResponseStatus)) {
                    return false;
                }

                Object this$httpResponseBody = this.getHttpResponseBody();
                Object other$httpResponseBody = other.getHttpResponseBody();
                if (this$httpResponseBody == null) {
                    if (other$httpResponseBody != null) {
                        return false;
                    }
                } else if (!this$httpResponseBody.equals(other$httpResponseBody)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof HttpResponsePayload;
    }

    public int hashCode() {
        int result = 1;
        Object $httpResponseStatus = this.getHttpResponseStatus();
        result = result * 59 + ($httpResponseStatus == null ? 43 : $httpResponseStatus.hashCode());
        Object $httpResponseBody = this.getHttpResponseBody();
        result = result * 59 + ($httpResponseBody == null ? 43 : $httpResponseBody.hashCode());
        return result;
    }

    public String toString() {
        String var10000 = this.getHttpResponseBody();
        return "HttpResponsePayload(httpResponseBody=" + var10000 + ", httpResponseStatus=" + this.getHttpResponseStatus() + ")";
    }

    public HttpResponsePayload() {
    }

    public HttpResponsePayload(final String httpResponseBody, final Integer httpResponseStatus) {
        this.httpResponseBody = httpResponseBody;
        this.httpResponseStatus = httpResponseStatus;
    }

    public static class HttpResponsePayloadBuilder {
        private String httpResponseBody;
        private Integer httpResponseStatus;

        HttpResponsePayloadBuilder() {
        }

        public HttpResponsePayloadBuilder httpResponseBody(final String httpResponseBody) {
            this.httpResponseBody = httpResponseBody;
            return this;
        }

        public HttpResponsePayloadBuilder httpResponseStatus(final Integer httpResponseStatus) {
            this.httpResponseStatus = httpResponseStatus;
            return this;
        }

        public HttpResponsePayload build() {
            return new HttpResponsePayload(this.httpResponseBody, this.httpResponseStatus);
        }

        public String toString() {
            return "HttpResponsePayload.HttpResponsePayloadBuilder(httpResponseBody=" + this.httpResponseBody + ", httpResponseStatus=" + this.httpResponseStatus + ")";
        }
    }
}
