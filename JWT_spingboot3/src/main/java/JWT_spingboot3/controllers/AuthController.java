package JWT_spingboot3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthController {

    @GetMapping("login")
    public String loginPage() {          // /login
        return "login";                  // templates/login.html
    }

    @GetMapping("user/profile")
    public String profilePage() {        // /user/profile
        return "profile";                // templates/profile.html
    }
}
