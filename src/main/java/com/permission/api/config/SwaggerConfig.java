package com.permission.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI permissionApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Permission API")
                        .description("API for managing user permissions across accounts and systems")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Permission API Team")
                                .email("contact@permission-api.com")));
    }
}
