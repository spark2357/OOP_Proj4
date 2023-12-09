package com.oop23.Proj4Team5.controller;


import com.oop23.Proj4Team5.entity.FileEntity;
import com.oop23.Proj4Team5.entity.Schedule;
import com.oop23.Proj4Team5.entity.request.NoticeInputRequest;
import com.oop23.Proj4Team5.exception.NoticeNotFoundException;
import com.oop23.Proj4Team5.repository.FileRepository;
import com.oop23.Proj4Team5.repository.NoticeRepository;
import com.oop23.Proj4Team5.repository.ScheduleRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import com.oop23.Proj4Team5.entity.Notice;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeRepository noticeRepository;
    private final ScheduleRepository scheduleRepository;
    private final FileRepository fileRepository;

    private static final String path = "/root/Upload/Files/";

    @PostMapping("/api/notice")
    public Notice addNotice(@ModelAttribute NoticeInputRequest input) {
        System.out.println(input);
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
        noticeRepository.save(newNotice);

        if(input.getFiles() != null){
            if(!input.getFiles().isEmpty()) {
                List<Long> idList = uploadFile(input.getFiles(), newNotice.getNoticeId());
            }
        }
        // id contents에 넣기

        // user 연결

        return newNotice;
    }

    @GetMapping("/api/notice/{id}")
    public Notice getNotice(@PathVariable Long id){
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("존재하지 않는 글입니다."));

        return notice;
    }

    @GetMapping("api/notice/file/{id}")
    public HashMap<String, Long> getFiles(@PathVariable Long id){
        List<FileEntity> all = fileRepository.findAllByNoticeId(id);

        HashMap<String, Long> response = new HashMap<>();

        for(FileEntity e : all){
            response.put(e.getOriginalName(), e.getId());
        }

        return response;
    }

    @PutMapping("/api/notice/{id}")
    public void updateNotice(@PathVariable Long id, @ModelAttribute NoticeInputRequest input){
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
        deleteFile(input.getFiles(), notice.getNoticeId());
        if(input.getFiles() != null){
            if(!input.getFiles().isEmpty()) {
                List<Long> idList = uploadFile(input.getFiles(), notice.getNoticeId());
            }
        }

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

    public List<Long> uploadFile(ArrayList<MultipartFile> files, Long noticeId){
        String savedFileName = "";
        // 1. 파일 저장 경로 설정 : 실제 서비스되는 위치(프로젝트 외부에 저장)
        String uploadPath = path;
        // 여러 개의 원본 파일을 저장할 리스트 생성
        ArrayList<String> originalFileNameList = new ArrayList<String>();

        List<Long> idList = new ArrayList<Long>();

        for(MultipartFile file : files) {
            // 2. 원본 파일 이름 알아오기
            String originalFileName = file.getOriginalFilename();
            // 3. 파일 이름을 리스트에 추가
            originalFileNameList.add(originalFileName);
            // 4. 파일 이름 중복되지 않게 이름 변경(서버에 저장할 이름) UUID 사용
            UUID uuid = UUID.randomUUID();
            savedFileName = uuid.toString() + "_" + originalFileName;
            // 5. 파일 생성
            File file1 = new File(uploadPath + savedFileName);
            System.out.println(uploadPath + savedFileName);
            // 6. 서버로 전송
            try {
                file.transferTo(file1);

                FileEntity newFile = FileEntity.builder()
                        .savedName(savedFileName)
                        .uploadPath(uploadPath)
                        .noticeId(noticeId)
                        .originalName(originalFileName)
                        .build();

                fileRepository.save(newFile);
                idList.add(newFile.getId());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return idList;
    }

    @RequestMapping("/download/{id}")
    public void fileDownload(@PathVariable Long id, HttpServletResponse response) throws IOException {
        FileEntity downloadFile = fileRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("존재하지 않는 파일입니다."));
        String file = downloadFile.getSavedName();
        File f = new File(path, file);
        // file 다운로드 설정
        response.setContentType("application/download");
        response.setContentLength((int)f.length());
        response.setHeader("Content-disposition", "attachment;filename=\"" + file + "\"");
        // response 객체를 통해서 서버로부터 파일 다운로드
        OutputStream os = response.getOutputStream();
        // 파일 입력 객체 생성
        FileInputStream fis = new FileInputStream(f);
        FileCopyUtils.copy(fis, os);
        fis.close();
        os.close();
    }

    public void deleteFile(ArrayList<MultipartFile> files, Long noticeId){
        List<FileEntity> all = fileRepository.findAllByNoticeId(noticeId);

        for(FileEntity e : all){
            fileRepository.delete(e);
        }
    }
}
