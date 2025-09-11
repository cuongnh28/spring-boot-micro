package com.demo.config.resttemplate;

import com.demo.config.model.HttpRequestLogModel;
import com.demo.statics.ApiType;
import com.demo.statics.IntegrationType;
import com.demo.statics.LogType;
import com.demo.util.LogUtils;
import com.demo.util.RequestUtils;
import io.undertow.util.BadRequestException;
import net.logstash.logback.argument.StructuredArguments;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        setRequestId(request);

        HttpRequestPayload requestPayload = this.traceRequest(request, body);
        try {
            HttpRequestLogModel requestLogModel = ((HttpRequestLogModel.HttpRequestLogModelBuilder) ((HttpRequestLogModel.HttpRequestLogModelBuilder) ((HttpRequestLogModel.HttpRequestLogModelBuilder) HttpRequestLogModel.builder().requestApiType(ApiType.REST)).requestLogType(LogType.INTEGRATION)).requestIntegrationType(IntegrationType.REQUEST)).httpRequestUrl(requestPayload.getHttpRequestUrl()).httpRequestMethod(requestPayload.getHttpRequestMethod()).httpRequestParams(requestPayload.getHttpRequestParams()).httpRequestBody(requestPayload.getHttpRequestBody()).build();
            LogUtils.info("", StructuredArguments.fields(requestLogModel));
        } catch (Exception var11) {
            LogUtils.printError("HTTP request logging error: {}", var11);
        }

        long start = System.currentTimeMillis();
        ClientHttpResponse response = execution.execute(request, body);

        try {
            HttpResponsePayload responsePayload = this.traceResponse(response);
            HttpResponseLogModel responseLogModel = ((HttpResponseLogModel.HttpResponseLogModelBuilder)((HttpResponseLogModel.HttpResponseLogModelBuilder)((HttpResponseLogModel.HttpResponseLogModelBuilder)HttpResponseLogModel.builder().requestApiType(ApiType.REST)).requestLogType(LogType.INTEGRATION)).requestIntegrationType(IntegrationType.RESPONSE)).httpRequestUrl(requestPayload.getHttpRequestUrl()).httpRequestMethod(requestPayload.getHttpRequestMethod()).httpRequestParams(requestPayload.getHttpRequestParams()).httpRequestBody(requestPayload.getHttpRequestBody()).httpResponseTime(System.currentTimeMillis() - start).httpResponseStatus(responsePayload.getHttpResponseStatus()).httpResponseBody(responsePayload.getHttpResponseBody()).build();
            if (response.getStatusCode().is2xxSuccessful()) {
                LogUtils.info("", StructuredArguments.fields(responseLogModel));
            } else {
                LogUtils.printError("", StructuredArguments.fields(responseLogModel));
            }
        } catch (Exception var10) {
            LogUtils.printError("HTTP response logging error: {}", var10);
        }

        return response;
    }

    private void setRequestId(HttpRequest request) {
        if (StringUtils.isEmpty(RequestUtils.currentRequestId())) {
            LogUtils.printError(new BadRequestException("SEND REST API WITHOUT REQUEST ID"));
        }
        else {
            HttpHeaders headers = request.getHeaders();
            headers.add(RequestUtils.HEADER_REQUEST_ID, RequestUtils.currentRequestId());
        }
    }

    private HttpRequestPayload traceRequest(HttpRequest req, byte[] reqBody) {
        HttpRequestPayload requestPayload = new HttpRequestPayload();
        requestPayload.setHttpRequestUrl(req.getURI().toString());
        requestPayload.setHttpRequestMethod(req.getMethod().name());
        if (Objects.nonNull(req.getURI().getQuery())) {
            requestPayload.setHttpRequestParams(req.getURI().getQuery());
        }

        if (reqBody.length > 0) {
            requestPayload.setHttpRequestBody(new String(reqBody, StandardCharsets.UTF_8));
        }

        return requestPayload;
    }

    private HttpResponsePayload traceResponse(ClientHttpResponse response) throws IOException {
        InputStreamReader isr = new InputStreamReader(response.getBody(), StandardCharsets.UTF_8);
        String body = (new BufferedReader(isr)).lines().collect(Collectors.joining("\n"));
        return HttpResponsePayload.builder().httpResponseBody(body).httpResponseStatus(response.getStatusCode().value()).build();
    }

}
