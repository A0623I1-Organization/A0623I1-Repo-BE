package com.codegym.fashionshop.service.notification.impl;

import com.codegym.fashionshop.dto.AddNewNotificationDTO;
import com.codegym.fashionshop.dto.CheckNotificationExistsDTO;
import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.Notification;
import com.codegym.fashionshop.repository.notification.INotificationRepository;
import com.codegym.fashionshop.service.authenticate.impl.AuthenticationService;
import com.codegym.fashionshop.service.notification.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {
    @Autowired
    INotificationRepository notificationRepository;
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public List<INotificationDTO> getAllNotification(Long roleId, Long userId) {
        return notificationRepository.findAll(roleId, userId);
    }

    @Override
    public int addNotification(String content, LocalDateTime createDate, String topic, List<Long> listRole) {
        int create_insert = notificationRepository.createNotification(content, createDate, topic);
        if (create_insert > 0) {
            Long last_insert = notificationRepository.getLastInsertNotificationId();
            return notificationRepository.addNewNotification(last_insert, listRole);
        }
        return 0;
    }

    @Override
    public Notification findNotificationById(Long notifId) {
        return notificationRepository.findNotificationByNotifId(notifId);
    }

    @Override
    public List<INotificationDTO> findNotificationsByStatusRead(Long userId, boolean statusRead) {
        return notificationRepository.findNotificationsByStatusRead(userId, statusRead);
    }

    @Override
    public boolean markAsRead(Long userId) {
        if (!isMarkAllAsRead()) {
            int updateRows = notificationRepository.markAsRead(userId);
            return updateRows > 0;
        }
        return false;
    }

    @Override
    public boolean isMarkAllAsRead() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthenticationResponse response = authenticationService.getMyInfo(username);
        List<INotificationDTO> list = notificationRepository.findNotificationsByStatusRead(response.getUserId(), false);
        if (list == null) {
            return true;
        }
        return false;
    }

    @Override
    public void updateStatusRead(Long notifId) {
        System.out.println("");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthenticationResponse response = authenticationService.getMyInfo(username);
        notificationRepository.updateStatusRead(response.getUserId(), notifId);
    }

    @Override
    public boolean checkDataExists(CheckNotificationExistsDTO checkNotificationExistsDTO) {
        int listSize = notificationRepository.checkDataExists(
                checkNotificationExistsDTO.getTopic(),
                checkNotificationExistsDTO.getContent(),
                checkNotificationExistsDTO.getListRole());
        if (listSize>0){
            return true;
        }
        return false;
    }
}
