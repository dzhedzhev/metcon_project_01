package com.containerdepot.metcon.data;

import com.containerdepot.metcon.model.entities.Role;
import com.containerdepot.metcon.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(UserRole userRole);
}
