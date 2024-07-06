package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.data.UserRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.User;
import com.containerdepot.metcon.service.UserService;
import com.containerdepot.metcon.service.dtos.AddUserDto;
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
    public void addUser(AddUserDto addUserDto) {
        Optional<User> byUsername = this.userRepository.findByUsername(addUserDto.getUsername());
        if (byUsername.isPresent()) {
            return;
        }
        User user = this.modelMapper.map(addUserDto, User.class);
        String company = addUserDto.getCompany();
        Optional<Company> byNameEn = this.companyRepository.findByNameEn(company);
        if (byNameEn.isEmpty()){
            return;
        }
        user.setCompany(byNameEn.get());
        user.setRoles(new HashSet<>());
        this.userRepository.save(user);
    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void editUser() {

    }
}
