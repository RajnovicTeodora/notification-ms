package com.notbooking.notificationms.dto;

import com.notbooking.notificationms.model.Notification;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private int id;
    private int userId;
    private String message;
    private String type;
    private String date;
    private boolean read;

    public NotificationDTO(Notification notification){
        this.id = notification.getId().intValue();
        this.userId = notification.getUser();
        this.type = notification.getType().name();
        this.date = notification.getDate().toString();
        this.read = notification.isRead();
    }
}
