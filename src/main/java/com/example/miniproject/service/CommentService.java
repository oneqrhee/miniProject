package com.example.miniproject.service;

import com.example.miniproject.dto.request.CommentRequestDto;
import com.example.miniproject.dto.response.CommentResponseDto;
import com.example.miniproject.entity.Comment;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Product;
import com.example.miniproject.jwt.TokenProvider;
import com.example.miniproject.repository.CommentRepository;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final TokenProvider tokenProvider;


    @Transactional
    public ResponseEntity<String> createComment(Long productId, CommentRequestDto requestDto, HttpServletRequest request) {
        // 멤버 유효성 검사
//        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기
//
//        Member member = memberRepository.findByUsername(requestToken.getUsername().orElseThrow(
//                () -> new IllegalArgumentException("Can not find username"))).orElseThrow();

        Member member = validateMember(request);
        if (null == member) {
            return new ResponseEntity<>("INVALID_TOKEN", HttpStatus.BAD_REQUEST);
        }

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

        return new ResponseEntity<>("댓글이 등록되었습니다.", HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public ResponseEntity<String> updateComment(Long commentId, CommentRequestDto requestDto,
                                            HttpServletRequest request) {
        //멤버 유효성 검사
//        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기
//
//        Member member = memberRepository.findByUsername(requestToken.getUsername().orElseThrow(
//                () -> new IllegalArgumentException("Can not find username"))).orElseThrow();
        Member member = validateMember(request);
        if (null == member) {
            return new ResponseEntity<>("INVALID_TOKEN", HttpStatus.BAD_REQUEST);
        }

        //댓글 id 검사
        Comment comment = isPresentComment(commentId);
        if (null == comment){
            throw new IllegalArgumentException("comment id is not exist");
        }

        //댓글 멤버 유효성 검사
//        if (comment.validateMember(member)){
//            throw new IllegalArgumentException("only author can update");
//        }
        if (!comment.getNickname().equals(member.getNickname())) {
            return new ResponseEntity<>("작성자만 수정할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }

        comment.update(requestDto);

        return new ResponseEntity<>("댓글이 수정되었습니다.", HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<String> deleteComment(Long commentId, HttpServletRequest request) {
        //멤버 유효성 검사
//        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기
//
//        Member member = memberRepository.findByUsername(requestToken.getUsername().orElseThrow(
//                () -> new IllegalArgumentException("Can not find username"))).orElseThrow();
        Member member = validateMember(request);
        if (null == member) {
            return new ResponseEntity<>("INVALID_TOKEN", HttpStatus.BAD_REQUEST);
        }

        //댓글 id 검사
        Comment comment = isPresentComment(commentId);
        if (null == comment){
            throw new IllegalArgumentException("comment id is not exist");
        }

        //댓글 멤버 유효성 검사
//        if (comment.validateMember(member)){
//            throw new IllegalArgumentException("only author can update");
//        }
        if (!comment.getNickname().equals(member.getNickname())) {
            return new ResponseEntity<>("작성자만 수정할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }

        commentRepository.delete(comment);

        return new ResponseEntity<>("댓글이 삭제되었습니다.", HttpStatus.NO_CONTENT);
    }

    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (!tokenProvider.validateToken(accessToken.substring(7))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
