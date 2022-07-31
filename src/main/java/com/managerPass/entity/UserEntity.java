package com.managerPass.entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users",
       uniqueConstraints = {
             @UniqueConstraint(columnNames = "username"),
             @UniqueConstraint(columnNames = "email")
       }
)
@NamedEntityGraph(name = "userEntityGraph", attributeNodes = {@NamedAttributeNode("roles")})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<RoleEntity> roles = new HashSet<>();

    @Column(name = "isAccountActive")
    private Boolean isAccountActive;

    @Column(name = "isAccountNonBlock")
    private Boolean isAccountNonBlock;

    public UserEntity(String username, @Email String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity user = (UserEntity) o;
        return idUser != null && Objects.equals(idUser, user.idUser);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
