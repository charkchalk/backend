package io.charkchalk.backend.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Charkchalk Backend API")
                        .description("Charkchalk Backend API implemented with Spring Boot RESTful service " +
                                "and documented using springdoc-openapi and OpenAPI 3.")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Charkchalk Database Documentation")
                        .url("https://dbdocs.io/YukinaMochizuki/Charkchalk"))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
