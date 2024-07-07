package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.data.UserRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.UserEntity;
import com.containerdepot.metcon.service.UserService;
import com.containerdepot.metcon.service.dtos.SignUpDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.companyRepository = companyRepository;
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
        userEntity.setRoles(new HashSet<>());
        this.userRepository.save(userEntity);
        return true;
    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void editUser() {

    }
}
