package com.codegym.fashionshop.controller.notification;


import com.codegym.fashionshop.dto.AddNewNotificationDTO;
import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.Notification;
import com.codegym.fashionshop.service.authenticate.impl.AuthenticationService;
import com.codegym.fashionshop.service.notification.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth/notification")
public class NotificationController {
    @Autowired
    INotificationService notificationService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/list")
    public ResponseEntity<?> findAllNotification() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            AuthenticationResponse response = authenticationService.getMyInfo(username);
            System.out.println("App user dang su dung co user name : " + response.getUsername());
            List<INotificationDTO> notification = notificationService.getAllNotification(response.getRole().getRoleId(), response.getUserId());
            return ResponseEntity.ok(notification);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching notifications");
        }
    }

    @GetMapping("/listByStatusRead/{statusRead}")
    public ResponseEntity<?> findAllNotificationByStatusRead(@PathVariable("statusRead") boolean statusRead) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            AuthenticationResponse response = authenticationService.getMyInfo(username);
            List<INotificationDTO> notification = notificationService.findNotificationsByStatusRead(response.getUserId(), statusRead);
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
            notificationService.updateStatusRead(notifId);
            return ResponseEntity.ok(INotificationDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching notifications");
        }
    }

    @GetMapping("/markAllRead")
    public boolean markAllRead() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            AuthenticationResponse response = authenticationService.getMyInfo(username);
            return notificationService.markAsRead(response.getUserId());
    }



}
