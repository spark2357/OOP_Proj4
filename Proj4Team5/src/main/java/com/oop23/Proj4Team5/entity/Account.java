package com.oop23.Proj4Team5.entity;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="LogIn")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    private boolean isAdmin;
}
