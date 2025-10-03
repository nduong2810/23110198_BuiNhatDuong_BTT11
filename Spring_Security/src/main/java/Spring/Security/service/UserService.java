package Spring.Security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Spring.Security.entity.Role;
import Spring.Security.entity.Users;
import Spring.Security.repository.RoleRepository;
import Spring.Security.repository.UsersRepository;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /** Dùng khi muốn tạo user với role mặc định USER */
    @Transactional
    public Users createUserWithDefaultRole(Users user) {
        return addUser(user, "USER");
    }

    /** === Method cần thiết cho UserController ===
     *  Tạo user và gán role theo tên (tự tạo role nếu chưa có) */
    @Transactional
    public Users addUser(Users user, String roleName) {
        // encode password nếu đang là plain text
        String pwd = user.getPassword();
        if (pwd != null && !pwd.matches("^\\$2[aby]\\$.*")) {
            user.setPassword(passwordEncoder.encode(pwd));
        }

        // bật enabled nếu cần
        user.setEnabled(true);

        // lấy/ tạo role theo tên
        Role role = roleRepository.findByRoleName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(null, roleName)));

        // gán role
        user.getRoles().add(role);

        // lưu user
        return usersRepository.save(user);
    }
}
