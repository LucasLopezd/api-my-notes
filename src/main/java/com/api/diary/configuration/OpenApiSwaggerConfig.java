package com.api.diary.configuration;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "My Personal Notes",
        description = "API Document",
        version = "1.0",
        contact = @Contact(
                name = "Lucas Ariel López Delgado",
                email = "lucaslopezd@hotmail.com",
                url = "https://github.com/LucasLopezd"
        ),
        license = @License(
                name = "MIT Licence",
                url = "https://opensource.org/licenses/mit-license.php"
        )
))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiSwaggerConfig {
   
}
