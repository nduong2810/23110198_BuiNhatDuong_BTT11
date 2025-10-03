package JWT_spingboot3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Dùng @Nationalized để map NVARCHAR, tránh lỗi DDL với SQL Server
    @Nationalized
    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Nationalized
    @Column(length = 500)
    private String images;

    @Column(nullable = false, length = 255)
    private String password;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /* ---------------- UserDetails implements ---------------- */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Nếu chưa dùng vai trò/quyền, cho trả về danh sách rỗng
        return List.of();
    }

    @Override
    public String getUsername() {
        // Dùng email làm username
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
