package demo.stockmgr;

import demo.stockmgr.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main Application of Stock Manager Demo backend services
 * @author Teki Chan
 * @since 28 May 2019
 */
@SpringBootApplication
public class MainApp {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Application Configuration
     */
    @Autowired
    private AppConfig appConfig;

    /**
     * Main static method of this application
     * @param args  arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String allowedOrigins = appConfig.getRest().getAllowedCors();
                logger.info("allowedOrigins: " + allowedOrigins);
                registry.addMapping(AppConfig.REST_PREFIX + "/**").allowedOrigins(allowedOrigins);
            }
        };
    }
}