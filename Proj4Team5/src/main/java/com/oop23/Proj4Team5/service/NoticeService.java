package com.oop23.Proj4Team5.service;

import com.oop23.Proj4Team5.repository.AccountRepository;
import com.oop23.Proj4Team5.repository.NoticeRepository;
import com.oop23.Proj4Team5.repository.ScheduleRepository;
import com.oop23.Proj4Team5.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final AccountRepository accountRepository;
    private final NoticeRepository noticeRepository;
    private final ScheduleRepository scheduleRepository;

}
