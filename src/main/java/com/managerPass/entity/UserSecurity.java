package com.managerPass.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class UserSecurity implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private boolean isAccountNonLocked;

    private boolean isAccountActive;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserSecurity build(UserEntity user) {
       List<GrantedAuthority> authorities = user.getRoles()
                                                .stream()
                                                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                                                .collect(Collectors.toList());

       return new UserSecurity(user.getIdUser(), user.getUsername(), user.getEmail(), user.getPassword(),
                               user.getIsAccountNonBlock(), user.getIsAccountActive(), authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAccountActive;
    }
}
