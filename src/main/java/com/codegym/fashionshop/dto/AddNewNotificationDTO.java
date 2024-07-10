package com.codegym.fashionshop.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class AddNewNotificationDTO {
    String content;
    String topic;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    LocalDateTime createDate;
    List<Long> listRole;

    public AddNewNotificationDTO() {
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public List<Long> getListRole() {
        return listRole;
    }

    public void setListRole(List<Long> listRole) {
        this.listRole = listRole;
    }
    public AddNewNotificationDTO(String content, String topic, LocalDateTime createDate, List<Long> listRole) {
        this.content = content;
        this.topic = topic;
        this.createDate = createDate;
        this.listRole = listRole;
    }
    public AddNewNotificationDTO(String content, String topic, List<Long> listRole) {
        this.content = content;
        this.topic = topic;
        this.listRole = listRole;
    }
}
