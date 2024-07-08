package com.codegym.fashionshop.repository;

import com.codegym.fashionshop.entities.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Transactional
@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
    @Query(value = "SELECT n.notif_id,n.topic,n.create_date,n.content,u.status_read from notification n\n" +
            "join user_notification u on n.notif_id=u.notif_id\n" +
            "join app_user a on a.user_id = u.user_id\n" +
            "where a.role_id=:roleId order by n.create_date desc", nativeQuery = true)
    List<Notification> findAll(@Param("roleId") Long roleId);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO notification(content,create_date,topic) values(:content,:createDate,:topic)", nativeQuery = true)
    Long createNotification(@Param("content") String content, @Param("createDate") LocalDateTime createDate, @Param("topic") String topic);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO UserNotification (statusRead, userId, notifId) " +
            "SELECT 0, u.userId, :notifId " +
            "FROM AppUser u " +
            "JOIN u.role r " +
            "WHERE r.roleId IN :listRole", nativeQuery = true)
    void addNewNotification(@Param("notifId") Long notifId, @Param("listRole") List<Long> listRole);

    @Query(value = "SELECT LAST_INSERT_ID()",nativeQuery = true)
    Long getLastInsertNotificationId();

    @Query(value = "select * from notification n where n.notif_id=:notifId; ", nativeQuery = true)
    Notification findNotificationByNotifId(@Param("notifId") Long notifId);

    @Query(value = "select n.content,n.create_date,n.topic from notification n\n" +
            "join user_notification u on n.notif_id=u.notif_id\n" +
            "where u.status_read=:statusRead", nativeQuery = true)
    List<Notification> findNotificationsByStatusRead(@Param("statusRead") boolean statusRead);
}