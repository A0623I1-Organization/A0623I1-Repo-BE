package com.codegym.fashionshop.service.notification;

import com.codegym.fashionshop.dto.AddNewNotificationDTO;
import com.codegym.fashionshop.dto.CheckNotificationExistsDTO;
import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.Notification;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for managing notifications.
 *
 * @author NhiNTY
 */
public interface INotificationService {

    /**
     * Retrieves all notifications based on the given roleId and userId.
     *
     * @param roleId The ID of the role to filter notifications.
     * @param userId The ID of the user to filter notifications.
     * @return A list of {@link INotificationDTO} objects.
     */
    List<INotificationDTO> getAllNotification(Long roleId, Long userId);

    /**
     * Adds a new notification with the provided details.
     *
     * @param content    The content of the notification.
     * @param createDate The creation date and time of the notification.
     * @param topic      The topic or subject of the notification.
     * @param listRole   A list of role IDs to which the notification should be sent.
     * @return The number of notifications added.
     */
    int addNotification(String content, LocalDateTime createDate, String topic, List<Long> listRole);

    /**
     * Finds a notification by its ID.
     *
     * @param notifId The ID of the notification to find.
     * @return The {@link Notification} object if found, null otherwise.
     */
    Notification findNotificationById(Long notifId);

    /**
     * Finds notifications by the read status for a specific user.
     *
     * @param userId     The ID of the user to filter notifications.
     * @param statusRead The read status of the notifications (true for read, false for unread).
     * @return A list of {@link INotificationDTO} objects matching the criteria.
     */
    List<INotificationDTO> findNotificationsByStatusRead(Long userId, boolean statusRead);

    /**
     * Marks all notifications as read for a specific user.
     *
     * @param userId The ID of the user to mark notifications as read.
     * @return true if successfully marked as read, false otherwise.
     */
    boolean markAsRead(Long userId);

    /**
     * Marks all notifications as read for all users.
     *
     * @return true if successfully marked all as read, false otherwise.
     */
    boolean isMarkAllAsRead();

    /**
     * Updates the read status of a notification.
     *
     * @param notifId The ID of the notification to update.
     */
    void updateStatusRead(Long notifId);

    /**
     * Checks if a specific notification data exists.
     *
     * @param checkNotificationExistsDTO The DTO containing data to check existence.
     * @return true if the data exists, false otherwise.
     */
    boolean checkDataExists(CheckNotificationExistsDTO checkNotificationExistsDTO);

    /**
     * Retrieves authentication response for the service.
     *
     * @return An {@link AuthenticationResponse} object.
     */
    AuthenticationResponse responseAuthentication();
}
