package com.notbooking.notificationms.service;

import com.notbooking.notificationms.dto.NotificationDTO;
import com.notbooking.notificationms.exception.BadRequestException;
import com.notbooking.notificationms.exception.NotFoundException;
import com.notbooking.notificationms.model.Notification;
import com.notbooking.notificationms.model.enums.NotificationType;
import com.notbooking.notificationms.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(int userId, int type){
        Notification n = new Notification(userId);
        switch(type) {
            case 1:
                // RESERVATION_REQUEST
                n.setType(NotificationType.RESERVATION_REQUEST);
                break;
            case 2:
                // RESERVATION_CANCELLATION
                n.setType(NotificationType.RESERVATION_CANCELLATION);
                break;
            case 3:
                // HOST_RATING
                n.setType(NotificationType.HOST_RATING);
                break;
            case 4:
                // ACCOMMODATION_RATING
                n.setType(NotificationType.ACCOMMODATION_RATING);
                break;
            case 5:
                // HOST_RESPONSE
                n.setType(NotificationType.HOST_RESPONSE);
                break;
            default:
                // type error
                throw new BadRequestException("Entered notification type does not exist");
        }
        notificationRepository.save(n);
    }

    public void markAsRead(Long id){
        Optional<Notification> notification = notificationRepository.findById(id);
        if(!notification.isPresent()){
            throw new NotFoundException("Notification not found");
        }
        notification.get().setRead(true);
        notificationRepository.save(notification.get());
    }

    public List<NotificationDTO> getNotifications(int userId, int status){
        List<Notification> nl;
        switch(status) {
            case 1: // ALL
                nl = notificationRepository.findAllByUserId(userId);
                break;
            case 2: // READ
                nl = notificationRepository.findReadByUserId(userId);
                break;
            case 3: // UNREAD
                nl = notificationRepository.findUnreadByUserId(userId);
                break;
            default: // status error
                throw new BadRequestException("Entered notification status does not exist");
        }
        if(nl.isEmpty()){
            throw new NotFoundException("No notifications present");
        }else{
            List<Notification> sorted = nl.stream()
                    .sorted(Comparator.comparing(Notification::getDate).reversed())
                    .collect(Collectors.toList());
            List<NotificationDTO> table = new ArrayList<>();
            for (Notification n : sorted) {
                NotificationDTO dto = new NotificationDTO(n);
                int temp = n.getType().ordinal();
                switch(temp) {
                    case 0:
                        // RESERVATION_REQUEST
                        dto.setMessage("You have a new reservation request");
                        break;
                    case 1:
                        // RESERVATION_CANCELLATION
                        dto.setMessage("A reservation has been cancelled");
                        break;
                    case 2:
                        // HOST_RATING
                        dto.setMessage("Your host rating has been updated");
                        break;
                    case 3:
                        // ACCOMMODATION_RATING
                        dto.setMessage("One accommodation rating has been updated");
                        break;
                    case 4:
                        // HOST_RESPONSE
                        dto.setMessage("Host has responded to your reservation request");
                        break;
                    default:
                        // type error
                        throw new BadRequestException("Notification type does not exist");
                }
                table.add(dto);
            }

            return table;
        }
    }

}
