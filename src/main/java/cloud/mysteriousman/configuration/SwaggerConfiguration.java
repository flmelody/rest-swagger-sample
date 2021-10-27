package cloud.mysteriousman.configuration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.configuration.OpenApiDocumentationConfiguration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * @author God
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(value = SwaggerProperties.class)
@ConditionalOnProperty(prefix = "swagger", name = "enable", havingValue = "true")
@Import(value = {OpenApiDocumentationConfiguration.class})
public class SwaggerConfiguration implements WebMvcConfigurer {
    private final SwaggerProperties swaggerProperties;

    public SwaggerConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:" + "/swagger-ui/index.html");
    }

    @Bean
    public UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder().filter(true).build();
    }

    /**
     * api 文档组
     *
     * @return Rest API
     */
    @Bean
    public Docket swaggerWithApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("API文档组")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .apis(RequestHandlerSelectors.withMethodAnnotation(Operation.class))
                .apis(RequestHandlerSelectors.withClassAnnotation(Validated.class))
                .apis(RequestHandlerSelectors.withClassAnnotation(Tag.class))
                .paths(PathSelectors.ant("/**/api/**"))
                .build();
    }

    /**
     * 其它文档组
     *
     * @return Rest API
     */
    @Bean
    public Docket swaggerWithOthers() {
        AntPathMatcher matcher = new AntPathMatcher();
        return new Docket(DocumentationType.OAS_30)
                .groupName("其它文档组")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .apis(RequestHandlerSelectors.withMethodAnnotation(Operation.class))
                .apis(RequestHandlerSelectors.withClassAnnotation(Validated.class))
                .apis(RequestHandlerSelectors.withClassAnnotation(Tag.class))
                .paths(input -> !matcher.match("/**/api/**", input))
                .build();
    }

    /**
     * 获取API信息
     *
     * @return API信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .version(swaggerProperties.getVersion())
                .build();
    }
}
