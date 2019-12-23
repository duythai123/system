package capstone_project.av_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.Collections;


@Configuration
@EnableJpaRepositories("capstone_project.av_service.repository")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableSwagger2
public class AppConfiguration {

    @Value("${swagger.services.basePath:}")
    private String basePath;

    /**
     * Enable Swagger Bean.
     *
     * @return Docket
     */
    @Bean
    public Docket productApi(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2)
                .protocols(Collections.singleton("http"))
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("capstone_project.av_service"))
                .paths(PathSelectors.any())
                .build()
                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                    	System.out.println("=============PATH=============");
                    	System.out.println(basePath + super.getApplicationBasePath());
                        return basePath + super.getApplicationBasePath();
                    }
                });
    }
}
