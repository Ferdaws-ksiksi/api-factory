package com.example.apifactory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info()
                .title("Client–Contract API")
                .version("1.0.0")
                .description("REST API to manage clients and contracts.")
                .contact(new Contact().name("Ferdaws Ksiksi").email("ferdawsksiksi2015@gmail.com")));
    }
}
