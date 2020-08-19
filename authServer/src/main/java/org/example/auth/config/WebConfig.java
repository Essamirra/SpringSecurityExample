package org.example.auth.config;

import org.example.auth.rest.LoginRestController;
import org.example.auth.rest.TokenRestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig  extends WebMvcConfigurerAdapter {
    @Bean
    public TokenRestController tokenRestController() {
        return new TokenRestController();
    }

    @Bean
    public LoginRestController loginRestController() {
        return new LoginRestController();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
