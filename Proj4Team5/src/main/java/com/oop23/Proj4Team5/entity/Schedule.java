package com.oop23.Proj4Team5.entity;

import com.oop23.Proj4Team5.entity.request.NoticeInputRequest;
import com.oop23.Proj4Team5.entity.request.NoticeUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@Entity(name="schedule")
@Builder
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memo;

    @Column(nullable = false)
    private String time;

    public void update(NoticeUpdateRequest input){
        this.memo = input.getMemo();
        this.time = input.getTime();
    }
}