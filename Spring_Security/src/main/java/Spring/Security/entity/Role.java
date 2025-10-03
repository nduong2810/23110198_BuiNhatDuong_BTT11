package Spring.Security.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = "role_name"))
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name", nullable = false, length = 50, unique = true)
    private String roleName;

    public Role() {}

    // tiện khi tạo mới theo tên role
    public Role(String roleName) {
        this.roleName = roleName;
    }

    // tiện khi dùng new Role(null, "USER")
    public Role(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // ===== Alias để không phải sửa quá nhiều code cũ =====
    public String getName() {         // code cũ gọi role.getName()
        return roleName;
    }

    public void setName(String name) {
        this.roleName = name;
    }
}
