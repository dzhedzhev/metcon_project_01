package com.containerdepot.metcon.service;

import com.containerdepot.metcon.service.dtos.AddUserDto;
import org.springframework.stereotype.Service;


public interface UserService {
    void addUser(AddUserDto addUserDto);
    void deleteUser();
    void editUser();
}
