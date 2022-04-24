package com.stiches.fashionblog.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @ManyToOne
    private Category category;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Like> likeList = new ArrayList<>();
    private int likeCount;
    private int commentCount;
}

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
//    private List<Email> emails;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "employee_id")
//    private Employee employee;
