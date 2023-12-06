package com.oop23.Proj4Team5.entity;

import com.oop23.Proj4Team5.entity.request.NoticeCreationRequest;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity(name="notice")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long NoticeId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Boolean isCalendar;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TagStatus tagName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Account user;

    public void update(NoticeCreationRequest input) {
        this.title = input.getTitle();
        this.contents = input.getContents();
        this.isCalendar = input.getIsCalendar();
        this.tagName = input.getTagName();

    }
}