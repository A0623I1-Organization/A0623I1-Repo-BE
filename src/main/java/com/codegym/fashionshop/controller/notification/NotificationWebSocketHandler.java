package com.codegym.fashionshop.controller.notification;

import com.codegym.fashionshop.dto.AddNewNotificationDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * WebSocket message handler for handling notification messages.
 * Receives and processes notifications sent over WebSocket and broadcasts them to subscribers.
 *
 * @author NhiNTY
 */
@Controller
public class NotificationWebSocketHandler {

    /**
     * Handles incoming notification messages.
     * Receives a notification DTO and broadcasts it to subscribers on the specified WebSocket topic.
     *
     * @param addNewNotificationDTO the DTO containing the new notification data.
     * @return the same notification DTO received, which is broadcasted to subscribers.
     */
    @MessageMapping("/sendNotification")
    @SendTo("/topic/createNotification")
    public AddNewNotificationDTO sendNotification(AddNewNotificationDTO addNewNotificationDTO){
        return addNewNotificationDTO ;
    }
    @MessageMapping("/detailNotification")
    @SendTo("/topic/notification")
    public String sendNotification(String message){
        return message ;
    }
}
