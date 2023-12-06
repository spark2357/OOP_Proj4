package com.oop23.Proj4Team5.controller;

import com.oop23.Proj4Team5.entity.Notice;
import com.oop23.Proj4Team5.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final NoticeRepository noticeRepository;
    @GetMapping(value = "/api/list")
    public List<Notice> home() {
        return noticeRepository.findAll();
    }
}
