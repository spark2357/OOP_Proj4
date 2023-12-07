package com.oop23.Proj4Team5.controller;

import com.oop23.Proj4Team5.entity.Notice;
import com.oop23.Proj4Team5.entity.Schedule;
import com.oop23.Proj4Team5.entity.TagStatus;
import com.oop23.Proj4Team5.repository.NoticeRepository;
import com.oop23.Proj4Team5.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final NoticeRepository noticeRepository;
    private final ScheduleRepository scheduleRepository;
    @GetMapping(value = "/api/schedule")
    public List<List<Notice>> getMainCalendar() {
        List<Notice> all = noticeRepository.findAll();
        // 스케줄 별로 정렬.
        List<Schedule> scheduleAll = scheduleRepository.findAll();
        HashSet<String> timeList = new HashSet<>();
        for(Schedule e : scheduleAll){
            timeList.add(e.getTime());
        }
        List<List<Notice>> schedule = new ArrayList<>();
        for(String e : timeList){
            schedule.add(noticeRepository.findByScheduleTime(e));
        }

        return schedule;
    }

    @GetMapping(value = "/api/notice")
    public List<List<Notice>> getMainNotice() {
        List<Notice> all = noticeRepository.findAll();

        List<Notice> notice = new ArrayList<>();
        List<Notice> resource = new ArrayList<>();

        for(Notice e : all){
            if(e.getTagName() == TagStatus.Notice){
                notice.add(e);
            }
            else{
                resource.add(e);
            }
        }

        List<List<Notice>> response = new ArrayList<>();

        response.add(notice);
        response.add(resource);

        return response;
    }
}
