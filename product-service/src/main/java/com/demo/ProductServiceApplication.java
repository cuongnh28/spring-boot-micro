package com.demo;

import com.demo.logging.FeignRequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import com.demo.util.StringToDateConverter;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = {FeignRequestInterceptor.class})
@Import({StringToDateConverter.class})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableKafka

public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
