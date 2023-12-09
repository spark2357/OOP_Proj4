package com.oop23.Proj4Team5.entity.request;


import com.oop23.Proj4Team5.entity.TagStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class NoticeUpdateRequest {

    private String title;
    private String contents;
    private Boolean isCalendar;
    private TagStatus tagName;

    private String memo;
    private String time;

    private List<Long> checked;
    private ArrayList<MultipartFile> files;
}
