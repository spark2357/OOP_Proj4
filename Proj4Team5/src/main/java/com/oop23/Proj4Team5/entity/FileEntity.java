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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "noticeId", referencedColumnName = "noticeId")
    private Notice notice;

    @Column(nullable = false)
    private String savedName;

    @Column(nullable = false)
    private String uploadPath;

    public void addNotice(Notice notice){
        this.notice = notice;
    }
}
