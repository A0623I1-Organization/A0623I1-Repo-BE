package com.codegym.fashionshop.controller.notification;

import com.codegym.fashionshop.dto.AddNewNotificationDTO;
import com.codegym.fashionshop.dto.CheckNotificationExistsDTO;
import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.dto.respone.ErrorDetail;
import com.codegym.fashionshop.entities.Notification;
import com.codegym.fashionshop.service.notification.INotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing notifications.
 * Provides endpoints for CRUD operations on notifications.
 *
 * @author NhiNTY
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth/notification")
public class NotificationController {

    private final INotificationService notificationService;
    private static final String FETCH_ERROR_MESSAGE = "An error occurred while fetching notifications";

    public NotificationController(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Get a list of all notifications.
     *
     * @return ResponseEntity containing the list of notifications or an error message.
     */
    @GetMapping("/list")
    public ResponseEntity<Object> findAllNotification() {
        try {
            AuthenticationResponse response = notificationService.responseAuthentication();
            List<INotificationDTO> notification = notificationService.getAllNotification(response.getRole().getRoleId(), response.getUserId());
            return ResponseEntity.ok(notification);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FETCH_ERROR_MESSAGE);
        }
    }

    /**
     * Get a list of notifications filtered by read status.
     *
     * @param statusRead the read status of the notifications.
     * @return ResponseEntity containing the list of notifications or an error message.
     */
    @GetMapping("/listByStatusRead/{statusRead}")
    public ResponseEntity<Object> findAllNotificationByStatusRead(@PathVariable("statusRead") boolean statusRead) {
        try {
            AuthenticationResponse response = notificationService.responseAuthentication();
            List<INotificationDTO> notification = notificationService.findNotificationsByStatusRead(response.getUserId(), statusRead);
            return ResponseEntity.ok(notification);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FETCH_ERROR_MESSAGE);
        }
    }

    /**
     * Create a new notification.
     *
     * @param addNewNotificationDTO the notification data transfer object.
     * @param bindingResult the binding result for validation errors.
     * @return ResponseEntity containing the result of the creation or validation errors.
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Object> createNotification(@Validated @RequestBody AddNewNotificationDTO addNewNotificationDTO, BindingResult bindingResult) {
        if (addNewNotificationDTO == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (bindingResult.hasErrors()) {
            ErrorDetail errorDetail = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorDetail.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
        }
        int resultInsert = notificationService.addNotification(
                addNewNotificationDTO.getContent(),
                addNewNotificationDTO.getCreateDate(),
                addNewNotificationDTO.getTopic(),
                addNewNotificationDTO.getListRole());
        return ResponseEntity.ok(resultInsert);
    }

    /**
     * Get a notification by its ID and mark it as read.
     *
     * @param notifId the ID of the notification.
     * @return ResponseEntity containing the notification or an error message.
     */
    @GetMapping("/getInfoNotification/{notifId}")
    public ResponseEntity<Object> findNotificationById(@PathVariable("notifId") Long notifId) {
        Notification notification = notificationService.findNotificationById(notifId);
        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            notificationService.updateStatusRead(notifId);
            return ResponseEntity.ok(notification);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FETCH_ERROR_MESSAGE);
        }
    }

    /**
     * Mark all notifications as read for the current user.
     *
     * @return boolean indicating whether the operation was successful.
     */
    @GetMapping("/markAllRead")
    public boolean markAllRead() {
        AuthenticationResponse response = notificationService.responseAuthentication();
        return notificationService.markAsRead(response.getUserId());
    }

    /**
     * Check if the provided notification data exists.
     *
     * @param checkNotificationExistsDTO the data transfer object containing the notification data to check.
     * @return boolean indicating whether the notification data exists.
     */
    @PostMapping("/checkData")
    public boolean checkExists(@RequestBody CheckNotificationExistsDTO checkNotificationExistsDTO) {
        return notificationService.checkDataExists(checkNotificationExistsDTO);
    }
}
