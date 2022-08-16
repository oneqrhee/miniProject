package com.example.miniproject.service;

import com.example.miniproject.config.jwt.token.RequestToken;
import com.example.miniproject.dto.request.ProductRequestDto;
import com.example.miniproject.dto.response.CommentResponseDto;
import com.example.miniproject.dto.response.ProductResponseDto;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Post;
import com.example.miniproject.dto.response.ProductsResponseDto;
import com.example.miniproject.repository.LikesRepository;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.repository.ProductRepository;
import com.example.miniproject.s3Service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
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

    @Transactional
    public ResponseEntity<String> createProduct(MultipartFile multipartFile, ProductRequestDto productRequestDto,
                                                String username) throws IOException {
        String imgUrl = s3Uploader.upload(multipartFile, "upload");
        Member member = memberRepository.findByUsername(username).orElseThrow();

        Post post = Post.builder()
                .title(productRequestDto.getTitle())
                .imgUrl(imgUrl)
                .size(productRequestDto.getSize())
                .price(productRequestDto.getPrice())
                .content(productRequestDto.getContent())
                .nickname(member.getNickname())
                .member(member)
                .build();
        productRepository.save(post);
        return new ResponseEntity<>("글이 등록되었습니다.", HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> readAllPost() {
        List<Post> posts = productRepository.findAll();
        List<ProductsResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            postList.add(ProductsResponseDto.builder()
                    .title(post.getTitle())
                    .size(post.getSize())
                    .nickname(post.getNickname())
                    .imgUrl(post.getImgUrl())
                    .likesCnt(likesRepository.findAllByPost(post).size())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build());
        }
        return postList;
    }

    @Transactional(readOnly = true)
    public ProductResponseDto readPost(Long productId) {
        Post post = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        return ProductResponseDto.builder()
                .title(post.getTitle())
                .size(post.getSize())
                .nickname(post.getNickname())
                .price(post.getPrice())
                .content(post.getContent())
                .imgUrl(post.getImgUrl())
                .likesCnt(likesRepository.findAllByPost(post).size())
                .commentList(post.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList()))
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    @Transactional
    public ResponseEntity<String> updateProduct(Long productId, ProductRequestDto productRequestDto, HttpServletRequest request) {
        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기

        Member member = memberRepository.findByUsername(requestToken.getUsername().orElseThrow(
                () -> new IllegalArgumentException("Can not find username"))).orElseThrow();

        Post post = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!post.getNickname().equals(member.getNickname())) {
            return new ResponseEntity<>("작성자만 수정할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        post.updatePost(productRequestDto);

        return new ResponseEntity<>("글이 수정되었습니다.", HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<String> deleteProduct(Long productId, HttpServletRequest request) {
        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기

        Member member = memberRepository.findByUsername(requestToken.getUsername().orElseThrow(
                () -> new IllegalArgumentException("Can not find username"))).orElseThrow();

        Post post = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!post.getNickname().equals(member.getNickname())) {
            return new ResponseEntity<>("작성자만 삭제할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        productRepository.deleteById(productId);

        return new ResponseEntity<>("글이 삭제되었습니다.", HttpStatus.NO_CONTENT);
    }
}
