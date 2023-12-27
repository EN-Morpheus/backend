package com.imaginecup.morpheus.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Morpheus for Imagine Cup",
                description = "Morpheus API 명세서",
                version = "v1",
                contact = @Contact(
                        name = "김현아",
                        email = "kimha105@naver.com"
                )
        )
)

@Configuration
public class SwaggerConfig {
}
