package com.oop23.Proj4Team5.repository;

import com.oop23.Proj4Team5.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
