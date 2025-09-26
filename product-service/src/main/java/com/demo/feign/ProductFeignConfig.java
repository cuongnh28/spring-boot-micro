package com.demo.feign;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.QueryMapEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import java.util.Map;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * @author Vito Nguyen (<a href="https://github.com/cuongnh28">...</a>)
 */


//@Configuration // Disabled to avoid duplicate bean names; using CommonFeignConfig instead
public class ProductFeignConfig {

    // @Bean
    // public Client getClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    //     TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
    //     SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
    //             .loadTrustMaterial(null, acceptingTrustStrategy)
    //             .build();

    //     return new Client.Default(sslContext.getSocketFactory(), NoopHostnameVerifier.INSTANCE);
    // }


//    @Bean
//    Logger.Level feignLoggerLevel() {
//        return Logger.Level.BASIC;
//    }

    @Bean
    public QueryMapEncoder queryMapEncoder(ObjectMapper objectMapper) {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return object -> objectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {});
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public Feign.Builder feignBuilder(QueryMapEncoder queryMapEncoder) {
        return Feign.builder().queryMapEncoder(queryMapEncoder);
    }

}
