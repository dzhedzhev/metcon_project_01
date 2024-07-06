package com.containerdepot.metcon.service;

import com.containerdepot.metcon.service.dtos.SignUpDto;


public interface UserService {
    boolean signUp(SignUpDto signUpDto);
    void deleteUser();
    void editUser();
}
