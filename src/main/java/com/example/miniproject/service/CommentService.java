package com.example.miniproject.service;

import com.example.miniproject.dto.request.CommentRequestDto;
import com.example.miniproject.dto.response.CommentResponseDto;
import com.example.miniproject.entity.Comment;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Product;
import com.example.miniproject.repository.CommentRepository;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.repository.ProductRepository;
import com.example.miniproject.security.config.jwt.token.RequestToken;
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

    private final MemberRepository memberRepository;

    private final ProductRepository productRepository;


    @Transactional
    public CommentResponseDto createComment(Long productId, CommentRequestDto requestDto, HttpServletRequest request) {
        // 멤버 유효성 검사
        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기

        Member member = memberRepository.findByUsername(requestToken.getUsername().orElseThrow(
                () -> new IllegalArgumentException("Can not find username"))).orElseThrow();

        //포스트 id 검사
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("product id is not exist"));

        Comment comment = Comment.builder()
                .member(member)
                .nickname(member.getNickname())
                .product(product)
                .content(requestDto.getContent())
                .build();

        commentRepository.save(comment);

        return CommentResponseDto.builder()
                .id(comment.getId())
                .nickname(comment.getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

    public CommentResponseDto getAllComments(Long id) {
        // 포스트 id 검사
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("product id is not exist"));


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

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto,
                                            HttpServletRequest request) {
        //멤버 유효성 검사
        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기

        Member member = memberRepository.findByUsername(requestToken.getUsername().orElseThrow(
                () -> new IllegalArgumentException("Can not find username"))).orElseThrow();

        //댓글 id 검사
        Comment comment = isPresentComment(commentId);
        if (null == comment){
            throw new IllegalArgumentException("comment id is not exist");
        }

        //댓글 멤버 유효성 검사
        if (comment.validateMember(member)){
            throw new IllegalArgumentException("only author can update");
        }

        comment.update(requestDto);

        return CommentResponseDto.builder()
                .id(comment.getId())
                .nickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();

    }

    public void deleteComment(Long commentId, HttpServletRequest request) {
        //멤버 유효성 검사
        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기

        Member member = memberRepository.findByUsername(requestToken.getUsername().orElseThrow(
                () -> new IllegalArgumentException("Can not find username"))).orElseThrow();

        //댓글 id 검사
        Comment comment = isPresentComment(commentId);
        if (null == comment){
            throw new IllegalArgumentException("comment id is not exist");
        }

        //댓글 멤버 유효성 검사
        if (comment.validateMember(member)){
            throw new IllegalArgumentException("only author can update");
        }

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }
}
