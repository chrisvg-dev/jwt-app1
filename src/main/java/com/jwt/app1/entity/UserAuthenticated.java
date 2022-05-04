package com.jwt.app1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserAuthenticated implements UserDetails {
    private String name;
    private String username;
    private String email;
    private String pwd;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserAuthenticated build(User user) {
        List<GrantedAuthority> authorities =
                user.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolName().name()))
                                .collect(Collectors.toList());

        return UserAuthenticated.builder()
                .name( user.getName() )
                .username( user.getUsername() )
                .email(user.getEmail() )
                .pwd(user.getPwd() )
                .authorities( authorities )
                .build();
    }

    @Override
    public String getPassword() {
        return this.pwd;
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
