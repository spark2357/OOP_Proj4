package com.oop23.Proj4Team5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@Entity(name="File")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private Long noticeId;

    @Column(nullable = false)
    private String savedName;

    @Column(nullable = false)
    private String uploadPath;

    @Column(nullable = false)
    private String originalName;
}
