package Spring.Security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // GET /login -> trả về template login.html (Thymeleaf)
        registry.addViewController("/login").setViewName("login");

        // Trang 403 tùy chọn
        registry.addViewController("/403").setViewName("403");
    }
}
