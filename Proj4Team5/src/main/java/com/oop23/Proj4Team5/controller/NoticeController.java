package com.oop23.Proj4Team5.controller;


import com.oop23.Proj4Team5.exception.NoticeNotFoundException;
import com.oop23.Proj4Team5.repository.NoticeRepository;
import com.oop23.Proj4Team5.entity.request.NoticeCreationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.oop23.Proj4Team5.entity.Notice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeRepository noticeRepository;

    @PostMapping("/api/notice")
    public Notice addNotice(@RequestBody NoticeCreationRequest input) {
        Notice newNotice = Notice.builder()
                .title(input.getTitle())
                .contents(input.getContents())
                .isCalendar(input.getIsCalendar())
                .tagName(input.getTagName())
                .build();

        // isCalendar 이면 스케줄 생성해서 추가하기.
        // user 연결

        noticeRepository.save(newNotice);

        return newNotice;
    }

    @GetMapping("/api/notice/{id}")
    public Notice getNotice(@PathVariable Long id){
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("존재하지 않는 글입니다."));

        return notice;
    }

    @PutMapping("/api/notice/{id}")
    public void updateNotice(@PathVariable Long id, @RequestBody NoticeCreationRequest input){
        Notice notice = noticeRepository.findById(id)
                        .orElseThrow(() -> new NoticeNotFoundException("존재하지 않는 글입니다."));
        notice.update(input);
        noticeRepository.save(notice);
    }

    @ExceptionHandler(NoticeNotFoundException.class)
    public ResponseEntity<String> handlerNoticeNotFoundException(NoticeNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/api/notice/{id}")
    public void deleteNotice(@PathVariable Long id){
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("존재하지 않는 글입니다."));

        noticeRepository.delete(notice);
    }
}
