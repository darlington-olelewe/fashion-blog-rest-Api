package com.stiches.fashionblog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String info;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
//    private List<Email> emails;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "employee_id")
//    private Employee employee;
