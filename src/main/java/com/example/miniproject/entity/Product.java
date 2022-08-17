package com.example.miniproject.entity;

import com.example.miniproject.dto.request.ProductRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Builder
@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
public class Product extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String imgUrl;


    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    private int price;

    @Column
    private String content;

    @Column
    private String nickname;

    @ManyToOne
    @JoinColumn
    private Member member;

    @OneToMany(mappedBy = "product")
    private List<Comment> commentList;


    public void updateProduct(String title, int size, int price, String content, String imgUrl){
        this.title = title;
        this.size = size;
        this.price = price;
        this.content = content;
        this.imgUrl = imgUrl;
    }
}
