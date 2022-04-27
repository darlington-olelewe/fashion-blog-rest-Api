package com.stiches.fashionblog.service;

import com.stiches.fashionblog.dto.LoginDto;
import com.stiches.fashionblog.dto.UserDto;
import com.stiches.fashionblog.dto.UserDtoTwo;
import com.stiches.fashionblog.models.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    ResponseEntity<UserDtoTwo> createUser(UserDto userDto);

    ResponseEntity<UserDtoTwo> createAdmin(UserDto userDto);

    ResponseEntity<UserDtoTwo> findById(Integer id);

    ResponseEntity<UserDtoTwo> login(LoginDto loginDto, HttpSession session);

    ResponseEntity<List<UserDtoTwo>> getAllUser();

    void checkRole(Integer adminId);

    ResponseEntity<String> logout(HttpSession session);
}
