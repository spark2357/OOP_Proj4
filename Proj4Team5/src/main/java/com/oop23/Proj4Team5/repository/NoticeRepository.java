package com.oop23.Proj4Team5.repository;

import com.oop23.Proj4Team5.entity.Notice;
import com.oop23.Proj4Team5.entity.TagStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByScheduleTime(String time);
}
