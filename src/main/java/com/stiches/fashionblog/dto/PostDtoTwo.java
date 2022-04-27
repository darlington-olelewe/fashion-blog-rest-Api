package com.stiches.fashionblog.dto;

import com.stiches.fashionblog.models.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.SecondaryTable;

@Getter
@Setter
public class PostDtoTwo {

    private Integer id;
    private String title;
    private String content;
    private Category category;
    private Integer likeCount;

}
