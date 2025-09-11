package com.demo;

import com.demo.logging.CommonRequestFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import com.demo.util.StringToDateConverter;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = {CommonRequestFeignConfig.class})
@Import({StringToDateConverter.class})
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

}
