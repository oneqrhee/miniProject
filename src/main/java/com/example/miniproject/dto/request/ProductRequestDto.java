package com.example.miniproject.dto.request;

import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String title;
    private int size;
    private int price;
    private String content;
    private String nickname;
}
