package com.example.trainproject.base.Model;

import com.example.trainproject.base.Constant.Role;
import com.example.trainproject.base.Util.Wapper.DataTransferObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User implements UserDetails , DataTransferObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username; // Required for authentication

    @Column(nullable = false)
    private String password; // Required for authentication

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // Enum for roles (USER, ADMIN, etc.)

    @OneToMany(mappedBy = "user")
    private List<Order> order;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(
            "ROLE_" + role.name()));  // it uses de prefix 'ROLE_' to validate at controller methods
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
