package com.example.miniproject.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @Column(unique = true)
    private String nickname;

    public Member(String username, String password,String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public static Member createMember(String username, String password , String nickname) {
        return new Member(username,password,nickname);
    }

}
