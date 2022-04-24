package com.stiches.fashionblog.controller;

import com.stiches.fashionblog.dto.LoginDto;
import com.stiches.fashionblog.dto.UserDto;
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
    public ResponseEntity<User> createAccount(@RequestBody UserDto userDto){
        User user = userService.createAdmin(userDto);
        return new  ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PostMapping("/accounts")
    public ResponseEntity<User> CreateAccount(@RequestBody UserDto userDto){
        User user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<User>> getAccount(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/accounts/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Integer userId){
        return ResponseEntity.ok(userService.findById(userId));
    }



    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto, HttpSession session){
        User user = userService.login(loginDto);
        session.setAttribute("user",user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        User user = (User) session.getAttribute("user");
        String name = user.getFirstName()+" "+user.getLastName()+" has logged out";
        session.invalidate();
        return new ResponseEntity<>(name,HttpStatus.OK);
    }

}
