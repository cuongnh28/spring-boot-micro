package com.demo.config.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpRequestLogModel extends LogModel {
    private String httpRequestUrl;
    private String httpRequestMethod;
    private String httpRequestParams;
    private String httpRequestBody;

    protected HttpRequestLogModel(final HttpRequestLogModelBuilder<?, ?> b) {
        super(b);
        this.httpRequestUrl = b.httpRequestUrl;
        this.httpRequestMethod = b.httpRequestMethod;
        this.httpRequestParams = b.httpRequestParams;
        this.httpRequestBody = b.httpRequestBody;
    }

    public static HttpRequestLogModelBuilder<?, ?> builder() {
        return new HttpRequestLogModelBuilderImpl();
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof HttpRequestLogModel)) {
            return false;
        } else {
            HttpRequestLogModel other = (HttpRequestLogModel)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                label61: {
                    Object this$httpRequestUrl = this.getHttpRequestUrl();
                    Object other$httpRequestUrl = other.getHttpRequestUrl();
                    if (this$httpRequestUrl == null) {
                        if (other$httpRequestUrl == null) {
                            break label61;
                        }
                    } else if (this$httpRequestUrl.equals(other$httpRequestUrl)) {
                        break label61;
                    }

                    return false;
                }

                label54: {
                    Object this$httpRequestMethod = this.getHttpRequestMethod();
                    Object other$httpRequestMethod = other.getHttpRequestMethod();
                    if (this$httpRequestMethod == null) {
                        if (other$httpRequestMethod == null) {
                            break label54;
                        }
                    } else if (this$httpRequestMethod.equals(other$httpRequestMethod)) {
                        break label54;
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

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof HttpRequestLogModel;
    }

    public int hashCode() {
        int result = super.hashCode();
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

    public String toString() {
        String var10000 = this.getHttpRequestUrl();
        return "HttpRequestLogModel(httpRequestUrl=" + var10000 + ", httpRequestMethod=" + this.getHttpRequestMethod() + ", httpRequestParams=" + this.getHttpRequestParams() + ", httpRequestBody=" + this.getHttpRequestBody() + ")";
    }

    public HttpRequestLogModel() {
    }

    public HttpRequestLogModel(final String httpRequestUrl, final String httpRequestMethod, final String httpRequestParams, final String httpRequestBody) {
        this.httpRequestUrl = httpRequestUrl;
        this.httpRequestMethod = httpRequestMethod;
        this.httpRequestParams = httpRequestParams;
        this.httpRequestBody = httpRequestBody;
    }

    public abstract static class HttpRequestLogModelBuilder<C extends HttpRequestLogModel, B extends HttpRequestLogModelBuilder<C, B>> extends LogModel.LogModelBuilder<C, B> {
        private String httpRequestUrl;
        private String httpRequestMethod;
        private String httpRequestParams;
        private String httpRequestBody;

        public HttpRequestLogModelBuilder() {
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

        public String toString() {
            String var10000 = super.toString();
            return "HttpRequestLogModel.HttpRequestLogModelBuilder(super=" + var10000 + ", httpRequestUrl=" + this.httpRequestUrl + ", httpRequestMethod=" + this.httpRequestMethod + ", httpRequestParams=" + this.httpRequestParams + ", httpRequestBody=" + this.httpRequestBody + ")";
        }
    }

    private static final class HttpRequestLogModelBuilderImpl extends HttpRequestLogModelBuilder<HttpRequestLogModel, HttpRequestLogModelBuilderImpl> {
        private HttpRequestLogModelBuilderImpl() {
        }

        protected HttpRequestLogModelBuilderImpl self() {
            return this;
        }

        public HttpRequestLogModel build() {
            return new HttpRequestLogModel(this);
        }
    }
}
