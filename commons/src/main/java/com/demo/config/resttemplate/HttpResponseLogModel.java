package com.demo.config.resttemplate;

import com.demo.config.model.LogModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpResponseLogModel extends LogModel {
    private String httpRequestUrl;
    private String httpRequestMethod;
    private String httpRequestParams;
    private String httpRequestBody;
    private Long httpResponseTime;
    private Integer httpResponseStatus;
    private String httpResponseBody;

    protected HttpResponseLogModel(final HttpResponseLogModelBuilder<?, ?> b) {
        super(b);
        this.httpRequestUrl = b.httpRequestUrl;
        this.httpRequestMethod = b.httpRequestMethod;
        this.httpRequestParams = b.httpRequestParams;
        this.httpRequestBody = b.httpRequestBody;
        this.httpResponseTime = b.httpResponseTime;
        this.httpResponseStatus = b.httpResponseStatus;
        this.httpResponseBody = b.httpResponseBody;
    }

    public static HttpResponseLogModelBuilder<?, ?> builder() {
        return new HttpResponseLogModelBuilderImpl();
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof HttpResponseLogModel)) {
            return false;
        } else {
            HttpResponseLogModel other = (HttpResponseLogModel)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                label97: {
                    Object this$httpResponseTime = this.getHttpResponseTime();
                    Object other$httpResponseTime = other.getHttpResponseTime();
                    if (this$httpResponseTime == null) {
                        if (other$httpResponseTime == null) {
                            break label97;
                        }
                    } else if (this$httpResponseTime.equals(other$httpResponseTime)) {
                        break label97;
                    }

                    return false;
                }

                Object this$httpResponseStatus = this.getHttpResponseStatus();
                Object other$httpResponseStatus = other.getHttpResponseStatus();
                if (this$httpResponseStatus == null) {
                    if (other$httpResponseStatus != null) {
                        return false;
                    }
                } else if (!this$httpResponseStatus.equals(other$httpResponseStatus)) {
                    return false;
                }

                Object this$httpRequestUrl = this.getHttpRequestUrl();
                Object other$httpRequestUrl = other.getHttpRequestUrl();
                if (this$httpRequestUrl == null) {
                    if (other$httpRequestUrl != null) {
                        return false;
                    }
                } else if (!this$httpRequestUrl.equals(other$httpRequestUrl)) {
                    return false;
                }

                label76: {
                    Object this$httpRequestMethod = this.getHttpRequestMethod();
                    Object other$httpRequestMethod = other.getHttpRequestMethod();
                    if (this$httpRequestMethod == null) {
                        if (other$httpRequestMethod == null) {
                            break label76;
                        }
                    } else if (this$httpRequestMethod.equals(other$httpRequestMethod)) {
                        break label76;
                    }

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
        return other instanceof HttpResponseLogModel;
    }

    public int hashCode() {
        int result = super.hashCode();
        Object $httpResponseTime = this.getHttpResponseTime();
        result = result * 59 + ($httpResponseTime == null ? 43 : $httpResponseTime.hashCode());
        Object $httpResponseStatus = this.getHttpResponseStatus();
        result = result * 59 + ($httpResponseStatus == null ? 43 : $httpResponseStatus.hashCode());
        Object $httpRequestUrl = this.getHttpRequestUrl();
        result = result * 59 + ($httpRequestUrl == null ? 43 : $httpRequestUrl.hashCode());
        Object $httpRequestMethod = this.getHttpRequestMethod();
        result = result * 59 + ($httpRequestMethod == null ? 43 : $httpRequestMethod.hashCode());
        Object $httpRequestParams = this.getHttpRequestParams();
        result = result * 59 + ($httpRequestParams == null ? 43 : $httpRequestParams.hashCode());
        Object $httpRequestBody = this.getHttpRequestBody();
        result = result * 59 + ($httpRequestBody == null ? 43 : $httpRequestBody.hashCode());
        Object $httpResponseBody = this.getHttpResponseBody();
        result = result * 59 + ($httpResponseBody == null ? 43 : $httpResponseBody.hashCode());
        return result;
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

    public Long getHttpResponseTime() {
        return this.httpResponseTime;
    }

    public Integer getHttpResponseStatus() {
        return this.httpResponseStatus;
    }

    public String getHttpResponseBody() {
        return this.httpResponseBody;
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

    public void setHttpResponseTime(final Long httpResponseTime) {
        this.httpResponseTime = httpResponseTime;
    }

    public void setHttpResponseStatus(final Integer httpResponseStatus) {
        this.httpResponseStatus = httpResponseStatus;
    }

    public void setHttpResponseBody(final String httpResponseBody) {
        this.httpResponseBody = httpResponseBody;
    }

    public String toString() {
        String var10000 = this.getHttpRequestUrl();
        return "HttpResponseLogModel(httpRequestUrl=" + var10000 + ", httpRequestMethod=" + this.getHttpRequestMethod() + ", httpRequestParams=" + this.getHttpRequestParams() + ", httpRequestBody=" + this.getHttpRequestBody() + ", httpResponseTime=" + this.getHttpResponseTime() + ", httpResponseStatus=" + this.getHttpResponseStatus() + ", httpResponseBody=" + this.getHttpResponseBody() + ")";
    }

    public HttpResponseLogModel() {
    }

    public HttpResponseLogModel(final String httpRequestUrl, final String httpRequestMethod, final String httpRequestParams, final String httpRequestBody, final Long httpResponseTime, final Integer httpResponseStatus, final String httpResponseBody) {
        this.httpRequestUrl = httpRequestUrl;
        this.httpRequestMethod = httpRequestMethod;
        this.httpRequestParams = httpRequestParams;
        this.httpRequestBody = httpRequestBody;
        this.httpResponseTime = httpResponseTime;
        this.httpResponseStatus = httpResponseStatus;
        this.httpResponseBody = httpResponseBody;
    }

    public abstract static class HttpResponseLogModelBuilder<C extends HttpResponseLogModel, B extends HttpResponseLogModelBuilder<C, B>> extends LogModel.LogModelBuilder<C, B> {
        private String httpRequestUrl;
        private String httpRequestMethod;
        private String httpRequestParams;
        private String httpRequestBody;
        private Long httpResponseTime;
        private Integer httpResponseStatus;
        private String httpResponseBody;

        public HttpResponseLogModelBuilder() {
        }

        protected abstract B self();

        public abstract C build();

        public B httpRequestUrl(final String httpRequestUrl) {
            this.httpRequestUrl = httpRequestUrl;
            return this.self();
        }

        public B httpRequestMethod(final String httpRequestMethod) {
            this.httpRequestMethod = httpRequestMethod;
            return this.self();
        }

        public B httpRequestParams(final String httpRequestParams) {
            this.httpRequestParams = httpRequestParams;
            return this.self();
        }

        public B httpRequestBody(final String httpRequestBody) {
            this.httpRequestBody = httpRequestBody;
            return this.self();
        }

        public B httpResponseTime(final Long httpResponseTime) {
            this.httpResponseTime = httpResponseTime;
            return this.self();
        }

        public B httpResponseStatus(final Integer httpResponseStatus) {
            this.httpResponseStatus = httpResponseStatus;
            return this.self();
        }

        public B httpResponseBody(final String httpResponseBody) {
            this.httpResponseBody = httpResponseBody;
            return this.self();
        }

        public String toString() {
            String var10000 = super.toString();
            return "HttpResponseLogModel.HttpResponseLogModelBuilder(super=" + var10000 + ", httpRequestUrl=" + this.httpRequestUrl + ", httpRequestMethod=" + this.httpRequestMethod + ", httpRequestParams=" + this.httpRequestParams + ", httpRequestBody=" + this.httpRequestBody + ", httpResponseTime=" + this.httpResponseTime + ", httpResponseStatus=" + this.httpResponseStatus + ", httpResponseBody=" + this.httpResponseBody + ")";
        }
    }

    private static final class HttpResponseLogModelBuilderImpl extends HttpResponseLogModelBuilder<HttpResponseLogModel, HttpResponseLogModelBuilderImpl> {
        private HttpResponseLogModelBuilderImpl() {
        }

        protected HttpResponseLogModelBuilderImpl self() {
            return this;
        }

        public HttpResponseLogModel build() {
            return new HttpResponseLogModel(this);
        }
    }
}

