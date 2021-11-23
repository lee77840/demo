package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfigure {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket api() {
        //Configuring settings for Swagger API Documentation
    	return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .protocols(Set.of("https", "http"))
                .apiInfo(metadata())
                .select()
                .apis(RequestHandlerSelectors.any())
                .build();

    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("Account Service API's")
                .description("")
                .version("1.0.0")
                .build();
    }

}
