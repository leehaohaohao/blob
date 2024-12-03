package com.lihao.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lihao.constants.StringConstants;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class FeedBackDto {
    private String feedbackId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private String content;
    private String file;
    private String type;
    private String name;
    public void setFile(String file){
        if(file.contains(StringConstants.URL)){
            this.file=file;
            return;
        }
        this.file=StringConstants.URL+file;
    }
}
