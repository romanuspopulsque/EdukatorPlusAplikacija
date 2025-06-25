package ffos.rsimunovic.edukatorplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EdukatorPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdukatorPlusApplication.class, args);
    }
    @Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**")
                .allowedOrigins("https://edukatorplusaplikacija-3.onrender.com")
                .allowedMethods("*");
        }
    };
}

}
