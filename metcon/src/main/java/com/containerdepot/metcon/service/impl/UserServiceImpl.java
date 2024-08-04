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
    public void signUp(SignUpDto signUpDto) {
        if(!signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match! Please try again!");
        }
        boolean isUsernameOrEmailTaken = this.userRepository.existsByUsernameOrEmail(signUpDto.getUsername(), signUpDto.getEmail());
        if (isUsernameOrEmailTaken) {
            throw new IllegalArgumentException("There is registered user wit this username! Please try again!");
        }
        UserEntity userEntity = this.modelMapper.map(signUpDto, UserEntity.class);
        String company = signUpDto.getCompany();
        Optional<Company> byNameEn = this.companyRepository.findByNameEn(company);
        if (byNameEn.isEmpty()){
            throw new IllegalArgumentException("No such company in database!");
        }
        userEntity.setCompany(byNameEn.get());
        userEntity.setRoles(signUpDto.getRoles().stream().map(this.roleRepository::findByRole).collect(Collectors.toSet()));
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        this.userRepository.save(userEntity);
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
        if (!this.userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with this id does not exist!");
        }
        this.userRepository.deleteById(id);
    }
}
