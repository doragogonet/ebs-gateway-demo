package com.ebs.web.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ebs.common.config.EbsConfig;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2インタフェース構成
 * 
 * @author ebs
 */
@Configuration
public class SwaggerConfig
{

    @Value("${swagger.enabled}")
    private boolean enabled;
    
   
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.OAS_30)
              
                .enable(enabled)
              
                .apiInfo(apiInfo())
               
                .select()
                
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
               
                //.apis(RequestHandlerSelectors.basePackage("com.ebs.project.tool.swagger"))
             
                .paths(PathSelectors.any())
                .build();
    }

 
    private ApiInfo apiInfo()
    {
        
        return new ApiInfoBuilder()
                // タイトル
                .title("タイトル：XXXXX")
                // 説明
                .description("説明：XXXXX")
                // 作者情報
                .contact(new Contact(EbsConfig.getName(), null, null))
                // バージョン
                .version("バージョン番号：" + EbsConfig.getVersion())
                .build();
    }
}
