package com.demo.config.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.demo.statics.ApiType;
import com.demo.statics.IntegrationType;
import com.demo.statics.LogType;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogModel {
    private ApiType requestApiType;
    private LogType requestLogType;
    private IntegrationType requestIntegrationType;

    protected LogModel(final LogModelBuilder<?, ?> b) {
        this.requestApiType = ApiType.REST;
        this.requestApiType = b.requestApiType;
        this.requestLogType = b.requestLogType;
        this.requestIntegrationType = b.requestIntegrationType;
    }

    public static LogModelBuilder<?, ?> builder() {
        return new LogModelBuilderImpl();
    }

    public ApiType getRequestApiType() {
        return this.requestApiType;
    }

    public LogType getRequestLogType() {
        return this.requestLogType;
    }

    public IntegrationType getRequestIntegrationType() {
        return this.requestIntegrationType;
    }

    public void setRequestApiType(final ApiType requestApiType) {
        this.requestApiType = requestApiType;
    }

    public void setRequestLogType(final LogType requestLogType) {
        this.requestLogType = requestLogType;
    }

    public void setRequestIntegrationType(final IntegrationType requestIntegrationType) {
        this.requestIntegrationType = requestIntegrationType;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof LogModel)) {
            return false;
        } else {
            LogModel other = (LogModel)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$requestApiType = this.getRequestApiType();
                    Object other$requestApiType = other.getRequestApiType();
                    if (this$requestApiType == null) {
                        if (other$requestApiType == null) {
                            break label47;
                        }
                    } else if (this$requestApiType.equals(other$requestApiType)) {
                        break label47;
                    }

                    return false;
                }

                Object this$requestLogType = this.getRequestLogType();
                Object other$requestLogType = other.getRequestLogType();
                if (this$requestLogType == null) {
                    if (other$requestLogType != null) {
                        return false;
                    }
                } else if (!this$requestLogType.equals(other$requestLogType)) {
                    return false;
                }

                Object this$requestIntegrationType = this.getRequestIntegrationType();
                Object other$requestIntegrationType = other.getRequestIntegrationType();
                if (this$requestIntegrationType == null) {
                    if (other$requestIntegrationType != null) {
                        return false;
                    }
                } else if (!this$requestIntegrationType.equals(other$requestIntegrationType)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LogModel;
    }

    public int hashCode() {
        int result = 1;
        Object $requestApiType = this.getRequestApiType();
        result = result * 59 + ($requestApiType == null ? 43 : $requestApiType.hashCode());
        Object $requestLogType = this.getRequestLogType();
        result = result * 59 + ($requestLogType == null ? 43 : $requestLogType.hashCode());
        Object $requestIntegrationType = this.getRequestIntegrationType();
        result = result * 59 + ($requestIntegrationType == null ? 43 : $requestIntegrationType.hashCode());
        return result;
    }

    public String toString() {
        ApiType var10000 = this.getRequestApiType();
        return "LogModel(requestApiType=" + var10000 + ", requestLogType=" + this.getRequestLogType() + ", requestIntegrationType=" + this.getRequestIntegrationType() + ")";
    }

    public LogModel() {
        this.requestApiType = ApiType.REST;
    }

    public LogModel(final ApiType requestApiType, final LogType requestLogType, final IntegrationType requestIntegrationType) {
        this.requestApiType = ApiType.REST;
        this.requestApiType = requestApiType;
        this.requestLogType = requestLogType;
        this.requestIntegrationType = requestIntegrationType;
    }

    public abstract static class LogModelBuilder<C extends LogModel, B extends LogModelBuilder<C, B>> {
        private ApiType requestApiType;
        private LogType requestLogType;
        private IntegrationType requestIntegrationType;

        public LogModelBuilder() {
        }

        protected abstract B self();

        public abstract C build();

        public B requestApiType(final ApiType requestApiType) {
            this.requestApiType = requestApiType;
            return this.self();
        }

        public B requestLogType(final LogType requestLogType) {
            this.requestLogType = requestLogType;
            return this.self();
        }

        public B requestIntegrationType(final IntegrationType requestIntegrationType) {
            this.requestIntegrationType = requestIntegrationType;
            return this.self();
        }

        public String toString() {
            return "LogModel.LogModelBuilder(requestApiType=" + this.requestApiType + ", requestLogType=" + this.requestLogType + ", requestIntegrationType=" + this.requestIntegrationType + ")";
        }
    }

    private static final class LogModelBuilderImpl extends LogModelBuilder<LogModel, LogModelBuilderImpl> {
        private LogModelBuilderImpl() {
        }

        protected LogModelBuilderImpl self() {
            return this;
        }

        public LogModel build() {
            return new LogModel(this);
        }
    }
}
