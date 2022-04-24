package com.stiches.fashionblog.service.Impl;

import com.stiches.fashionblog.dto.LoginDto;
import com.stiches.fashionblog.dto.UserDto;
import com.stiches.fashionblog.models.Role;
import com.stiches.fashionblog.models.User;
import com.stiches.fashionblog.repository.UserRepo;
import com.stiches.fashionblog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public User createUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.CUSTOMER);
        return userRepo.save(user);
    }

    @Override
    public User createAdmin(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.ADMIN);
        return userRepo.save(user);
    }

    @Override
    public User findById(Integer id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if(optionalUser.isEmpty()) throw new RuntimeException();
        return optionalUser.get();
    }


    @Override
    public User login(LoginDto loginDto) {
        User user = userRepo.findByEmailAndPassword(loginDto.getEmail(),loginDto.getPassword());
        if(user == null) throw new RuntimeException();
        return user;
    }

    @Override
    public void checkRole(Integer adminId) {
        Optional<User> optionalUser = userRepo.findById(adminId);
        if(optionalUser.isEmpty()) throw new RuntimeException();
        User user = optionalUser.get();
        if(user.getRole().equals(Role.CUSTOMER)) throw new RuntimeException();
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

}
