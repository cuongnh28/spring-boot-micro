package com.demo.config.resttemplate;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "app.resttemplate.enabled", havingValue = "true", matchIfMissing = false)
public class RestTemplateConfig {
}
