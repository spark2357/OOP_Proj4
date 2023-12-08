package com.oop23.Proj4Team5.entity.request;


import com.oop23.Proj4Team5.entity.TagStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Data
public class NoticeInputRequest {

    private String title;
    private String contents;
    private Boolean isCalendar;
    private TagStatus tagName;

    private String memo;
    private String time;

    private ArrayList<MultipartFile> files;
}
