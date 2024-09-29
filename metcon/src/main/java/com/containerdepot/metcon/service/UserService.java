package com.containerdepot.metcon.service;

import com.containerdepot.metcon.service.dtos.imports.SignUpDto;
import com.containerdepot.metcon.service.dtos.imports.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.List;


public interface UserService {
    void signUp(SignUpDto signUpDto);
    List<UserDto> getAllUsers();
    PagedModel<UserDto> getAllUsersPaged(Pageable pageable, int pageNumber);
    void delete(Long id);
}
