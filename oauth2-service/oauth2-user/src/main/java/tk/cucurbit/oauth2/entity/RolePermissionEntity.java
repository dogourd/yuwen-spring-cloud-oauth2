package tk.cucurbit.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "oauth2_role_permission", schema = "public")
public class RolePermissionEntity {

    @Id
    private String id;

    @Column(name = "role_id", nullable = false)
    private String roleId;

    @Column(name = "permission_id", nullable = false)
    private String permissionId;
}
