package com.example.miniproject.service;

import com.example.miniproject.dto.request.CommentRequestDto;
import com.example.miniproject.dto.response.CommentResponseDto;
import com.example.miniproject.entity.Comment;
import com.example.miniproject.entity.Product;
import com.example.miniproject.repository.CommentRepository;
import com.example.miniproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final ProductRepository productRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, HttpServletRequest request) {

        // 멤버 유효성 검사

        //포스트 id 검사
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Comment comment = Comment.builder()
//                .member(member)
                .product(product)
                .content(requestDto.getContent())
                .build();


        commentRepository.save(comment);

        return CommentResponseDto.builder()
                .id(comment.getId())
                .nickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

    public CommentResponseDto getAllComments(Long id) {

        // 포스트 id 검사
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        List<Comment> commentList = commentRepository.findAllByProduct(product);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList){
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .nickname(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }
        return (CommentResponseDto) commentResponseDtoList;
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto,
                                            HttpServletRequest request) {

        //멤버 유효성 검사

        //포스트 id 검사
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Comment comment = isPresentComment(id);
        if (null == comment){
            throw new IllegalArgumentException("존재하지 않은 댓글입니다.");
        }

        //댓글 멤버 유효성 검사

        comment.update(requestDto);

        return CommentResponseDto.builder()
                .id(comment.getId())
                .nickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();

    }

    public void deleteComment(Long id, HttpServletRequest request) {

        //멤버 유효성 검사

        Comment comment = isPresentComment(id);
        if (null == comment){
            throw new IllegalArgumentException("존재하지 않은 댓글입니다.");
        }

        //댓글 멤버 유효성 검사

        commentRepository.delete(comment);
    }


    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }


}
