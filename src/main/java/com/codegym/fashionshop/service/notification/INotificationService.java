package com.codegym.fashionshop.service.notification;


import com.codegym.fashionshop.dto.AddNewNotificationDTO;
import com.codegym.fashionshop.dto.CheckNotificationExistsDTO;
import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.entities.Notification;
import java.time.LocalDateTime;
import java.util.List;

public interface INotificationService {
    List<INotificationDTO> getAllNotification(Long roleId,Long userId);

    int addNotification(String content, LocalDateTime createDate, String topic, List<Long> listRole);
    Notification findNotificationById(Long notifId);
    List<INotificationDTO> findNotificationsByStatusRead(Long userId, boolean statusRead);
    boolean markAsRead(Long userId);
    boolean isMarkAllAsRead();
    void updateStatusRead(Long notifId);
    boolean checkDataExists(CheckNotificationExistsDTO checkNotificationExistsDTO);

}
