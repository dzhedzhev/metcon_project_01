package com.containerdepot.metcon.model.entities;

import com.containerdepot.metcon.model.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private UserRole role;

    public Role() {
    }

    public Role(UserRole role) {
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
}
