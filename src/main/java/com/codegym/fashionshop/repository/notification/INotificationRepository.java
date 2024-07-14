package com.codegym.fashionshop.repository.notification;

import com.codegym.fashionshop.dto.AddNewNotificationDTO;
import com.codegym.fashionshop.dto.CheckNotificationExistsDTO;
import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.entities.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing Notification entities.
 * Provides methods for performing CRUD operations and custom queries on notifications.
 *
 * @author NhiNTY
 */
@Transactional
@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Retrieves a list of notifications based on the role ID and user ID.
     *
     * @param roleId the ID of the role.
     * @param userId the ID of the user.
     * @return a list of notification DTOs.
     */
    @Query(value = "SELECT n.notif_id,n.topic,n.create_date,n.content,u.status_read from notification n\n" +
            "join user_notification u on n.notif_id=u.notif_id\n" +
            "join app_user a on a.user_id = u.user_id\n" +
            "where (a.role_id=:roleId and u.user_id=:userId) order by n.create_date desc;", nativeQuery = true)
    List<INotificationDTO> findAll(@Param("roleId") Long roleId, @Param("userId") Long userId);

    /**
     * Inserts a new notification into the database.
     *
     * @param content    the content of the notification.
     * @param createDate the creation date of the notification.
     * @param topic      the topic of the notification.
     * @return the number of rows affected by the insert operation.
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO notification(content,create_date,topic) values(:content,:createDate,:topic)", nativeQuery = true)
    int createNotification(@Param("content") String content, @Param("createDate") LocalDateTime createDate, @Param("topic") String topic);

    /**
     * Adds a new notification entry for each user specified in the list of roles.
     *
     * @param notifId   the ID of the notification.
     * @param listRole  the list of role IDs.
     * @return the number of rows affected by the insert operation.
     */
    @Modifying
    @Transactional
    @Query(value = "   INSERT INTO user_notification (status_read, user_id, notif_id)\n" +
            "    SELECT 0, a.user_id,:notifId\n" +
            "    FROM app_user a\n" +
            "    JOIN app_role r ON a.role_id = r.role_id\n" +
            "    WHERE r.role_id in :listRole;", nativeQuery = true)
    int addNewNotification(@Param("notifId") Long notifId, @Param("listRole") List<Long> listRole);

    /**
     * Retrieves the ID of the last inserted notification.
     *
     * @return the ID of the last inserted notification.
     */
    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Long getLastInsertNotificationId();

    /**
     * Retrieves a notification by its ID.
     *
     * @param notifId the ID of the notification.
     * @return the notification entity.
     */
    @Query(value = "select * from notification n where n.notif_id=:notifId; ", nativeQuery = true)
    Notification findNotificationByNotifId(@Param("notifId") Long notifId);

    /**
     * Retrieves a list of notifications filtered by read status for a specific user.
     *
     * @param userId    the ID of the user.
     * @param statusRead the read status of the notifications.
     * @return a list of notification DTOs.
     */
    @Query(value = "select n.notif_id,n.content,n.create_date,n.topic,u.status_read from notification n\n" +
            "join user_notification u on n.notif_id=u.notif_id\n" +
            "join app_user a on a.user_id=u.user_id\n" +
            "where (u.status_read=:statusRead and a.user_id=:userId) order by n.create_date desc;", nativeQuery = true)
    List<INotificationDTO> findNotificationsByStatusRead(@Param("userId") Long userId, @Param("statusRead") boolean statusRead);

    /**
     * Marks all notifications as read for a specific user.
     *
     * @param userId the ID of the user.
     * @return the number of notifications marked as read.
     */
    @Modifying
    @Transactional
    @Query(value = "update user_notification u set u.status_read=1 where u.status_read=0 and u.user_id=:userId", nativeQuery = true)
    int markAsRead(@Param("userId") Long userId);

    /**
     * Updates the read status of a specific notification for a user.
     *
     * @param userId  the ID of the user.
     * @param notifId the ID of the notification.
     */
    @Modifying
    @Transactional
    @Query(value = "update user_notification u set status_read=1 where u.user_id=:userId and u.status_read=0 and u.notif_id=:notifId", nativeQuery = true)
    void updateStatusRead(@Param("userId") Long userId, @Param("notifId") Long notifId);

    /**
     * Checks if a notification with the given topic, content, and roles exists.
     *
     * @param topic     the topic of the notification.
     * @param content   the content of the notification.
     * @param listRole  the list of role IDs.
     * @return the number of matching notifications found.
     */
    @Query(value = "SELECT count(u.id) \n" +
            "FROM notification n\n" +
            "JOIN user_notification u ON u.notif_id = n.notif_id\n" +
            "JOIN app_user a ON a.user_id = u.user_id\n" +
            "WHERE n.topic =:topic\n" +
            "AND n.content =:content\n" +
            "AND a.role_id IN :listRole\n", nativeQuery = true)
    int checkDataExists(@Param("topic") String topic, @Param("content") String content, @Param("listRole") List<Long> listRole);
}
