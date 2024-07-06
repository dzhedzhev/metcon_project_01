package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.data.UserRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.User;
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
        User user = this.modelMapper.map(signUpDto, User.class);
        String company = signUpDto.getCompany();
        Optional<Company> byNameEn = this.companyRepository.findByNameEn(company);
        if (byNameEn.isEmpty()){
            return false;
        }
        user.setCompany(byNameEn.get());
        user.setRoles(new HashSet<>());
        this.userRepository.save(user);
        return true;
    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void editUser() {

    }
}
