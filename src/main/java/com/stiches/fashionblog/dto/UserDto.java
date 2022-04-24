package com.stiches.fashionblog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private String firstName;
    private String lastName;
    private String password;
    private String email;

}
