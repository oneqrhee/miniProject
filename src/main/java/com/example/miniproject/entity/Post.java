package com.example.miniproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    private int price;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;
}
