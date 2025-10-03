package Spring.Security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import Spring.Security.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsernameOrEmail(String username, String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
