package com.infomud.config;

import com.fasterxml.classmate.TypeResolver;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.PathSelectors;

import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.ImplicitGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import static com.google.common.collect.Lists.*;
import static springfox.documentation.builders.PathSelectors.ant;
import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.*;
import com.infomud.api.HomeController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Configuration
@EnableSwagger2
//@ComponentScan(basePackageClasses = {HomeController.class})
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Infomud API Reference")
            //.description("Live APIs to try out. Use /login api to get an authentication token")
            .license("© Infomud")
            //.licenseUrl("https://en.wiktionary.org/wiki/free_as_in_beer")
            .termsOfServiceUrl("")
            .version("1.0.0_alfa")
            .build();
    }

    @Bean
    public Docket customImplementation(){
		return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .securitySchemes(newArrayList(apiKey()))
            .securityContexts(newArrayList(securityContext()))
            .select()
                .paths(PathSelectors.any())
                //.apis(RequestHandlerSelectors.any())  // If you want to list all the apis including springboots own
                .apis(RequestHandlerSelectors.basePackage("com.infomud.api"))
                .build()
            .pathMapping("/")
            .useDefaultResponseMessages(false)
            .directModelSubstitute(LocalDate.class, String.class)
            .genericModelSubstitutes(ResponseEntity.class)
            ;
    }

    @Autowired
    private TypeResolver typeResolver;

    private ApiKey apiKey() {
        //return new ApiKey("Authorization", "api_key", "header");
        return new ApiKey("Authorization", "", "header");             // <<< === Create a Heaader (We are createing header named "Authorization" here)
    }

    //Dont want to provide any authentication to /session api
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(ant("/session")).build();
    }


    public List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("Authorization", authorizationScopes));   // Dosent Seem To matter much but the function must be present
    }


    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration("Infomud_client", "secret", "Spring", "emailSecurity", "", ApiKeyVehicle.HEADER, "", ",");
    }

    // This path will be called when swagger is loaded first time to get a token

    @Bean
    public UiConfiguration uiConfig() {
        return new UiConfiguration("session");
    }

}
