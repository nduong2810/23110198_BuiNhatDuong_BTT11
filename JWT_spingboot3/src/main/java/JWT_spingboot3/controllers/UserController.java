package JWT_spingboot3.controllers;

import JWT_spingboot3.entity.User;
import JWT_spingboot3.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Trả về danh sách toàn bộ user (JSON)
    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }

    // Trả về user đang đăng nhập (JSON) – giống hình “/users/me”
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser(Authentication authentication) {
        User current = (User) authentication.getPrincipal();
        return ResponseEntity.ok(current);
    }
}
