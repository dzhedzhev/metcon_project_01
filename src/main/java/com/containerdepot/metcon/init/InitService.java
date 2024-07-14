package com.containerdepot.metcon.init;

import com.containerdepot.metcon.data.*;
import com.containerdepot.metcon.model.entities.*;
import com.containerdepot.metcon.model.enums.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.*;


@Component
public class InitService implements CommandLineRunner {

    private final List<Role> roles = new ArrayList<>(List.of(
            new Role(UserRole.USER),
            new Role(UserRole.OPERATOR),
            new Role(UserRole.MODERATOR),
            new Role(UserRole.ADMIN)
            ));

    private final Company company = new Company("MET", "МЕТ", 154689941, "Varna", "Ivan Shishman 30 str.", "met-depot@metcontainer.com", "+35952 150 148");
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitService(CompanyRepository companyRepository,
                       RoleRepository roleRepository,
                       UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initMotherCompany();
        initRoles();
        initAdmin();
    }

    private void initMotherCompany() {
        if (this.companyRepository.count() > 0) {
            return;
        }
        this.companyRepository.save(company);
    }
    private void initRoles() {
        if (this.roleRepository.count() > 0) {
            return;
        }
        this.roleRepository.saveAll(roles);
    }
    private void initAdmin() {
        if (this.userRepository.count() > 0) {
            return;
        }
        Optional<Company> byId = this.companyRepository.findById(1L);
        if (byId.isEmpty()) {
            return;
        }
        UserEntity admin = new UserEntity("admin",
                passwordEncoder.encode("admin"),
                new HashSet<>(Set.of(this.roleRepository.findByRole(UserRole.ADMIN))),
                "admin",
                "admin",
                "admin@mail.com",
                byId.get());
        this.userRepository.save(admin);
    }
}
