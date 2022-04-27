package com.stiches.fashionblog.service.Impl;

import com.stiches.fashionblog.dto.LoginDto;
import com.stiches.fashionblog.dto.UserDto;
import com.stiches.fashionblog.dto.UserDtoTwo;
import com.stiches.fashionblog.exception.UserNotFoundEx;
import com.stiches.fashionblog.models.Role;
import com.stiches.fashionblog.models.User;
import com.stiches.fashionblog.repository.UserRepo;
import com.stiches.fashionblog.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper mapper;



    @Override
    public ResponseEntity<UserDtoTwo> createUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.CUSTOMER);
        user = userRepo.save(user);

        UserDtoTwo userD = mapper.map(user, UserDtoTwo.class);
        return new ResponseEntity<>(userD, HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<UserDtoTwo> createAdmin(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.ADMIN);
        user = userRepo.save(user);
        UserDtoTwo userD = mapper.map(user, UserDtoTwo.class);
        return new ResponseEntity<>(userD, HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<UserDtoTwo> findById(Integer id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if(optionalUser.isEmpty()) throw new UserNotFoundEx("No user with ID "+id+" exists");

        User user = optionalUser.get();
        UserDtoTwo userD = mapper.map(user, UserDtoTwo.class);
        return new ResponseEntity<>(userD, HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<UserDtoTwo> login(LoginDto loginDto, HttpSession session) {
        User user = userRepo.findByEmailAndPassword(loginDto.getEmail(),loginDto.getPassword());
        if(user == null) throw new UserNotFoundEx("Incorrect Username or Password");
        UserDtoTwo userD = mapper.map(user, UserDtoTwo.class);
        session.setAttribute("user",userD);
        return new ResponseEntity<>(userD, HttpStatus.CREATED);
    }

    @Override
    public void checkRole(Integer adminId) {
        Optional<User> optionalUser = userRepo.findById(adminId);
        if(optionalUser.isEmpty()) throw new RuntimeException();
        User user = optionalUser.get();
        if(user.getRole().equals(Role.CUSTOMER)) throw new RuntimeException();
    }

    @Override
    public ResponseEntity<String> logout(HttpSession session) {
        UserDtoTwo user = (UserDtoTwo) session.getAttribute("user");
        String info = user.getFirstName()+" "+user.getLastName()+", successfully logout";
        return ResponseEntity.ok(info);
    }

    @Override
    public ResponseEntity<List<UserDtoTwo>> getAllUser() {

        List<UserDtoTwo> all = userRepo.findAll()
                .stream()
                .map(p -> mapper.map(p,UserDtoTwo.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(all);
    }

}
