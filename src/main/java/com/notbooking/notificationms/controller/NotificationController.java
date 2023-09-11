package com.notbooking.notificationms.controller;

import com.notbooking.notificationms.exception.BadRequestException;
import com.notbooking.notificationms.exception.NotFoundException;
import com.notbooking.notificationms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "/createNotification/{userId}/{type}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNotification(@PathVariable int userId, @PathVariable int type) {
        try {
            notificationService.createNotification(userId, type);
            return new ResponseEntity<>("ok", HttpStatus.CREATED);
        }catch (BadRequestException e){
            return new ResponseEntity<>("Entered notification type does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/markAsRead/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        try {
            notificationService.markAsRead(id);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>("Notification not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getNotifications/{userId}/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getNotifications(@PathVariable int userId, @PathVariable int status) {
        try {
            return new ResponseEntity<>(notificationService.getNotifications(userId,status), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>("Notifications not found", HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>("Entered notification status does not exist", HttpStatus.BAD_REQUEST);
        }
    }

}
