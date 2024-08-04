package com.containerdepot.metcon.service;

import com.containerdepot.metcon.service.dtos.imports.SignUpDto;
import com.containerdepot.metcon.service.dtos.imports.UserDto;

import java.util.List;


public interface UserService {
    void signUp(SignUpDto signUpDto);
    List<UserDto> getAllUsers();

    void delete(Long id);
}
