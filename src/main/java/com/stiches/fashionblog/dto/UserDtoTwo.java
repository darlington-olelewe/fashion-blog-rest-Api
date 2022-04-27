package com.stiches.fashionblog.dto;
import com.stiches.fashionblog.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoTwo {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
