package com.codegym.fashionshop.service;


import com.codegym.fashionshop.entities.Notification;

import java.time.LocalDateTime;
import java.util.List;

public interface INotificationService {
    List<Notification> getAllNotification(Long roleId);

    void addNotification(String content, LocalDateTime createDate, String topic, List<Long> listRole);
    Notification findNotificationById(Long notifId);
    List<Notification> findNotificationsByStatusRead(boolean statusRead);

}
