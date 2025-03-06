package sio.app.camping_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

   @Override
   public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**")
               .allowedOrigins("http://localhost:3000") // specify allowed origins
               .allowedMethods("GET", "POST", "PUT", "DELETE") // specify allowed methods
               .allowedHeaders("Content-Type", "Authorization") // specify allowed headers
               .allowCredentials(true); // allow credentials
   }
}