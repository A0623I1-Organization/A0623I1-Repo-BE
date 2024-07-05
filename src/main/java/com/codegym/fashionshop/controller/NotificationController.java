package com.codegym.fashionshop.controller;


import com.codegym.fashionshop.dto.AddNewNotificationDTO;
import com.codegym.fashionshop.dto.INotificationDTO;
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
            List<INotificationDTO> notification = notificationService.getAllNotification(roleId);
            return ResponseEntity.ok(notification);
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
            notificationService.addNotification(addNewNotificationDTO.getContent(), addNewNotificationDTO.getCreateDate(), addNewNotificationDTO.getTopic(), addNewNotificationDTO.getListRole());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/getInfoNotification/{notifId}")
    public ResponseEntity<?> findNotificationById(@PathVariable("notifId") Long notifId) {
        try {
            Notification INotificationDTO = notificationService.findNotificationById(notifId);
            return ResponseEntity.ok(INotificationDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching notifications");
        }
    }
    @GetMapping("/userID/{userId}/statusRead/{status}")
    public ResponseEntity<?> getAllNotificationByStatusRead(@PathVariable("userId") Long userId, @PathVariable("status") boolean status) {
        try {
            List<Notification> INotificationDTOList = notificationService.findNotificationsByStatusRead(userId,status);
            return ResponseEntity.ok(INotificationDTOList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching notifications");
        }
    }


}
