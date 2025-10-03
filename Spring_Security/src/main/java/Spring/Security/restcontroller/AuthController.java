package Spring.Security.restcontroller;

import Spring.Security.entity.Role;
import Spring.Security.entity.Users;
import Spring.Security.model.LoginDto;
import Spring.Security.model.SignUpDto;
import Spring.Security.repository.RoleRepository;
import Spring.Security.repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          UsersRepository usersRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto dto) {
        if (usersRepository.existsByUsername(dto.getUsername())) {
            return ResponseEntity.badRequest().body("Username already taken");
        }
        if (usersRepository.existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        Users u = new Users();
        u.setName(dto.getName());
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setEnabled(true);

        // Lấy role USER dạng managed; nếu chưa có thì tạo 1 lần
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseGet(() -> roleRepository.save(new Role("USER")));
        u.setRoles(Set.of(userRole));

        usersRepository.save(u);
        return ResponseEntity.ok("User registered");
    }

    // (tuỳ bạn) API login… dùng LoginDto
}
