package com.oop23.Proj4Team5.repository;

import com.oop23.Proj4Team5.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findAllByNoticeId(Long NoticeId);
}
