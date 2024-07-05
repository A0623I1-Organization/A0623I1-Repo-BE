package com.codegym.fashionshop.service.impl;

import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.entities.Notification;
import com.codegym.fashionshop.repository.INotificationRepository;
import com.codegym.fashionshop.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {
    @Autowired
    INotificationRepository notificationRepository;

    @Override
    public List<INotificationDTO> getAllNotification(Long roleId) {
        return notificationRepository.findAll(roleId);
    }

    @Override
    public void addNotification(String content, LocalDateTime createDate, String topic, List<Long> listRole) {
        notificationRepository.createNotification(content,createDate,topic);
        Long last_insert= notificationRepository.getLastInsertNotificationId();
        notificationRepository.addNewNotification(last_insert,listRole);
    }

    @Override
    public Notification findNotificationById(Long notifId) {
        return notificationRepository.findNotificationByNotifId(notifId);
    }

    @Override
    public List<Notification> findNotificationsByStatusRead(Long userId, boolean statusRead) {
        return notificationRepository.findNotificationsByStatusRead(userId,statusRead);
    }

    @Override
    public void markAsRead(Long userId) {
        notificationRepository.markAsRead(userId);
    }
}
