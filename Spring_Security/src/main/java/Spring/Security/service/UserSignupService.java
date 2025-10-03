package Spring.Security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Spring.Security.entity.Role;
import Spring.Security.entity.Users;
import Spring.Security.model.SignUpDto;  // nếu bạn có DTO này
import Spring.Security.repository.RoleRepository;
import Spring.Security.repository.UsersRepository;

@Service
public class UserSignupService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSignupService(UsersRepository usersRepository,
                             RoleRepository roleRepository,
                             PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Users signup(SignUpDto dto) {
        Users user = new Users();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setEnabled(dto.isEnabled());           // nếu DTO có cờ enabled
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role userRole = roleRepository.findByRoleName("USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "USER")));
        user.getRoles().add(userRole);

        return usersRepository.save(user);
    }
}
