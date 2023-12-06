package com.oop23.Proj4Team5.entity.request;


import com.oop23.Proj4Team5.entity.TagStatus;
import jakarta.persistence.Column;
import lombok.Data;
@Data
public class NoticeCreationRequest {

    private String title;
    private String contents;
    private Boolean isCalendar;
    private TagStatus tagName;
}
