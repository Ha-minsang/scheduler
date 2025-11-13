package com.example.scheduler.user.entity;

import com.example.scheduler.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    // user 이름
    @Column(nullable = false)
    private String userName;

    // user 이메일
    @Column(nullable = false)
    private String email;


    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public void setUser(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}
