package com.stiches.fashionblog.service;

import com.stiches.fashionblog.dto.LoginDto;
import com.stiches.fashionblog.dto.UserDto;
import com.stiches.fashionblog.models.User;

import java.util.List;

public interface UserService {
    User createUser(UserDto userDto);

    User createAdmin(UserDto userDto);

    User findById(Integer id);

    User login(LoginDto loginDto);

    List<User> getAllUser();

    void checkRole(Integer adminId);
}
