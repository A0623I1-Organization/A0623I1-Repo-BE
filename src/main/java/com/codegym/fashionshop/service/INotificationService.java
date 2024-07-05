package com.codegym.fashionshop.service;


import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.entities.Notification;
import java.time.LocalDateTime;
import java.util.List;

public interface INotificationService {
    List<INotificationDTO> getAllNotification(Long roleId);

    void addNotification(String content, LocalDateTime createDate, String topic, List<Long> listRole);
    Notification findNotificationById(Long notifId);
    List<Notification> findNotificationsByStatusRead(Long userId, boolean statusRead);
    void markAsRead(Long userId);

}
