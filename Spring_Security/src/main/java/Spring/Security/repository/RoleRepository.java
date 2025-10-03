package Spring.Security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import Spring.Security.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // ĐÚNG theo field trong entity
    Optional<Role> findByRoleName(String roleName);

    // Alias để code cũ còn gọi findByName vẫn chạy được
    default Optional<Role> findByName(String name) {
        return findByRoleName(name);
    }
}
