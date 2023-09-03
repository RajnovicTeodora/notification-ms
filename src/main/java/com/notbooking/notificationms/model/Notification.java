package com.notbooking.notificationms.model;

import com.notbooking.notificationms.model.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "read", nullable = false)
    private boolean read;

    @Column(name = "user_id", nullable = false)
    private int user;

    @Column
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    public Notification(int user){
        this.date = LocalDate.now();
        this.read = false;
        this.user = user;
    }
}
