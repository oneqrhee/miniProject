package com.example.miniproject.service;

import com.example.miniproject.dto.request.ProductRequestDto;
import com.example.miniproject.dto.response.CommentResponseDto;
import com.example.miniproject.dto.response.ProductResponseDto;
import com.example.miniproject.dto.response.ProductsResponseDto;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Product;
import com.example.miniproject.repository.LikesRepository;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.repository.ProductRepository;
import com.example.miniproject.s3Service.S3Uploader;
import com.example.miniproject.securitytest.config.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final S3Uploader s3Uploader;
    private final LikesRepository likesRepository;

    private final MemberRepository memberRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Transactional
    public ResponseEntity<String> createProduct(MultipartFile multipartFile, ProductRequestDto productRequestDto,
                                                Member member) throws IOException {
        String imgUrl = s3Uploader.upload(multipartFile, "upload");



        Product product = Product.builder()
                .title(productRequestDto.getTitle())
                .imgUrl(imgUrl)
                .size(productRequestDto.getSize())
                .price(productRequestDto.getPrice())
                .content(productRequestDto.getContent())
                .nickname(member.getNickname())
                .member(member)
                .build();
        productRepository.save(product);
        return new ResponseEntity<>("글이 등록되었습니다.", HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> readAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductsResponseDto> productList = new ArrayList<>();
        for (Product product : products) {
            productList.add(ProductsResponseDto.builder()
                    .title(product.getTitle())
                    .size(product.getSize())
                    .nickname(product.getNickname())
                    .imgUrl(product.getImgUrl())
                    .likesCnt(likesRepository.findAllByProduct(product).size())
                    .createdAt(product.getCreatedAt())
                    .modifiedAt(product.getModifiedAt())
                    .build());
        }
        return productList;
    }

    @Transactional(readOnly = true)
    public ProductResponseDto readProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        return ProductResponseDto.builder()
                .title(product.getTitle())
                .size(product.getSize())
                .nickname(product.getNickname())
                .price(product.getPrice())
                .content(product.getContent())
                .imgUrl(product.getImgUrl())
                .likesCnt(likesRepository.findAllByProduct(product).size())
                .commentList(product.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList()))
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .build();
    }

    @Transactional
    public ResponseEntity<String> updateProduct(Long productId, ProductRequestDto productRequestDto, Member member) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!product.getNickname().equals(member.getNickname())) {
            return new ResponseEntity<>("작성자만 수정할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        product.updateProduct(productRequestDto);

        return new ResponseEntity<>("글이 수정되었습니다.", HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<String> deleteProduct(Long productId, Member member) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!product.getNickname().equals(member.getNickname())) {
            return new ResponseEntity<>("작성자만 삭제할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        productRepository.deleteById(productId);

        return new ResponseEntity<>("글이 삭제되었습니다.", HttpStatus.NO_CONTENT);
    }
}
