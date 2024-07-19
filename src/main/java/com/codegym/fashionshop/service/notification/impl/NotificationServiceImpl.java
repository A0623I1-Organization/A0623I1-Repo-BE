package com.codegym.fashionshop.service.notification.impl;

import com.codegym.fashionshop.dto.CheckNotificationExistsDTO;
import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.Notification;
import com.codegym.fashionshop.repository.notification.INotificationRepository;
import com.codegym.fashionshop.service.authenticate.impl.AuthenticationService;
import com.codegym.fashionshop.service.notification.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of {@link INotificationService} for managing notifications.
 * This class interacts with the repository layer to perform CRUD operations on notifications.
 *
 * <p>Author: NhiNTY</p>
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {

    private final INotificationRepository notificationRepository;
    private final AuthenticationService authenticationService;

    /**
     * Retrieves all notifications for a given role and user.
     *
     * @param roleId the ID of the role
     * @param userId the ID of the user
     * @return a list of {@link INotificationDTO} objects representing the notifications
     */
    @Override
    public List<INotificationDTO> getAllNotification(Long roleId, Long userId) {
        return notificationRepository.findAll(roleId, userId);
    }

    /**
     * Adds a new notification.
     *
     * @param content    the content of the notification
     * @param createDate the date and time the notification was created
     * @param topic      the topic of the notification
     * @param listRole   the list of roles associated with the notification
     */
    @Override
    public void addNotification(String content, LocalDateTime createDate, String topic, List<Long> listRole) {
        notificationRepository.createNotification(content, createDate, topic);
    }

    /**
     * Finds a notification by its ID.
     *
     * @param notifId the ID of the notification
     * @return the {@link Notification} object if found, or null if not found
     */
    @Override
    public Notification findNotificationById(Long notifId) {
        return notificationRepository.findNotificationByNotifId(notifId);
    }

    /**
     * Retrieves notifications for a given user based on their read status.
     *
     * @param userId     the ID of the user
     * @param statusRead the read status of the notifications
     * @return a list of {@link INotificationDTO} objects representing the notifications
     */
    @Override
    public List<INotificationDTO> findNotificationsByStatusRead(Long userId, boolean statusRead) {
        return notificationRepository.findNotificationsByStatusRead(userId, statusRead);
    }

    /**
     * Marks all notifications for a given user as read.
     *
     * @param userId the ID of the user
     * @return true if any notifications were marked as read, false otherwise
     */
    @Override
    public boolean markAsRead(Long userId) {
        if (!isMarkAllAsRead()) {
            int updateRows = notificationRepository.markAsRead(userId);
            return updateRows > 0;
        }
        return false;
    }

    /**
     * Checks if all notifications have been marked as read.
     *
     * @return true if all notifications are marked as read, false otherwise
     */
    @Override
    public boolean isMarkAllAsRead() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthenticationResponse response = authenticationService.getMyInfo(username);
        List<INotificationDTO> list = notificationRepository.findNotificationsByStatusRead(response.getUserId(), false);
        return list == null;
    }

    /**
     * Updates the read status of a notification for a given user.
     *
     * @param notifId the ID of the notification
     */
    @Override
    public void updateStatusRead(Long notifId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthenticationResponse response = authenticationService.getMyInfo(username);
        notificationRepository.updateStatusRead(response.getUserId(), notifId);
    }

    /**
     * Checks if notification data already exists based on provided criteria.
     *
     * @param checkNotificationExistsDTO the DTO containing the criteria for checking
     * @return true if the data exists, false otherwise
     */
    @Override
    public boolean checkDataExists(CheckNotificationExistsDTO checkNotificationExistsDTO) {
        int listSize = notificationRepository.checkDataExists(
                checkNotificationExistsDTO.getTopic(),
                checkNotificationExistsDTO.getContent(),
                checkNotificationExistsDTO.getListRole());
        return listSize > 0;
    }

    /**
     * Retrieves the authentication response for the currently authenticated user.
     *
     * @return the {@link AuthenticationResponse} object containing the user's authentication information
     */
    @Override
    public AuthenticationResponse responseAuthentication() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            return authenticationService.getMyInfo(username);
        } catch (Exception e) {
            return new AuthenticationResponse();
        }
    }
}
