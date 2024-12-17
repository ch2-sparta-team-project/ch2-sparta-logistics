package com.sparta_logistics.order.presentation.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    Components components = new Components()
        .addSecuritySchemes("X-User-Id",
            new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-User-Id"))
        .addSecuritySchemes("X-User-Name",
            new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-User-Name"))
        .addSecuritySchemes("X-User-Role",
            new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-User-Role"));
    return new OpenAPI()
        .components(components)
        .addSecurityItem(new SecurityRequirement()
            .addList("X-User-Id")
            .addList("X-User-Name")
            .addList("X-User-Role"))
        .info(apiInfo());
  }

  private Info apiInfo() {
    return new Info()
        .title("Spring Boot REST API Specifications")
        .description("product api")
        .version("1.0.0");
  }

}