package com.example.monitor.config;

import com.example.monitor.rest.MonitorRestController;
import com.example.monitor.rest.SecretRestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig  extends WebMvcConfigurerAdapter {
    @Bean
    public MonitorRestController monitorRestController() {
        return new MonitorRestController();
    }
    @Bean
    public SecretRestController secretRestController() {
        return new SecretRestController();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


}
