package com.codegym.fashionshop.controller.NotificationController;


import com.codegym.fashionshop.dto.AddNewNotificationDTO;
import com.codegym.fashionshop.entities.Notification;
import com.codegym.fashionshop.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth/notification")
public class NotificationController {
    @Autowired
    INotificationService notificationService;

    @GetMapping("/role/{roleId}")
    public ResponseEntity<?> findAllNotification(@PathVariable("roleId") Long roleId) {
        try {
            List<Notification> notificationList = notificationService.getAllNotification(roleId);
            return ResponseEntity.ok(notificationList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching notifications");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNotification(@RequestBody AddNewNotificationDTO addNewNotificationDTO) {
        System.out.println("Đã vào phương thức tạo thông báo.");
        if (addNewNotificationDTO == null) {
            return new ResponseEntity<AddNewNotificationDTO>(HttpStatus.NO_CONTENT);
        } else {
            notificationService.addNotification(addNewNotificationDTO.getContent(),addNewNotificationDTO.getCreateDate(), addNewNotificationDTO.getTopic(), addNewNotificationDTO.getListRole());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @GetMapping("/detail/{notifId}")
    public ResponseEntity<?> findNotificationById(@PathVariable("notifId") Long notifId){
        try {
            Notification notification = notificationService.findNotificationById(notifId);
            return ResponseEntity.ok(notification);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching notifications");
        }
    }

}
