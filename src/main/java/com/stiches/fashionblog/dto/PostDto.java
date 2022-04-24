package com.stiches.fashionblog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostDto {
    private String title;
    private String content;
    private String category;
}
