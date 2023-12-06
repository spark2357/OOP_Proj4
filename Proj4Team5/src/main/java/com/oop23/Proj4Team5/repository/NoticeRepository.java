package com.oop23.Proj4Team5.repository;

import com.oop23.Proj4Team5.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
