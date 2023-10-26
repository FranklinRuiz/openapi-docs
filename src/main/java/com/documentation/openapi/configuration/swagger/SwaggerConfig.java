package com.documentation.openapi.configuration.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    private final SwaggerConfigService swaggerConfigService;

    public SwaggerConfig(SwaggerConfigService swaggerConfigService) {
        this.swaggerConfigService = swaggerConfigService;
    }

    @Bean
    public List<GroupedOpenApi> groupedOpenApis() {
        return swaggerConfigService.getGroupedOpenApis();
    }
}
