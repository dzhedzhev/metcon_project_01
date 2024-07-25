package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.data.RoleRepository;
import com.containerdepot.metcon.data.UserRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.UserEntity;
import com.containerdepot.metcon.service.UserService;
import com.containerdepot.metcon.service.dtos.imports.SignUpDto;
import com.containerdepot.metcon.service.dtos.imports.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, CompanyRepository companyRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean signUp(SignUpDto signUpDto) {
        if(!signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            return false;
        }
        boolean isUsernameOrEmailTaken = this.userRepository.existsByUsernameOrEmail(signUpDto.getUsername(), signUpDto.getEmail());
        if (isUsernameOrEmailTaken) {
            return false;
        }
        UserEntity userEntity = this.modelMapper.map(signUpDto, UserEntity.class);
        String company = signUpDto.getCompany();
        Optional<Company> byNameEn = this.companyRepository.findByNameEn(company);
        if (byNameEn.isEmpty()){
            return false;
        }
        userEntity.setCompany(byNameEn.get());
        userEntity.setRoles(signUpDto.getRoles().stream().map(this.roleRepository::findByRole).collect(Collectors.toSet()));
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        this.userRepository.save(userEntity);
        return true;
    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void editUser() {

    }

    @Override
    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll().stream().map(userEntity -> {
            UserDto mapped = this.modelMapper.map(userEntity, UserDto.class);
            mapped.setCompany(userEntity.getCompany().getNameEn());
            mapped.setRoles(userEntity.getRoles().stream().map(role -> role.getRole().toString()).collect(Collectors.joining(", ")));
            return mapped;
        }).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }
}
