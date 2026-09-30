package com.example.crud.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Konfigurasi OpenAPI / Swagger UI.
 * Akses Swagger UI di: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot CRUD API")
                        .description("REST API untuk manajemen produk dengan best practice Spring Boot")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Developer")
                                .email("dev@example.com")));
    }
}
