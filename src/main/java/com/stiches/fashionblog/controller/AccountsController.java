package com.stiches.fashionblog.controller;

import com.stiches.fashionblog.dto.LoginDto;
import com.stiches.fashionblog.dto.UserDto;
import com.stiches.fashionblog.dto.UserDtoTwo;
import com.stiches.fashionblog.models.User;
import com.stiches.fashionblog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@AllArgsConstructor
public class AccountsController {
    private final UserService userService;

    @PostMapping("admin/accounts")
    public ResponseEntity<UserDtoTwo> createAccount(@RequestBody UserDto userDto){
        return userService.createAdmin(userDto);
    }

    @PostMapping("/accounts")
    public ResponseEntity<UserDtoTwo> CreateAccount(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<UserDtoTwo>> getAccount(){
        return userService.getAllUser();
    }

    @GetMapping("/accounts/{userId}")
    public ResponseEntity<UserDtoTwo> getUser(@PathVariable Integer userId){
        return userService.findById(userId);
    }


    @PostMapping("/login")
    public ResponseEntity<UserDtoTwo> login(@RequestBody LoginDto loginDto, HttpSession session){
        return userService.login(loginDto,session);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        return userService.logout(session);
    }

}
