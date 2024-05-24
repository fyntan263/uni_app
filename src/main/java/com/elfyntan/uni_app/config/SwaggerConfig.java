package com.elfyntan.uni_app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HR Management API")
                        .version("1.0")
                        .description("API documentation for HR Management system"));
    }

    @Bean
    public GroupedOpenApi employeeApi() {
        return GroupedOpenApi.builder()
                .group("employees")
                .pathsToMatch("/api/employees/**")
                .build();
    }

    @Bean
    public GroupedOpenApi payrollApi() {
        return GroupedOpenApi.builder()
                .group("payrolls")
                .pathsToMatch("/api/payrolls/**")
                .build();
    }

    @Bean
    public GroupedOpenApi recruitmentApi() {
        return GroupedOpenApi.builder()
                .group("recruitments")
                .pathsToMatch("/api/recruitments/**")
                .build();
    }

    @Bean
    public GroupedOpenApi performanceApi() {
        return GroupedOpenApi.builder()
                .group("performances")
                .pathsToMatch("/api/performances/**")
                .build();
    }
}
