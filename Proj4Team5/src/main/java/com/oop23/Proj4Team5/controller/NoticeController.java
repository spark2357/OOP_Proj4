package com.oop23.Proj4Team5.controller;


import com.oop23.Proj4Team5.entity.Schedule;
import com.oop23.Proj4Team5.entity.request.NoticeInputRequest;
import com.oop23.Proj4Team5.exception.NoticeNotFoundException;
import com.oop23.Proj4Team5.repository.NoticeRepository;
import com.oop23.Proj4Team5.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.oop23.Proj4Team5.entity.Notice;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class NoticeController {
    private final NoticeRepository noticeRepository;
    private final ScheduleRepository scheduleRepository;

    @PostMapping("/api/notice")
    public Notice addNotice(@RequestBody NoticeInputRequest input) {
        Notice newNotice = Notice.builder()
                .title(input.getTitle())
                .contents(input.getContents())
                .isCalendar(input.getIsCalendar())
                .tagName(input.getTagName())
                .build();

        // isCalendar 이면 스케줄 생성해서 추가하기.
        if(input.getIsCalendar()){
            Schedule newSchedule = Schedule.builder()
                    .memo(input.getMemo())
                    .time(input.getTime())
                    .build();
            scheduleRepository.save(newSchedule);
            newNotice.addSchedule(newSchedule);
        }
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
    public void updateNotice(@PathVariable Long id, @RequestBody NoticeInputRequest input){
        Notice notice = noticeRepository.findById(id)
                        .orElseThrow(() -> new NoticeNotFoundException("존재하지 않는 글입니다."));

        if(notice.getIsCalendar()){
            Schedule schedule = scheduleRepository.findById(notice.getSchedule().getId())
                    .orElseThrow(() -> new NoticeNotFoundException("존재하지 않는 스케줄입니다."));
            if(!input.getIsCalendar()){ // schedule true -> false : 기존 schedule 삭제
                notice.deleteSchedule();
                scheduleRepository.delete(schedule);
            }
            else{ // schedule true -> true : 기존 schedule 수정
                schedule.update(input);
                scheduleRepository.save(schedule);
            }
        }
        else if(input.getIsCalendar()){ // schedule false -> true : 새로 생성
            Schedule newSchedule = Schedule.builder()
                    .memo(input.getMemo())
                    .time(input.getTime())
                    .build();

            scheduleRepository.save(newSchedule);
            notice.addSchedule(newSchedule);
        }

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
