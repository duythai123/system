package capstone_project.av_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsUtils;

import capstone_project.av_service.constant.SecurityConstants;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public WebSecurityConfig() {
    }

    @Override
    /**
     * disable csrf method
     * by pass authentication for swagger url
     */
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(SecurityConstants.SWAGGER_UI_URL,
                        "/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources", "/swagger-resources/configuration/security", "/webjars/**").permitAll();
    }

}
