package com.containerdepot.metcon.service;

import com.containerdepot.metcon.service.dtos.imports.SignUpDto;
import com.containerdepot.metcon.service.dtos.imports.UserDto;

import java.util.List;


public interface UserService {
    boolean signUp(SignUpDto signUpDto);
    void deleteUser();
    void editUser();
    List<UserDto> getAllUsers();

    void delete(Long id);
}
