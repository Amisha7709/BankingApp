package bankingapplication.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("*").allowedHeaders("*").exposedHeaders("*")
            .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS")
            .allowCredentials(false).maxAge(3600);
      }
    };
  }
}
