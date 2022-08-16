package com.example.miniproject.entity;


import com.example.miniproject.dto.request.ProductRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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


//    @Column(nullable = false)
//    private String imgUrl;


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

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;



    @OneToMany(mappedBy = "product")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "product")
    private List<Likes> likesList;

    public void updateProduct(ProductRequestDto productRequestDto){
        this.title = productRequestDto.getTitle();
        this.size = productRequestDto.getSize();
        this.price = productRequestDto.getPrice();
        this.content = productRequestDto.getContent();
    }

}
