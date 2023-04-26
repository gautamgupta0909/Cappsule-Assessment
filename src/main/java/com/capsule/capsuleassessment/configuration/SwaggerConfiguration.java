package com.capsule.capsuleassessment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfiguration {
    public static final Contact DEFAULT_CONTACT = new Contact("Gautam Gupta", "", "gautamgupta0909@gmail.com");

    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Cappsule Assessment",
            "RestFul Api for Cappsule assessment",
            "1.0", "urn:tos",
            DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());

    public static final Set<String> DEFAULT_PRODUCES = new HashSet<>(Arrays.asList("application/json", "multipart/form-data"));
    public static final Set<String> DEFAULT_CONSUMES = new HashSet<>(Arrays.asList( "multipart/form-data"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES)
                .consumes(DEFAULT_CONSUMES);
        }

}
