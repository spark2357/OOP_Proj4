package com.oop23.Proj4Team5.entity;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memo;

    @Column(nullable = false)
    private String time;
}