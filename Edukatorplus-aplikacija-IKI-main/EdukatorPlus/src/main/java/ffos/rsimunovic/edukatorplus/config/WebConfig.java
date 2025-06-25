package ffos.rsimunovic.edukatorplus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("*")  // za test, nije preporučeno u produkciji
      .allowedMethods("*")
      .allowedHeaders("*")
      .allowCredentials(false); // ako staviš *, mora biti false
  }
}

