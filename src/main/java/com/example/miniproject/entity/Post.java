package com.example.miniproject.entity;

import com.example.miniproject.product.ProductRequestDto;
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
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String imgUrl;

    @Column(nullable = false)
    private String title;

//    @Column(nullable = false)
//    private String nickname;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    private int price;

    @Column
    private String content;

//    @ManyToOne
//    @JoinColumn(nullable = false)
//    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;

    public void updatePost(ProductRequestDto productRequestDto){
        this.title = productRequestDto.getTitle();
        this.size = productRequestDto.getSize();
        this.price = productRequestDto.getPrice();
        this.content = productRequestDto.getContent();
    }
}
