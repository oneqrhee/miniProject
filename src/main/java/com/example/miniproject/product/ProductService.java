package com.example.miniproject.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRespository productRespository;

    @Transactional
    public void createProduct(ProductRequestDto productRequestDto){

    }
}
