package cloud.angst.k8s.featuretoggle.config.swagger;

import cloud.angst.k8s.featuretoggle.config.properties.AppInfoProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(
            AppInfoProperties properties,
            ServletContext servletContext,
            @Value("${REWRITE_PATH:/}") String rewritePath) {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(properties))
                .pathProvider(
                        new RelativePathProvider(servletContext) {
                            @Override
                            public String getApplicationBasePath() {
                                return rewritePath;
                            }
                        })
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ExposedInterface.class))
                .build();
    }

    private ApiInfo apiInfo(AppInfoProperties properties) {
        return new ApiInfoBuilder()
                .title(properties.getName())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .build();
    }
}
