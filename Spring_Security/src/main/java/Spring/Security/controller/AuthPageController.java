package Spring.Security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPageController {

    // Trang chủ -> dùng lại index để đơn giản
    @GetMapping({"/", "/index"})
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // -> templates/login.html
    }

    // Trang 403 đã có template 403.html
    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}
