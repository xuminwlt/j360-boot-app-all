package me.j360.disboot.miniprogram.configuration;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * @author: min_xu
 * @date: 2019/1/10 5:45 PM
 * 说明：
 */

@Profile("test")
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket webApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                //.host()
                .genericModelSubstitutes(DeferredResult.class)
                //.genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                //.paths(PathSelectors.regex("/api"))//过滤的接口
                .build()
                .apiInfo(webApiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo webApiInfo() {
        Contact contact = new Contact("徐敏", "http://j360.me", "xumin.wlt@gmail.com");
        ApiInfo apiInfo = new ApiInfo("微信小程序API接口",
                "",
                "1.0",
                "",
                contact,
                "主页",
                "",
                Lists.newArrayList()
        );
        return apiInfo;
    }


    private List<ApiKey> securitySchemes() {
        return Lists.newArrayList(
                new ApiKey("Authorization", "Authorization", "header"));
    }

    private List<SecurityContext> securityContexts() {
        return Lists.newArrayList((
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build()
        ));
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("Authorization", authorizationScopes));
    }

}
