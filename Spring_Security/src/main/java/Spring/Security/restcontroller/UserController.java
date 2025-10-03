package Spring.Security.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Spring.Security.entity.Users;
import Spring.Security.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users saved = userService.addUser(user, "USER"); // hoặc "ADMIN"
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
